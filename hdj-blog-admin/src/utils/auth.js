import Cookies from 'js-cookie'

const TokenKey = 'blog_token'

export function getToken() {
  // 开启时候执行的代码
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  // 开启时候执行的代码
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  // 开启时候执行的代码
  return Cookies.remove(TokenKey)
}
