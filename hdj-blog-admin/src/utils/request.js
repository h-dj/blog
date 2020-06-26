import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

const baseURL = process.env.VUE_APP_BASE_API

// create an axios instance
const service = axios.create({
  baseURL: baseURL, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 5000 // request timeout
})

// request interceptor
service.interceptors.request.use(
  config => {
    // do something before request is sent

    if (store.getters.token) {
      // let each request carry token
      // ['x-auth-token'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['x-auth-token'] = 'Bearer ' + getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.log(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data

    const token = response.headers['x-auth-token']
    // refresh token when the token is invalid
    if (token) {
      store.dispatch('user/refreshToken', token)
    }
    // if the custom code is not 200, it is judged as an error.
    if (res.code !== 200) {
      if (document.querySelectorAll('.el-message--error').length <= 0) {
        Message({
          message: res.msg || 'Error',
          type: 'error',
          duration: 1 * 1000
        })
      }

      // 500001: Illegal token; 500002: Token expired;
      if (res.code === 500001 || res.code === 500002) {
        // to re-login
        MessageBox.confirm(
          'You have been logged out, you can cancel to stay on this page, or log in again',
          'Confirm logout',
          {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return res
    }
  },
  error => {
    console.error(error)
    let msg
    if (error.response && error.response.data) {
      msg = error.response.data.msg || 'Error'
    } else {
      msg = error.message || 'Error'
    }
    if (document.querySelectorAll('.el-message--error').length <= 0) {
      Message({
        message: msg,
        type: 'error',
        duration: 5 * 1000
      })
    }
    return Promise.reject(error)
  }
)

export default service
