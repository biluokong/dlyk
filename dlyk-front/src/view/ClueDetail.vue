<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import { addClueRemarkApi, getClueDetailByIdApi, getClueRemarkListApi } from '../api/clue.js'
import { getDictValuesApi } from '../api/dict.js'
import { ElMessage } from 'element-plus'
import { convertClueToCustomerApi } from '../api/customer.js'

const clueRemarkRules = {
  noteContent: [
    { required: true, message: '请输入备注内容', trigger: 'blur' },
    { min: 5, message: '备注内容至少5个字符', trigger: 'blur' }
  ],
  noteWay: [{ required: true, message: '请选择下次联系方式', trigger: 'blur' }]
}
const convertCustomerRules = {
  product: [
    { required: true, message: '请选择意向产品', trigger: ['blur', 'change'] }
  ],
  description: [
    { required: true, message: '客户描述不能为空', trigger: 'blur' },
    { min: 5, max: 255, message: '客户描述长度为5-255个字符', trigger: 'blur' }
  ],
  nextContactTime: [
    { required: true, message: '请选择下次联系时间', trigger: 'blur' }
  ]
}

// 获取线索详情
const clueDetail = ref({})
const route = useRoute()
const getClueDetail = async () => {
  const { data } = await getClueDetailByIdApi(route.params.id)
  clueDetail.value = data.data
}
getClueDetail()

// 备注信息
const clueRemark = ref({
  nextContactTime: '',
  noteWay: ''
})

// 跟踪方式select选项
const noteWayOptions = ref([])
const getNoteWayOptions = async () => {
  const { data } = await getDictValuesApi('noteWay')
  noteWayOptions.value = data.data
}
getNoteWayOptions()

// 获取线索备注列表
const clueRemarkList = ref([])
const pageInfo = ref({
  pageNum: 1,
  pageSize: 2,
  total: 0
})
const getClueRemarkList = async () => {
  const { data } = await getClueRemarkListApi(route.params.id, pageInfo.value)
  clueRemarkList.value = data.data.list
  pageInfo.value.total = data.data.total
}
getClueRemarkList()

// 切换页码
const toPage = (current) => {
  pageInfo.value.pageNum = current
  getClueRemarkList()
}

// 提交线索备注信息
const clueRemarkRefForm = ref(null)
const clueRemarkSubmit = async () => {
  await clueRemarkRefForm.value.validate()
  await addClueRemarkApi({
    clueId: clueDetail.value.id,
    ...clueRemark.value
  })
  ElMessage.success('提交成功')
  clueRemarkRefForm.value.resetFields()
  await getClueRemarkList()
}

// 控制线索转客户的弹窗显示
const convertCustomerDialogVisible = ref(false)
const customerQuery = ref({})

const convertCustomer = () => {
  customerQuery.value = {}
  convertCustomerDialogVisible.value = true
}

// 加载意向产品列表
const productOptions = ref([])
const getProductOptions = async () => {
  const { data } = await getDictValuesApi('intentionProduct')
   productOptions.value = data.data
}
getProductOptions()

// 转换为客户的处理
const convertCustomerRefForm = ref(null)
const convertCustomerSubmit = async () => {
  await convertCustomerRefForm.value.validate()
  await convertClueToCustomerApi({
    clueId: clueDetail.value.id,
    ...customerQuery.value
  })
  ElMessage.success('转换成功')
  convertCustomerDialogVisible.value = false
  await getClueDetail()
}
</script>

<template>
  <el-form
      ref="clueRemarkRefForm"
      :model="clueRemark"
      label-width="110px"
      :rules="clueRemarkRules"
      style="max-width: 95%;">

    <el-form-item label="负责人">
      <div class="desc">{{ clueDetail.ownerName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="所属活动">
      <div class="desc">{{ clueDetail.activityName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="姓名">
      <div class="desc">{{ clueDetail.fullName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="称呼">
      <div class="desc">{{ clueDetail.appellationName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="手机">
      <div class="desc">{{ clueDetail.phone }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="微信">
      <div class="desc">{{ clueDetail.weixin }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="QQ">
      <div class="desc">{{ clueDetail.qq }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="邮箱">
      <div class="desc">{{ clueDetail.email }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年龄">
      <div class="desc">{{ clueDetail.age }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="职业">
      <div class="desc">{{ clueDetail.job }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="年收入">
      <div class="desc">{{ clueDetail.yearIncome }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="住址">
      <div class="desc">{{ clueDetail.address }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="贷款">
      <div class="desc">{{ clueDetail.needLoanName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向状态">
      <div class="desc">{{ clueDetail.intentionStateName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="意向产品">
      <div class="desc">{{ clueDetail.intentionProductName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索状态">
      <div class="desc">{{ clueDetail.stateName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索来源">
      <div class="desc">{{ clueDetail.sourceName }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="线索描述">
      <div class="desc">{{ clueDetail.description }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="下次联系时间">
      <div class="desc">{{ clueDetail.nextContactTime }}&nbsp;</div>
    </el-form-item>

    <el-form-item label="填写跟踪记录" prop="noteContent">
      <el-input
          v-model="clueRemark.noteContent"
          :rows="8"
          type="textarea"/>
    </el-form-item>
    <el-form-item label="跟踪方式" prop="noteWay">
      <el-select
          v-model="clueRemark.noteWay"
          placeholder="请选择跟踪方式"
          style="width: 100%"
          @click="loadDicValue('noteWay')"
          clearable>
        <el-option
            v-for="item in noteWayOptions"
            :key="item.id"
            :label="item.typeValue"
            :value="item.id"/>
      </el-select>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="clueRemarkSubmit">提 交</el-button>
      <el-button type="success" @click="convertCustomer" v-if="clueDetail.state !== -1">转换客户</el-button>
      <el-button type="success" plain @click="$router.back()">返 回</el-button>
    </el-form-item>
  </el-form>

  <el-table
      :data="clueRemarkList"
      style="width: 100%">
    <el-table-column type="index" label="序号" width="60"/>
    <el-table-column prop="noteWayName" label="跟踪方式"/>
    <el-table-column prop="noteContent" label="跟踪内容"/>
    <el-table-column property="createTime" label="跟踪时间"/>
    <el-table-column property="createName" label="跟踪人"/>
    <el-table-column property="editTime" label="编辑时间"/>
    <el-table-column property="editName" label="编辑人"/>
    <el-table-column label="操作">
      <template #default="scope">
        <a href="javascript:" @click="edit(scope.row.id)">编辑</a>&nbsp;
        <a href="javascript:" @click="del(scope.row.id)">删除</a>
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

  <!--线索转换为客户的弹窗（对话框）-->
  <el-dialog v-model="convertCustomerDialogVisible" title="线索转换客户" width="55%" center>
    <el-form ref="convertCustomerRefForm" :model="customerQuery" label-width="110px" :rules="convertCustomerRules">
      <el-form-item label="意向产品" prop="product">
        <el-select v-model="customerQuery.product" placeholder="请选择" style="width: 100%;">
          <el-option
              v-for="item in productOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客户描述" prop="description">
        <el-input
            v-model="customerQuery.description"
            :rows="8"
            type="textarea"
            placeholder="请输入客户描述"/>
      </el-form-item>
      <el-form-item label="下次跟踪时间" prop="nextContactTime">
        <el-date-picker
            v-model="customerQuery.nextContactTime"
            type="datetime"
            style="width: 100%;"
            value-format="YYYY-MM-DD HH:mm:ss"
            placeholder="请选择下次跟踪时间"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="convertCustomerDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="convertCustomerSubmit">转 换</el-button>
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