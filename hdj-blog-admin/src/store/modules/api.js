const baseUrl = process.env.VUE_APP_BASE_API
const basePrefix = process.env.VUE_APP_BASE_PATH
const api = {
  state: {
    // 文件上传
    fileUploadApi: baseUrl + '/api/localStorage',
    // baseUrl，
    baseApi: baseUrl + basePrefix
  }
}

export default api
