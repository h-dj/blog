
import request from '@/utils/request'

/**
 * 文章列表
 * @param {Object} data 
 */
export function fetchList(data) {
  return request({
    url: '/articles',
    method: 'get',
    params: data || null
  })
}


/**
 * 文章详情
 * @param {Object} data 
 */
export function articleInfo(id) {
  return request({
    url: '/articles/' + id,
    method: 'get'
  })
}
/**
 * 标签
 * @param {Object} data 
 */
export function tags() {
  return request({
    url: "/articles/tags",
    method: 'get'
  })
}

/**
 * 标签
 * @param {Object} data 
 */
export function categorys() {
  return request({
    url: "/articles/categorys",
    method: 'get'
  })
}

/**
 * 归档
 * @param {*} query 
 */
export function archives(query) {
  return request({
    url: "/articles/archive",
    method: 'get',
    params: query
  })
}
/**
 * 友链
 */
export function friendLinks() {
  return request({
    url: "/friendLinks/list",
    method: 'get',
  })
}
/**
 * 添加友链
 * @param {Object} data 
 */
export function addfriendLinks(data) {
  return request({
    url: "/friendLinks/add",
    method: 'post',
    data
  })
}
/**
 * 搜索文章
 * @param {*} data 
 */
export function searchArticles(query) {
  return request({
    url: "/articles/search",
    method: 'get',
    params:query
  })
}

/**
 * 搜索文章
 * @param {*} data
 */
export function articleLike(articleId) {
  return request({
    url: "/articles/like/"+articleId,
    method: 'put'
  })
}