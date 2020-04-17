import request from '@/utils/request'
import store from '@/store'

export function uploadImage(data) {
  return request({
    url: '/admin/images/upload',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data
  })
}

export function getuploadImageURL() {
  console.log(store.getters.baseApi)
  return store.getters.baseApi + '/admin/images/upload'
}
