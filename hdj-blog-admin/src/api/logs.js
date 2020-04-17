import request from '@/utils/request'

export function fetchLogList(data) {
  return request({
    url: '/admin/logs/list',
    method: 'get',
    params: data
  })
}
