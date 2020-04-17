import request from '@/utils/request'

export function fetchList(data) {
  return request({
    url: '/admin/menus/list',
    method: 'get',
    params: data
  })
}

export function fetchListAll(data) {
  return request({
    url: '/admin/menus/all',
    method: 'get',
    params: data
  })
}
export function fetchSelectList() {
  return request({
    url: '/admin/menus/select',
    method: 'get'
  })
}
export function addMenu(data) {
  return request({
    url: '/admin/menus/add',
    method: 'post',
    data
  })
}
export function menuInfo(id) {
  return request({
    url: '/admin/menus/info/' + id,
    method: 'get'
  })
}
export function updateMenu(id, data) {
  return request({
    url: '/admin/menus/edit/' + id,
    method: 'put',
    data
  })
}
export function deleteMenu(id) {
  return request({
    url: '/admin/menus/delete/' + id,
    method: 'delete'
  })
}
export function navMenu() {
  return request({
    url: '/admin/menus/nav',
    method: 'get'
  })
}

