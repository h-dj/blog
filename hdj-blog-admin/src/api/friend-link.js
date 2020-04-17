
import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/admin/friendLinks/list',
    method: 'get',
    params: data
  })
}
export function deleteLinks(ids = []) {
  return request({
    url: '/admin/friendLinks/delete',
    method: 'delete',
    data: ids
  })
}
export function addLink(data) {
  return request({
    url: '/admin/friendLinks/add',
    method: 'post',
    data
  })
}

export function examineLink(data) {
  return request({
    url: '/admin/friendLinks/examine',
    method: 'put',
    data
  })
}
