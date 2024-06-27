<script setup>
import { ref } from 'vue'
import { getAllUserApi } from '../api/user.js'
import { addActivityApi, getActivityByIdApi, updateActivityApi } from '../api/activity.js'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'

// 市场活动表单验证规则
const activityRules = {
  ownerId: [
    { required: true, message: '请选择负责人', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入活动名称', trigger: 'blur' }
  ],
  startTime: [
    { required: true, message: '请选择开始时间', trigger: 'blur' }
  ],
  endTime: [
    { required: true, message: '请选择结束时间', trigger: 'blur' }
  ],
  cost: [
    { required: true, message: '请输入活动预算', trigger: 'blur' },
    { pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '活动预算必须是整数或者两位小数', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入活动描述', trigger: 'blur' },
    { min: 5, max: 255, message: '活动描述长度为5-255个字符', trigger: 'blur' }
  ]
}

// 进入该vue就加载负责人列表
const ownerOptions = ref([])
const loadOwner = async () => {
  const {data} = await getAllUserApi()
  ownerOptions.value = data.data
}
loadOwner()

// 表单数据
const activityQuery = ref({
  id: 0,
  ownerId: '',
  name: '',
  startTime: '',
  endTime: '',
  cost: '',
  description: ''
})
// 如果有id，说明是编辑，根据id查询活动信息
const route = useRoute()
const getActivityInfo = async () => {
  const activityId = route.params.id
  if (activityId) {
    const { data } = await getActivityByIdApi(activityId)
    activityQuery.value = data.data
  }
}
getActivityInfo()

// 提交表单
const activityRefForm = ref(null)
const router = useRouter()
const activitySubmit = async () => {
  await activityRefForm.value.validate()
  if (activityQuery.value.id > 0) {
    await updateActivityApi(activityQuery.value)
    ElMessage.success('修改活动信息成功')
  } else {
    await addActivityApi(activityQuery.value)
    ElMessage.success('新增活动成功')
  }
  router.back()
}
</script>

<template>
  <el-form ref="activityRefForm" :model="activityQuery" label-width="110px" :rules="activityRules">
    <el-form-item label="负责人" prop="ownerId">
      <el-select v-model="activityQuery.ownerId" placeholder="请选择" class="width">
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="活动名称" prop="name">
      <el-input v-model="activityQuery.name"/>
    </el-form-item>

    <el-form-item label="开始时间" prop="startTime">
      <el-date-picker
          v-model="activityQuery.startTime"
          type="datetime"
          placeholder="请选择创建时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          format="YYYY-MM-DD"
          style="width:100%;"/>
    </el-form-item>

    <el-form-item label="结束时间" prop="endTime">
      <el-date-picker
          v-model="activityQuery.endTime"
          type="datetime"
          placeholder="请选择创建时间"
          value-format="YYYY-MM-DD HH:mm:ss"
          format="YYYY-MM-DD"
          style="width:100%;"/>
    </el-form-item>

    <el-form-item label="活动预算" prop="cost">
      <el-input v-model="activityQuery.cost"/>
    </el-form-item>

    <el-form-item label="活动描述" prop="description">
      <el-input
          v-model="activityQuery.description"
          :rows="6"
          type="textarea"
          placeholder="请输入活动描述"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="activitySubmit">提 交</el-button>
      <el-button @click="$router.back()">返 回</el-button>
    </el-form-item>

  </el-form>
</template>

<style scoped>
.width {
  width: 100%;
}
</style>