import Vue from 'vue'
import Router from 'vue-router'
import { treeDataTranslate } from '@/utils/index'
import { navMenu } from '@/api/menu'
import { getToken } from '@/utils/auth' // get token from cookie
import store from '@/store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
NProgress.configure({ showSpinner: false }) // NProgress Configuration

Vue.use(Router)

const isDev = process.env.NODE_ENV === 'development'

/* Layout */
import Layout from '@/layout'
import Login from '@/views/login'
import _404 from '@/views/404'
import Dashboard from '@/views/dashboard'
// import Menu from '@/views/sys/menu'

// 开发环境不使用懒加载, 因为懒加载页面太多的话会造成webpack热更新太慢, 所以只有生产环境使用懒加载
const _import = require('./import-' + process.env.NODE_ENV)

// 全局路由
const globalRoutes = [
  {
    path: '/login',
    component: Login,
    hidden: true,
    meta: { title: '登录' }
  },

  {
    path: '/404',
    component: _404,
    hidden: true,
    meta: { title: '404未找到' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    meta: { title: 'Dashboard', icon: 'dashboard' },
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard,
        meta: { title: 'Dashboard', icon: 'dashboard', breadcrumb: false }
      }
    ]
  }

  // 暂时菜单
  // {
  //   path: '/sys',
  //   component: Layout,
  //   redirect: '/sys/menu',
  //   meta: { title: '菜单管理' },
  //   children: [
  //     {
  //       path: '/sys/menu',
  //       name: '菜单管理',
  //       component: Menu,
  //       meta: { title: '菜单管理' }
  //     }
  //   ]
  // }
]

const createRouter = () =>
  new Router({
    base: process.env.VUE_APP_BASE_PATH,
    mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    isAddDynamicMenuRoutes: false, // 是否已经添加动态（菜单）路由
    routes: globalRoutes
  })

const router = createRouter()

router.beforeEach((to, from, next) => {
  NProgress.start()
  const hasToken = getToken()
  if (
    router.options.isAddDynamicMenuRoutes ||
    to.path === '/login' ||
    to.path === '/404') {
    next()
  } else {
    // 如果访问非登录界面，且户会话信息不存在，代表未登录，则跳转到登录界面
    if (!hasToken) {
      next({ path: `/login` })
    } else {
      store.dispatch('user/getInfo').then(() => {
        // 加载动态路由
        navMenu()
          .then(({ data, code }) => {
            if (data && code === 200) {
              parseRouters(data)
              next({ ...to, replace: false })
            } else {
              next()
            }
          })
          .catch(e => {
            next()
            NProgress.done()
            console.log(e, '动态创建路由失败！')
          })
      })
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})

/**
 * 解析菜单
 * @param {*} data
 */
function parseRouters(data) {
  // 把后台菜单转换为路由对象
  const treeData = treeDataTranslate(data)
  console.log(treeData)
  const routes = convertRoute(generateRoute(treeData))
  routes.push({ path: '*', redirect: '/404', hidden: true })
  const ayncRouters = []
  ayncRouters.push(...globalRoutes)
  ayncRouters.push(...routes)
  router.options.isAddDynamicMenuRoutes = true
  // 加载异步路由
  router.addRoutes(routes)
  // 更新vuex中的路由，以便侧边栏渲染
  store.dispatch('app/loadAyncRouter', ayncRouters)
}

/**
 * 包裹路由
 * @param {Array} route
 */
function convertRoute(routes = []) {
  return routes.map(r => {
    if (r.children) {
      return r
    }
    return {
      path: r.path,
      component: Layout,
      redirect: r.path,
      meta: r.meta,
      children: [r]
    }
  })
}

/**
 * 生成路由
 * @param {*} menuList 菜单列表
 */
function generateRoute(menuList = []) {
  const routes = []
  // 遍历菜单，生成路由
  for (let i = 0; i < menuList.length; i++) {
    const menuObj = menuList[i]
    // 如果是按钮，跳过
    if (menuObj.type === 2) {
      continue
    }
    // 路由对象
    const route = {
      path: menuObj.url || `i-${menuObj.id}`,
      name: menuObj.url
        ? menuObj.url.replace(new RegExp('/', 'g'), '-')
        : `i-${menuObj.id}`,
      hidden: menuObj.hidden,
      component: null,
      meta: {
        title: menuObj.menuName,
        is_iframe: false,
        icon: menuObj.icon,
        permission: menuObj.permission
      }
    }

    try {
      if (menuObj.component) {
        if (menuObj.component === 'layout/index') {
          route.component = Layout
        } else {
          route.component = _import(menuObj.component)
        }
      }
    } catch (e) {
      console.log(e)
    }
    if (menuObj.children && menuObj.children.length > 0) {
      route.children = generateRoute(menuObj.children)
      if (route.children && route.children.length > 0) {
        route.redirect = route.children[0].path
      }
    }
    routes.push(route)
  }
  return routes
}

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
