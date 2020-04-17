import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/user/signIn',
    method: 'post',
    data
  })
}

export function fetchList(data) {
  console.log(data)
  return request({
    url: '/admin/user/list',
    method: 'get',
    params: data
  })
}

export function getInfo() {
  return request({
    url: '/admin/user/info',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/admin/user/logout',
    method: 'put'
  })
}

export function addUser(data) {
  return request({
    url: '/admin/user/add',
    method: 'post',
    data
  })
}
export function updateUser(id, data) {
  return request({
    url: '/admin/user/edit/' + id,
    method: 'put',
    data
  })
}
export function updateProfile(data) {
  return request({
    url: '/admin/user/profile',
    method: 'put',
    data
  })
}

export function deleteUser(ids = []) {
  return request({
    url: '/admin/user/delete',
    method: 'delete',
    data: ids
  })
}

export function getUser(id) {
  return request({
    url: '/admin/user/info/' + id,
    method: 'get'
  })
}

export function uploadUserAvatar(avatar) {
  const data = { 'avatar': avatar }
  return request({
    url: '/admin/user/avatar',
    method: 'put',
    data
  })
}
