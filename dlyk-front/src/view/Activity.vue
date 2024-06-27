<script setup>
import { ref } from 'vue'
import { getActivityListApi } from '../api/activity'
import { getAllUserApi } from '../api/user.js'

const activityRules = {
  cost: [
    { pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur' }
  ]
}

const pageInfo = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

// 获取活动列表
const activityList = ref([])
const getActivityList = async (pageParam, conditions) => {
  const { data } = await getActivityListApi(pageParam, conditions)
  activityList.value = data.data.list
  pageInfo.value.total = data.data.total
}
getActivityList()

// 获取负责人列表
const ownerOptions = ref([])
const getOwnerList = async () => {
  const { data } = await getAllUserApi()
  ownerOptions.value = data.data
}
getOwnerList()

// 搜索
const activityQuery = ref({
  ownerId: null,
  name: null,
  activityTime: [],
  cost: null,
  createTime: null
})
const onSearch = async () => {
  const activityTime = activityQuery.value.activityTime
  pageInfo.value.pageNum = 1
  const { data } = await getActivityListApi(pageInfo.value, {
    ...activityQuery.value,
    startTime: activityTime.length > 0 ? activityTime[0] : null,
    endTime: activityTime.length > 0 ? activityTime[1] : null
  })
  activityList.value = data.data.list
}
const reset = () => {
  activityQuery.value = {
    ownerId: null,
    name: null,
    activityTime: [],
    cost: null,
    createTime: null
  }
}

// 切换页码
const toPage = async (current) => {
  pageInfo.value.pageNum = current
  await getActivityList(pageInfo.value, activityQuery.value)
}
</script>

<template>
  <el-form :inline="true" :model="activityQuery" :rules="activityRules">
    <el-form-item label="负责人">
      <el-select
          v-model="activityQuery.ownerId"
          placeholder="请选择"
          style="width: 100px"
          clearable>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="活动名称">
      <el-input v-model="activityQuery.name" placeholder="请输入活动名称" clearable/>
    </el-form-item>

    <el-form-item label="活动时间">
      <el-date-picker
          v-model="activityQuery.activityTime"
          type="datetimerange"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          format="YYYY-MM-DD"
      />
    </el-form-item>

    <el-form-item label="最小活动预算" prop="cost">
      <el-input v-model="activityQuery.cost" placeholder="请输入预算" clearable style="width: 100px"/>
    </el-form-item>

    <el-form-item label="最小创建时间">
      <el-date-picker
          v-model="activityQuery.createTime"
          type="datetime"
          placeholder="请选择创建时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          format="YYYY-MM-DD"
      />
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSearch" v-has-permission="'activity:search'">搜 索</el-button>
      <el-button type="primary" plain @click="reset">重 置</el-button>
    </el-form-item>
  </el-form>

  <el-button type="primary" @click="$router.push('/activity/add')" v-has-permission="'activity:add'">录入市场活动
  </el-button>
  <el-button type="danger" @click="batchDel" v-has-permission="'activity:delete'">批量删除</el-button>

  <el-table
      :data="activityList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55"/>
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="ownerName" label="负责人"/>
    <el-table-column property="name" label="活动名称" show-overflow-tooltip/>
    <el-table-column property="startTime" label="开始时间" show-overflow-tooltip/>
    <el-table-column property="endTime" label="结束时间" show-overflow-tooltip/>
    <el-table-column property="cost" label="活动预算" show-overflow-tooltip/>
    <el-table-column property="createTime" label="创建时间" show-overflow-tooltip/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="$router.push('/activity/detail/' + scope.row.id)"
                   v-has-permission="'activity:view'">详情
        </el-button>
        <el-button type="success" @click="$router.push('/activity/edit/' + scope.row.id)"
                   v-has-permission="'activity:edit'">编辑
        </el-button>
        <el-button type="danger" @click="del(scope.row.id)" v-has-permission="'activity:delete'">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-pagination
      background
      layout="prev, pager, next"
      :current-page="pageInfo.pageNum"
      :page-size="pageInfo.pageSize"
      :total="pageInfo.total"
      @prev-click="toPage"
      @next-click="toPage"
      @current-change="toPage"/>

</template>

<style scoped>
.el-form {
  margin-bottom: 20px;
}

.el-table {
  margin-top: 12px;
}

.el-pagination {
  margin-top: 12px;
}
</style>