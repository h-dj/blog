const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  userName: state => state.user.userName,
  email: state => state.user.email,
  remark: state => state.user.remark,
  sqlApi: state => state.api.sqlApi,
  baseApi: state => state.api.baseApi,
  swaggerApi: state => state.api.swaggerApi
}
export default getters
