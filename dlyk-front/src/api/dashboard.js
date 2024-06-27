import request from '../utils/request.js'

// 获取总览数据
export const getSummaryDataApi = () => {
  return request.get('/dashboard/summary')
}

// 获取销售漏斗图数据
export const getSalesFunnelDataApi = () => {
  return request.get('/dashboard/salesFunnel')
}

// 获取线索来源饼图
export const getClueSourcePieDataApi = () => {
  return request.get('/dashboard/clueSourcePie')
}