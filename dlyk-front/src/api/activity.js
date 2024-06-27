import request from '../utils/request.js'
import { DEFAULT_PAGE_SIZE } from '../utils/constant.js'

// 获取活动列表
export function getActivityListApi(pageInfo = {
  pageNum: 1,
  pageSize: DEFAULT_PAGE_SIZE
}, data = {}) {
  const params = `?pageNum=${pageInfo.pageNum || 1}&pageSize=${pageInfo.pageSize || DEFAULT_PAGE_SIZE}`
  return request.post('/activity/list' + params, data)
}

// 新增活动
export function addActivityApi(data) {
  return request.post('/activity', data)
}

// 获取活动信息
export function getActivityByIdApi(id) {
  return request.get('/activity/' + id)
}

// 更新活动信息
export function updateActivityApi(data) {
  return request.put('/activity', data)
}

// 获取活动详情
export function getActivityDetailByIdApi(id) {
  return request.get('/activity/detail/' + id)
}

// 新增活动备注
export function addActivityRemarkApi(data) {
  return request.post('/activity/remark', data)
}

// 获取活动备注列表
export function getActivityRemarkListApi(activityId, pageInfo = {
  pageNum: 1,
  pageSize: DEFAULT_PAGE_SIZE
}) {
  const params = `?pageNum=${pageInfo.pageNum || 1}&pageSize=${pageInfo.pageSize || DEFAULT_PAGE_SIZE}`
  return request.get('/activity/remarks/' + activityId + params)
}

// 删除活动备注
export function deleteActivityRemarkApi(id) {
  return request.delete('/activity/remark/' + id)
}

// 修改活动备注
export function updateActivityRemarkApi(data) {
  return request.put('/activity/remark', data)
}

// 获取正在进行的活动列表
export function getOngoingActivityListApi() {
  return request.get('/activity/ongoing/list')
}