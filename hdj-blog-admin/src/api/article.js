
import request from '@/utils/request'

export function fetchList(data) {
  console.log(data)
  return request({
    url: '/admin/article/list',
    method: 'get',
    params: data
  })
}
export function detail(articleId) {
  return request({
    url: '/admin/article/info/' + articleId,
    method: 'get'
  })
}
export function saveArticle(data) {
  return request({
    url: '/admin/article/save',
    method: 'post',
    data
  })
}
export function updateArticle(articleId, data) {
  return request({
    url: '/admin/article/edit/' + articleId,
    method: 'put',
    data
  })
}

export function deleteArticle(articleIds) {
  return request({
    url: '/admin/article/delete',
    method: 'delete',
    data: articleIds || []
  })
}

