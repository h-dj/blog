'use strict';
const path = require('path');
const defaultSettings = require('./src/settings.js');
function resolve(dir) {
  return path.join(__dirname, dir);
}

const name = defaultSettings.title;

const port = process.env.VUE_APP_PORT;

module.exports = {
  publicPath: process.env.VUE_APP_BASE_PATH || '/',
  outputDir: 'dist',
  assetsDir: 'static',
  devServer: {
    port: port || 4567,
    proxy: {
      // change xxx-api/login => mock/login
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
        // 解决console控制台反复打印“WebSocket connection to ‘ws://192.168.188.117:8080/sockjs-node/470/djhbq40w/websocket’ failed: Invalid frame header”问题
        ws: false, // proxy websockets
        target: process.env.VUE_APP_PROXY_API,
        // secure: false,  // 如果是https接口，需要配置这个参数
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: '',
        },
      },
    },
    // after: require('./mock/mock-server.js')
  },
  productionSourceMap: false,
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    name: name,
    resolve: {
      alias: {
        '@': resolve('src'),
      },
    },
  },
  chainWebpack(config) {
    config.plugins.delete('preload'); // TODO: need test
    config.plugins.delete('prefetch'); // TODO: need test

    const cdn = {
      css: [
        // element-ui css
        'https://cdn.bootcdn.net/ajax/libs/element-ui/2.13.2/theme-chalk/index.css',
      ],
      js: [
        // vue must at first!
        'https://cdn.bootcdn.net/ajax/libs/vue/2.6.11/vue.min.js',
        // element-ui js
        'https://cdn.bootcdn.net/ajax/libs/element-ui/2.13.2/index.js',
        'https://cdn.bootcss.com/js-cookie/2.2.0/js.cookie.min.js',
        'https://cdn.jsdelivr.net/npm/axios@0.18.1/dist/axios.min.js',
        'https://cdn.bootcdn.net/ajax/libs/echarts/4.6.0/echarts.min.js',
      ],

      // cdn预加载使用
      externals: {
        vue: 'Vue',
        'element-ui': 'ELEMENT',
        'js-cookie': 'Cookies',
        echarts: 'echarts',
        axios: 'axios',
      },
    };
    /**
     * 添加CDN参数到htmlWebpackPlugin配置中， 详见public/index.html 修改
     */
    config.plugin('html').tap((args) => {
      args[0].cdn = cdn;
      return args;
    });
    // 排除依赖
    config.externals(cdn.externals);

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/icons'))
      .end();
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]',
      })
      .end();
  },
};
