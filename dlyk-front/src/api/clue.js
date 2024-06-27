import request from '../utils/request.js'
import { DEFAULT_PAGE_SIZE } from '../utils/constant.js'

// 获取线索列表
export const getClueListApi = (pageInfo = {
  pageNum: 1,
  pageSize: DEFAULT_PAGE_SIZE
}) => {
  const params = `?pageNum=${pageInfo.pageNum || 1}&pageSize=${pageInfo.pageSize || DEFAULT_PAGE_SIZE}`
  return request.get('/clue/list' + params)
}

// 上传excel
export const uploadExcelApi = (data) => {
  return request.post('/clue/upload', data)
}

// 检查phone是否已经录入
export const checkPhoneApi = (phone) => {
  return request.get('/clue/check/phone/' + phone)
}

// 录入线索
export const addClueApi = (data) => {
  return request.post('/clue', data)
}

// 获取线索详情
export const getClueDetailByIdApi = (id) => {
  return request.get('/clue/' + id)
}

// 修改线索信息
export const updateClueApi = (data) => {
  return request.put('/clue', data)
}

// 删除线索
export const deleteClueApi = (id) => {
  return request.delete('/clue/' + id)
}

// 新增线索备注
export const addClueRemarkApi = (data) => {
  return request.post('/clue/remark', data)
}

// 根据线索id获取线索的备注列表
export const getClueRemarkListApi = (clueId, pageInfo = {
  pageNum: 1,
  pageSize: DEFAULT_PAGE_SIZE
}) => {
  const params = `?pageNum=${pageInfo.pageNum || 1}&pageSize=${pageInfo.pageSize || DEFAULT_PAGE_SIZE}`
  return request.get(`/clue/remarks/${clueId}` + params)
}