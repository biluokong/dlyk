import request from '../utils/request.js'

// 根据字典类型code获取值列表
export const getDictValuesApi = (typeCode) => {
  return request.get('/dict/values/' + typeCode)
}

// 获取所有字段值列表
export const getAllDictValuesApi = () => {
  return request.get('/dict/all')
}