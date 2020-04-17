import request from '@/utils/request'

export function fetchTagList(data) {
  return request({
    url: '/admin/tags/list',
    method: 'get',
    params: data
  })
}

export function tagSelectList() {
  return request({
    url: '/admin/tags/selectList',
    method: 'get'
  })
}
export function addTag(data) {
  return request({
    url: '/admin/tags/save',
    method: 'post',
    data
  })
}
export function updateTag(id, data) {
  return request({
    url: '/admin/tags/update/' + id,
    method: 'put',
    data
  })
}
export function getTag(id) {
  return request({
    url: '/admin/tags/info/' + id,
    method: 'get'
  })
}
export function deleteTags(ids = []) {
  return request({
    url: '/admin/tags/delete',
    method: 'delete',
    data: ids
  })
}

