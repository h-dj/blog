import request from '@/utils/request'

export function fetchList(data) {
  return request({
    url: '/admin/images/list',
    method: 'get',
    params: data
  })
}
