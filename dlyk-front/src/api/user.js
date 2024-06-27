import request from '../utils/request.js'
import { DEFAULT_PAGE_SIZE } from '../utils/constant.js'

// 登录
export const loginApi = (data) => {
  const formData = `loginAct=${data.loginAct}&loginPwd=${data.loginPwd}&rememberMe=${data.rememberMe}`
  // data是表单数据
  return request.post('/login', formData, {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
  })
}

// 退出登录
export const logoutApi = () => {
  return request.get('/logout')
}

// 获取用户列表
export const getUserListApi = (data = {
  pageNum: 1,
  pageSize: 10
}) => {
  const params = `?pageNum=${data.pageNum || 1}&pageSize=${data.pageSize || DEFAULT_PAGE_SIZE}`
  return request.get('/user/list' + params)
}

// 根据用户id获取用户信息
export const getUserByIdApi = (id) => {
  return request.get('/user/' + id)
}

// 根据用户id获取用户详情
export const getUserDetailByIdApi = (id) => {
  return request.get('/user/detail/' + id)
}

// 添加用户
export const addUserApi = (data) => {
  return request.post('/user', data)
}

// 修改用户信息
export const updateUserApi = (data) => {
  return request.put('/user', data)
}

// 根据id删除用户
export const deleteUserByIdApi = (id) => {
  return request.delete('/user/' + id)
}

// 批量删除用户
export const deleteUserByIdsApi = (ids) => {
  const joinStr = ids.join(',')
  return request.delete('/user/batch?ids=' + joinStr)
}

// 获取所有用户的name和id
export const getAllUserApi = () => {
  return request.get('/user/all')
}