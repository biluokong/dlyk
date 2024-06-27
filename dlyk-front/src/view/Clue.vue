<script setup>
import { markRaw, ref } from 'vue'
import { deleteClueApi, getClueListApi, uploadExcelApi } from '../api/clue.js'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'

// 线索列表和分页参数
const clueList = ref([])
const pageInfo = ref({
  pageNum: 1,
  pageSize: 10,
  total: 0
})
const getClueList = async () => {
  const {data} = await getClueListApi(pageInfo.value)
  clueList.value = data.data.list
  pageInfo.value.total = data.data.total
}
getClueList()

// 切换页码的处理
const toPage = (current) => {
  pageInfo.value.pageNum = current
  getClueList()
}

// 显示导入线索的模态框
const importExcelDialogVisible = ref(false)
const uploadRef = ref(null)
//上传文件时的回调
const uploadFile = async (options) => {
  const formData = new FormData()
  formData.append('file', options.file)
  await uploadExcelApi(formData)
  ElMessage.success('导入成功')
  uploadRef.value.clearFiles()
  await getClueList()
}
// 导入excel的处理
const submitExcel = async () => {
  uploadRef.value.submit()
}

// 点击添加线索的处理
const router = useRouter()
const addClue = () => {
  router.push('/clue/add')
}

// 删除线索的处理
const delHandle = (id) => {
  ElMessageBox.confirm('确定删除该线索吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteClueApi(id)
    ElMessage.success('删除成功')
    await getClueList()
  })
}
</script>

<template>
  <el-button type="primary" class="btn" @click="addClue" v-has-permission="'clue:add'">录入线索</el-button>
  <el-button type="success" class="btn" @click="importExcelDialogVisible = true" v-has-permission="'clue:import'">导入线索(Excel)</el-button>
  <el-button type="danger" class="btn" @click="batchDelClue" v-has-permission="'clue:delete'">批量删除</el-button>

  <el-table
      :data="clueList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="ownerName" label="负责人" width="120" />
    <el-table-column property="activityName" label="所属活动"/>
    <el-table-column label="姓名">
      <template #default="scope">
        <a href="javascript:" @click="$router.push(`/clue/detail/${scope.row.id}`)">{{ scope.row.fullName }}</a>
      </template>
    </el-table-column>
    <el-table-column property="appellationName" label="称呼"/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="weixin" label="微信" width="120"/>
    <el-table-column property="needLoanName" label="是否贷款"/>
    <el-table-column property="intentionStateName" label="意向状态"/>
    <el-table-column property="intentionProductName" label="意向产品"/>
    <el-table-column property="stateName" label="线索状态">
<!--      <template #default="scope">
        <span style="background: lightgoldenrodyellow" v-if="scope.row.state === -1"> {{ scope.row.stateName }} </span>
        <span v-else> {{ scope.row.stateName }} </span>
      </template>-->
    </el-table-column>
    <el-table-column property="sourceName" label="线索来源"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="$router.push(`/clue/detail/${scope.row.id}`)" v-has-permission="'clue:view'">详情</el-button>
        <el-button type="success" @click="$router.push(`/clue/edit/${scope.row.id}`)" v-has-permission="'clue:edit'">编辑</el-button>
        <el-button type="danger" @click="delHandle(scope.row.id)" v-has-permission="'clue:delete'">删除</el-button>
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
        @prev-click="toPage"
        @next-click="toPage"
        @current-change="toPage"/>
  </p>

  <!--活动备注记录的弹窗-->
  <el-dialog v-model="importExcelDialogVisible" title="导入线索Excel" width="55%" center draggable>
    <el-upload
        ref="uploadRef"
        :http-request="uploadFile"
        :auto-upload="false">

      <template #trigger>
        <el-button type="primary">选择Excel文件</el-button>
      </template>
      仅支持后缀名为.xls或.xlsx的文件

      <template #tip>
        <div class="fileTip">
          重要提示：
          <ul>
            <li>上传仅支持后缀名为.xls或.xlsx的文件；</li>
            <li>给定Excel文件的第一行将视为字段名；</li>
            <li>请确认您的文件大小不超过50MB；</li>
            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式；</li>
            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式；</li>
          </ul>
        </div>
      </template>
    </el-upload>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="importExcelDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="submitExcel">导 入</el-button>
      </span>
    </template>
  </el-dialog>

</template>

<style scoped>
.el-table {
  margin-top: 15px;
}
.fileTip {
  padding-top: 15px;
}
</style>