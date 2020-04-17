import request from '@/utils/request'

export function categorySelectList() {
  return request({
    url: '/admin/categorys/selectList',
    method: 'get'
  })
}
export function categoryList(data) {
  return request({
    url: '/admin/categorys/list',
    method: 'get',
    params: data
  })
}
export function addCategory(data) {
  return request({
    url: '/admin/categorys/save',
    method: 'post',
    data
  })
}
export function updateCategory(id, data) {
  return request({
    url: '/admin/categorys/update/' + id,
    method: 'put',
    data
  })
}
export function getCategory(id) {
  return request({
    url: '/admin/categorys/info/' + id,
    method: 'get'
  })
}
export function deleteCategory(ids = []) {
  return request({
    url: '/admin/categorys/delete',
    method: 'delete',
    data: ids
  })
}
