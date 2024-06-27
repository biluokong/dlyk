<script setup>
import { ref } from 'vue'
import { exportAllCustomerApi, exportCustomerApi, getCustomerListApi } from '../api/customer'
import { ElMessage } from 'element-plus'


// 客户列表
const customerList = ref([])
const pageInfo = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
})

// 获取列表数据
const getCustomerList = async () => {
  const {data} = await getCustomerListApi(pageInfo.value)
  customerList.value = data.data.list
  pageInfo.value.total = data.data.total
}
getCustomerList()

// 切换页码
const goPage = (current) => {
  pageInfo.value.pageNum = current
  getCustomerList()
}

// 将数据全部导出到excel
const batchExportExcel = async () => {
  const {data} = await exportAllCustomerApi()
  const objectUrl = URL.createObjectURL(data)
  let a = document.createElement('a')
  a.href = objectUrl
  a.download = '客户列表.xlsx'
  a.click()
}

// 选中表格中数据时的回调
let selectedIds = []
const handleSelectionChange = (selectedRows) => {
  selectedIds = []
  selectedRows.forEach(row => selectedIds.push(row.id))
}

// 选择性的导出到excel
const chooseExportExcel = async () => {
  if (selectedIds.length === 0) {
    return ElMessage.warning('请选择要导出的客户')
  }
  const {data} = await exportCustomerApi(selectedIds)
  const objectUrl = URL.createObjectURL(data)
  let a = document.createElement('a')
  a.href = objectUrl
  a.download = '客户列表.xlsx'
  a.click()
}
</script>

<template>
  <el-button type="primary" class="btn" @click="batchExportExcel">批量导出(Excel)</el-button>
  <el-button type="success" class="btn" @click="chooseExportExcel">选择导出(Excel)</el-button>

  <el-table
      :data="customerList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerName" label="负责人" width="120" />
    <el-table-column property="activityName" label="所属活动"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationName" label="称呼"/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="weixin" label="微信" width="120"/>
    <el-table-column property="needLoanName" label="是否贷款"/>
    <el-table-column property="intentionStateName" label="意向状态"/>
    <el-table-column property="stateName" label="线索状态"/>
    <el-table-column property="sourceName" label="线索来源"/>
    <el-table-column property="intentionProductName" label="意向产品"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="85">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
      </template>
    </el-table-column>
  </el-table>
  <p>
    <el-pagination
        background
        layout="prev, pager, next"
        :current-page="pageInfo.pageNum"
        :page-size="pageInfo.pageSize"
        :total="pageInfo.total"
        @prev-click="goPage"
        @next-click="goPage"
        @current-change="goPage"/>
  </p>
</template>

<style scoped>
.el-table {
  margin-top: 10px;
}
</style>