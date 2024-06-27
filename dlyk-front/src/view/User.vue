<script setup>
import { ref } from 'vue'
import {
  getUserByIdApi,
  getUserListApi,
  addUserApi,
  updateUserApi,
  deleteUserByIdApi,
  deleteUserByIdsApi
} from '../api/user'
import { ElMessage, ElMessageBox } from 'element-plus'

const userRules = {
  loginAct: [
    {required: true, message: '登录账号不能为空', trigger: 'blur'},
    {min: 3, max: 32, message: '登录账号长度范围为3-32个字符', trigger: 'blur'}
  ],
  loginPwd: [
    {required: true, message: '登录密码不能为空', trigger: 'blur'},
    {min: 6, max: 16, message: '登录密码长度为6-16位', trigger: 'blur'}
  ],
  name: [
    {required: true, message: '姓名不能为空', trigger: 'blur'},
    {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: '姓名必须是中文汉字', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '手机号码不能为空', trigger: 'blur'},
    {pattern: /^1[3-9]\d{9}$/, message: '手机号码格式有误', trigger: 'blur'}
  ],
  email: [
    {required: true, message: '邮箱不能为空', trigger: 'blur'},
    {pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, message: '邮箱格式有误', trigger: 'blur'}
  ],
  accountNoExpired: [
    {required: true, message: '请选择账号是否未过期', trigger: 'blur'}
  ],
  credentialsNoExpired: [
    {required: true, message: '请选择密码是否未过期', trigger: 'blur'}
  ],
  accountNoLocked: [
    {required: true, message: '请选择账号是否未未锁定', trigger: 'blur'}
  ],
  accountEnabled: [
    {required: true, message: '请选择账号是否可用', trigger: 'blur'}
  ]
}

const pageInfo = ref({
  list: [],
  pageNum: 1,
  pageSize: 10,
  total: 0
})
const getUserList = async (pageParams) => {
  const {data} = await getUserListApi(pageParams)
  pageInfo.value = data.data
}
getUserList()
const pageChange = async (current) => {
  await getUserList({ pageNum: current })
}

let selectedUserIds = []
// 数据选中改变时触发该函数
const handleSelectionChange = (selectedRows) => {
  selectedUserIds = []
  selectedRows.forEach(row => selectedUserIds.push(row.id))
  // console.log(selectedUserIds)
}

// 控制用户信息显示弹窗是否显示
const userDialogVisible = ref(false)
const userQuery = ref({
  id: 0,
  loginAct: '',
  loginPwd: '',
  name: '',
  phone: '',
  email: '',
  accountNoExpired: 1,
  credentialsNoExpired: 1,
  accountNoLocked: 1,
  accountEnabled: 1
})
const options = [
  {label: '是', value: 1},
  {label: '否', value: 0}
]
const showDialogForEdit = async (userId) => {
  const {data} = await getUserByIdApi(userId)
  userQuery.value = data.data
  userDialogVisible.value = true
}
const showDialogByAdd = () => {
  userQuery.value = {
    id: 0,
    loginAct: '',
    loginPwd: '',
    name: '',
    phone: '',
    email: '',
    accountNoExpired: 1,
    credentialsNoExpired: 1,
    accountNoLocked: 1,
    accountEnabled: 1
  }
  userDialogVisible.value = true
}

const userSubmit = async () => {
  // 编辑
  if (userQuery.value.id > 0) {
    await updateUserApi(userQuery.value)
    ElMessage.success('修改用户信息成功')
  } else {  // 新增
    await addUserApi(userQuery.value)
    ElMessage.success('新增用户成功')
  }
  userDialogVisible.value = false
  pageInfo.value.pageNum = 1
  await getUserList()
}

const deleteUser = async (userId) => {
  ElMessageBox.confirm('此操作将永久删除该用户, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteUserByIdApi(userId)
    ElMessage.success('删除用户成功')
    pageInfo.value.pageNum = 1
    await getUserList()
  })
}
const deleteUsers = async () => {
  if (selectedUserIds.length === 0) {
    ElMessage.error('请选择要删除的用户')
    return
  }
  await ElMessageBox.confirm('此操作将永久删除用户, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteUserByIdsApi(selectedUserIds)
    ElMessage.success('删除成功')
    pageInfo.value.pageNum = 1
    await getUserList()
  })
}
</script>

<template>
  <el-button type="primary" class="btn" @click="showDialogByAdd()" v-has-permission="'user:add'">新增用户</el-button>
  <el-button type="danger" class="btn" @click="deleteUsers" v-has-permission="'user:delete'">批量删除</el-button>

  <el-table
      :data="pageInfo.list"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="65"/>
    <el-table-column property="loginAct" label="账号"/>
    <el-table-column property="name" label="姓名"/>
    <el-table-column property="phone" label="手机"/>
    <el-table-column property="email" label="邮箱"/>
    <el-table-column property="createTime" label="创建时间"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="$router.push(`/user/${scope.row.id}`)" v-has-permission="'user:view'">详情</el-button>
        <el-button type="success" @click="showDialogForEdit(scope.row.id)" v-has-permission="'user:edit'">编辑</el-button>
        <el-button type="danger" @click="deleteUser(scope.row.id)" v-has-permission="'user:delete'">删除</el-button>
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
        @prev-click="pageChange"
        @next-click="pageChange"
        @current-change="pageChange"/>
  </p>

  <!--新增用户的弹窗（对话框）-->
  <el-dialog v-model="userDialogVisible" :title="userQuery.id > 0 ? '编辑用户' : '新增用户'" width="55%" center>

    <el-form ref="userRefForm" :model="userQuery" label-width="110px" :rules="userRules">
      <el-form-item label="账号" prop="loginAct">
        <el-input v-model="userQuery.loginAct"/>
      </el-form-item>

      <el-form-item label="密码" v-if="userQuery.id > 0">
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>

      <el-form-item label="密码" prop="loginPwd" v-else>
        <el-input type="password" v-model="userQuery.loginPwd"/>
      </el-form-item>

      <el-form-item label="姓名" prop="name">
        <el-input v-model="userQuery.name"/>
      </el-form-item>

      <el-form-item label="手机" prop="phone">
        <el-input v-model="userQuery.phone"/>
      </el-form-item>

      <el-form-item label="邮箱" prop="email">
        <el-input v-model="userQuery.email"/>
      </el-form-item>

      <el-form-item label="账号未过期" prop="accountNoExpired">
        <el-select v-model="userQuery.accountNoExpired" placeholder="请选择" style="width: 100%;">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="密码未过期" prop="credentialsNoExpired">
        <el-select v-model="userQuery.credentialsNoExpired" placeholder="请选择" style="width: 100%;">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号未锁定" prop="accountNoLocked">
        <el-select v-model="userQuery.accountNoLocked" placeholder="请选择" style="width: 100%;">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="账号是否可用" prop="accountEnabled">
        <el-select v-model="userQuery.accountEnabled" placeholder="请选择" style="width: 100%;">
          <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="userDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="userSubmit">提 交</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.btn {
  margin-bottom: 10px;
}
</style>