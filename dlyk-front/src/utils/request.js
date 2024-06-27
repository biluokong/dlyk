import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'
import useUserStore from '../store/index.js'
import router from '../router/router.js'
import { storeToRefs } from 'pinia'

const request = axios.create({
  baseURL: 'http://127.0.0.1:3000/api',
  timeout: 10000
})

const {userInfo} = storeToRefs(useUserStore())

// 添加请求拦截器
request.interceptors.request.use((config) => {
  config.headers['Authorization'] = userInfo.value.token
  // console.log(userInfo)
  config.headers['rememberMe'] = userInfo.value.rememberMe
  return config
}, (error) => {
  return Promise.reject(error)
})

// 添加响应拦截器
request.interceptors.response.use((response) => {
  const data = response.data
  if (data.type && data.type === 'application/octet-stream') {
    return response
  }
  if (data.code !== 200) {
    ElMessage.error(data.msg)
    return Promise.reject(data.msg)
  }
  return response
}, async (error) => {
  const status = error.response.status
  if (status === 401) {
    await ElMessageBox.alert(`${error.response.data.msg}，请重新登录`, '提示', {
      confirmButtonText: '确定',
      type: 'error',
      callback: async () => {
        await router.replace('/login')
      }
    })
    return Promise.reject(error)
  }
  ElMessage.error(error.response.data.msg)
  return Promise.reject(error)
})

export default request