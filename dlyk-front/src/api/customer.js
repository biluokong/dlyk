import request from '../utils/request.js'
import { DEFAULT_PAGE_SIZE } from '../utils/constant.js'

// 将线索转换为客户
export const convertClueToCustomerApi = (data) => {
  return request.post('/customer', data)
}

// 获取分页列表
export const getCustomerListApi = (pageInfo = { pageNum: 1, pageSize: DEFAULT_PAGE_SIZE }) => {
  const params = `?pageNum=${pageInfo.pageNum || 1}&pageSize=${pageInfo.pageSize || DEFAULT_PAGE_SIZE}`
  return request.get('/customer/list' + params)
}

// 导出全部客户数据到excel
export const exportAllCustomerApi = () => {
  return request.get('/customer/export', {
    responseType: 'blob'
  })
}

// 选择性导出客户数据到excel
export const exportCustomerApi = (data) => {
  const params = `?ids=${data.join(',')}`
  return request.get('/customer/export' + params, {
    responseType: 'blob'
  })
}