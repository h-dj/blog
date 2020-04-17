import request from '@/utils/request'

export function fetchRoleListAll() {
  return request({
    url: '/admin/role/selectList',
    method: 'get'
  })
}

export function fetchList(data) {
  return request({
    url: '/admin/role/list',
    method: 'get',
    params: data
  })
}
export function addRole(data) {
  return request({
    url: '/admin/role/add',
    method: 'post',
    data
  })
}
export function deleteRole(ids = []) {
  return request({
    url: '/admin/role/delete',
    method: 'delete',
    data: ids
  })
}
export function roleInfo(id) {
  return request({
    url: '/admin/role/info/' + id,
    method: 'get'
  })
}
export function updateRole(id, data) {
  return request({
    url: '/admin/role/edit/' + id,
    method: 'put',
    data
  })
}
