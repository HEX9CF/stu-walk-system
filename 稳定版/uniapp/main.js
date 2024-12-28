import App from './App'
import store from '@/store/store.js'

// #ifndef VUE3
import Vue from 'vue'
import './uni.promisify.adaptor'
import { $http } from '@escook/request-miniprogram'
uni.$http = $http
$http.baseUrl = 'https://database.ccjy16.top'
//请求拦截器
$http.beforeRequest = function (options) {
  uni.showLoading({
  	title: '数据加载中...',
  }) 
} 

//响应拦截器
$http.afterRequest = function () {
  uni.hideLoading()
}

// 封装的展示消息提示的方法
uni.$showMsg = function (title = '数据加载失败！', duration = 1500) {
  uni.showToast({
    title,
    duration,
    icon: 'none',
  })
}

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
  ...App,
  store
})
export default app

app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return { 
    app
  }
}
// #endif