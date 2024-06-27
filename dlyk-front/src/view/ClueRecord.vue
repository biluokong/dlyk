<script setup>
import { ref } from 'vue'
import { getAllDictValuesApi, getDictValuesApi } from '../api/dict.js'
import { getOngoingActivityListApi } from '../api/activity.js'
import useUserStore from '../store/index.js'
import { addClueApi, checkPhoneApi, getClueDetailByIdApi, updateClueApi } from '../api/clue.js'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { getAllUserApi } from '../api/user.js'

// 验证手机号，不能重复录入同一个手机号
const checkPhone = async (rule, value, callback) => {
  if (value) {
    const { data } = await checkPhoneApi(value)
    if (!data.data) {
      return callback(new Error('该手机号已被录入'))
    } else {
      return callback()
    }
  }
}

//录入线索验证规则
const clueRules = {
  phone: [
    { required: true, message: '请输入手机号码', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号码格式有误', trigger: 'blur' },
    { validator: checkPhone, trigger: 'blur' }
  ],
  fullName: [
    { min: 2, message: '姓名至少2个汉字', trigger: 'blur' },
    { pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '姓名必须为中文汉字', trigger: 'blur' }
  ],
  qq: [
    { min: 5, message: 'QQ号至少为5位', trigger: 'blur' },
    { pattern: /^\d+$/, message: 'QQ号码必须为数字', trigger: 'blur' }
  ],
  email: [
    { pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式有误', trigger: 'blur' }
  ],
  age: [
    { pattern: /^\d+$/, message: '年龄必须为数字', trigger: 'blur' }
  ],
  yearIncome: [
    { pattern: /^[0-9]+(\.[0-9]{2})?$/, message: '年收入必须是整数或者两位小数', trigger: 'blur' }
  ],
  description: [
    { min: 5, max: 255, message: '线索描述长度为5-255个字符', trigger: 'blur' }
  ]
}

//负责人的下拉选项
const ownerOptions = ref([])
//市场活动的下拉选项数据，初始值是空
const activityOptions = ref([])
const appellationOptions = ref([])
const needLoanOptions = ref([])
const intentionStateOptions = ref([])
const productOptions = ref([])
const clueStateOptions = ref([])
const sourceOptions = ref([])


// 获取当前负责人
const userStore = useUserStore()
//线索表单对象，初始值是空
const clueQuery = ref({ ownerId: userStore.userInfo.id })

// 如果是编辑，则需要获取线索详情
const route = useRoute()
const getClueDetail = async () => {
  if (!route.params.id) return
  const { data } = await getClueDetailByIdApi(route.params.id)
  if (!activityOptions.value.find(item => item.id === data.data.activityId)) {
    activityOptions.value.push({ id: data.data.activityId, name: '已过期活动' })
  }
  clueQuery.value = data.data
}
getClueDetail()

// 查询字典数据
const getDictValues = async () => {
  const { data } = await getAllDictValuesApi()
  const dictMap = data.data
  appellationOptions.value = dictMap.appellation
  needLoanOptions.value = dictMap.needLoan
  intentionStateOptions.value = dictMap.intentionState
  productOptions.value = dictMap.intentionProduct
  clueStateOptions.value = dictMap.clueState
  sourceOptions.value = dictMap.source
}
getDictValues()

// 获取所有负责人
const getOwnerOptions = async () => {
  const { data } = await getAllUserApi()
  ownerOptions.value = data.data
}
getOwnerOptions()

// 获取正在进行的活动列表
const getActivityOptions = async () => {
  const { data } = await getOngoingActivityListApi()
  activityOptions.value = data.data
}
getActivityOptions()

// 录入线索
const clueRefForm = ref(null)
const router = useRouter()
const addClueSubmit = async () => {
  await clueRefForm.value.validate()
  if (clueQuery.value.id) {
    await updateClueApi(clueQuery.value)
    ElMessage.success('修改线索信息成功')
  } else {
    await addClueApi(clueQuery.value)
    ElMessage.success('录入线索成功')
  }
  router.back()
}
</script>

<template>
  <el-form
      ref="clueRefForm"
      :model="clueQuery"
      :rules="clueRules"
      label-width="100px"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <el-select
          v-model="clueQuery.ownerId"
          placeholder="请选择负责人"
          style="width: 100%"
          clearable
          disabled>
        <el-option
            v-for="item in ownerOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="所属活动">
      <el-select
          v-model="clueQuery.activityId"
          placeholder="请选择所属活动"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in activityOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="姓名" prop="fullName">
      <el-input v-model="clueQuery.fullName"/>
    </el-form-item>

    <el-form-item label="称呼">
      <el-select
          v-model="clueQuery.appellation"
          placeholder="请选择称呼"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in appellationOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="手机" v-if="clueQuery.id > 0"><!--此时是编辑-->
      <el-input v-model="clueQuery.phone" disabled/>
    </el-form-item>

    <el-form-item label="手机" prop="phone" v-else><!--此时是录入-->
      <el-input v-model="clueQuery.phone"/>
    </el-form-item>

    <el-form-item label="微信">
      <el-input v-model="clueQuery.weixin"/>
    </el-form-item>

    <el-form-item label="QQ" prop="qq">
      <el-input v-model="clueQuery.qq"/>
    </el-form-item>

    <el-form-item label="邮箱" prop="email">
      <el-input v-model="clueQuery.email"/>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input v-model="clueQuery.age"/>
    </el-form-item>

    <el-form-item label="职业">
      <el-input v-model="clueQuery.job"/>
    </el-form-item>

    <el-form-item label="年收入" prop="yearIncome">
      <el-input v-model="clueQuery.yearIncome"/>
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="clueQuery.address"/>
    </el-form-item>

    <el-form-item label="贷款">
      <el-select
          v-model="clueQuery.needLoan"
          placeholder="请选择是否需要贷款"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in needLoanOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向状态">
      <el-select
          v-model="clueQuery.intentionState"
          placeholder="请选择意向状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in intentionStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="意向产品">
      <el-select
          v-model="clueQuery.intentionProduct"
          placeholder="请选择意向产品"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in productOptions"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索状态">
      <el-select
          v-model="clueQuery.state"
          placeholder="请选择线索状态"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in clueStateOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索来源">
      <el-select
          v-model="clueQuery.source"
          placeholder="请选择线索来源"
          style="width: 100%"
          clearable>
        <el-option
            v-for="item in sourceOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item label="线索描述" prop="description">
      <el-input
          v-model="clueQuery.description"
          :rows="5"
          type="textarea"
          placeholder="请输入线索描述"/>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <el-date-picker
          v-model="clueQuery.nextContactTime"
          type="datetime"
          style="width: 100%;"
          value-format="YYYY-MM-DD HH:mm:ss"
          placeholder="请选择下次联系时间"/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="addClueSubmit">提 交</el-button>
      <el-button type="success" plain @click="$router.back()">返 回</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>