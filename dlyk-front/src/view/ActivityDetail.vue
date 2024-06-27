<script setup>
import { useRoute } from 'vue-router'
import {
  addActivityRemarkApi,
  deleteActivityRemarkApi,
  getActivityDetailByIdApi,
  getActivityRemarkListApi, updateActivityRemarkApi
} from '../api/activity'
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

//提交活动备注的验证规则
const activityRemarkRules = {
  noteContent: [
    { required: true, message: '请输入活动备注', trigger: 'blur' },
    { min: 5, max: 255, message: '活动备注长度为5-255个字符', trigger: 'blur' }
  ]
}
//编辑活动备注的验证规则
const editActivityRemarkRules = {
  noteContent: [
    { required: true, message: '请输入活动备注', trigger: 'blur' },
    { min: 5, max: 255, message: '活动备注长度为5-255个字符', trigger: 'blur' }
  ]
}

// 获取活动详情
const route = useRoute()
let activityDetail = ref({})
const getActivityDetail = async () => {
  const { data } = await getActivityDetailByIdApi(route.params.id)
  activityDetail.value = data.data
}
getActivityDetail()

// 备注表单数据和提交表单的处理
const activityRemarkQuery = ref({
  noteContent: ''
})
const activityRemarkRefForm = ref(false)
const activityRemarkSubmit = async () => {
  await activityRemarkRefForm.value.validate()
  await addActivityRemarkApi({
    activityId: activityDetail.value.id,
    noteContent: activityRemarkQuery.value.noteContent
  })
  ElMessage.success('新增备注成功')
  activityRemarkQuery.value.noteContent = ''
  await getRemarkList()
}

//分页参数
const pageInfo = ref({
  pageNum: 1,
  pageSize: 2,
  total: 0
})

// 备注列表数据和获取列表数据的处理
const activityRemarkList = ref([])
const getRemarkList = async () => {
  const { data } = await getActivityRemarkListApi(route.params.id, pageInfo.value)
  activityRemarkList.value = data.data.list
  pageInfo.value.total = data.data.total
  console.log(pageInfo.value)
}
getRemarkList()

// 切换页码时的处理
const toPage = async (current) => {
  pageInfo.value.pageNum = current
  await getRemarkList()
}


// 控制对话框显示
const dialogVisible = ref(false)
// 用于编辑的备注内容数据项
const editActivityRemarkQuery = ref({
  id: '',
  noteContent: ''
})

// 显示编辑对话框
const editRemark = (id) => {
  const currentItem = activityRemarkList.value.find(item => item.id === id)
  editActivityRemarkQuery.value = { ...currentItem }
  dialogVisible.value = true
  console.log(editActivityRemarkQuery.value)
}
// 删除备注
const delRemark = (id) => {
  ElMessageBox.alert('确定删除该备注吗？', '提示', {
    confirmButtonText: '确定',
    type: 'warning',
    callback: async (action) => {
      if (action === 'confirm') {
        await deleteActivityRemarkApi(id)
        ElMessage.success('删除成功')
        pageInfo.value.pageNum = 1
        await getRemarkList()
      }
    }
  })
}
// 修改备注
const editActivityRemarkRefForm = ref(null)
const editActivityRemarkSubmit = async () => {
  await editActivityRemarkRefForm.value.validate()
  await updateActivityRemarkApi(editActivityRemarkQuery.value)
  ElMessage.success('修改成功')
  dialogVisible.value = false
  await getRemarkList()
}
</script>

<template>
  <el-form ref="activityRemarkRefForm" :model="activityRemarkQuery" label-width="120px" :rules="activityRemarkRules">
    <el-form-item label="ID">
      <div class="desc">{{ activityDetail.id }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="负责人">
      <div class="desc"> {{ activityDetail.ownerName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动名称">
      <div class="desc">{{ activityDetail.name }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="开始时间">
      <div class="desc">{{ activityDetail.startTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="结束时间">
      <div class="desc">{{ activityDetail.endTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动预算">
      <div class="desc">{{ activityDetail.cost }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="活动描述">
      <div class="desc">{{ activityDetail.description }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建时间">
      <div class="desc">{{ activityDetail.createTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="创建人">
      <div class="desc">{{ activityDetail.createName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑时间">
      <div class="desc">{{ activityDetail.editTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="编辑人">
      <div class="desc">{{ activityDetail.editName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写备注" prop="noteContent">
      <el-input
          v-model="activityRemarkQuery.noteContent"
          :rows="8"
          type="textarea"
          placeholder="请输入活动备注"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activityRemarkSubmit">提 交</el-button>
      <el-button @click="$router.back()">返 回</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="activityRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="noteContent" label="备注内容"/>
    <el-table-column property="createTime" label="备注时间"/>
    <el-table-column property="createName" label="备注人"/>
    <el-table-column property="editTime" label="编辑时间"/>
    <el-table-column property="editName" label="编辑人"/>
    <el-table-column label="操作">
      <template #default="scope">
        <a href="javascript:" style="margin-right: 10px" @click="editRemark(scope.row.id)">编辑</a>
        <a href="javascript:" @click="delRemark(scope.row.id)">删除</a>
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

  <!--活动备注记录的弹窗-->
  <el-dialog v-model="dialogVisible" title="编辑市场活动备注记录" width="55%" center draggable>
    <el-form ref="editActivityRemarkRefForm" :model="editActivityRemarkQuery" label-width="110px"
             :rules=editActivityRemarkRules>
      <el-form-item label="活动备注" prop="noteContent">
        <el-input
            v-model="editActivityRemarkQuery.noteContent"
            :rows="8"
            type="textarea"
            placeholder="请输入活动备注"/>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="editActivityRemarkSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
.desc {
  background-color: #F0FFFF;
  width: 100%;
  padding-left: 15px;
}
</style>