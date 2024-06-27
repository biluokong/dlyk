<script setup>
import { ref } from 'vue'
import { getUserDetailByIdApi } from '../api/user.js'
import { useRoute } from 'vue-router'

const userDetail = ref({})
const route = useRoute()
const getUserDetail = async () => {
  console.log(route)
  const {data} = await getUserDetailByIdApi(route.params.id)
  userDetail.value = data.data
}
getUserDetail()
</script>

<template>
  <el-form :model="userDetail" label-width="120px">
    <el-form-item label="ID">
      <el-input v-model="userDetail.id" disabled/>
    </el-form-item>

    <el-form-item label="账号">
      <el-input v-model="userDetail.loginAct" disabled/>
    </el-form-item>

<!--    <el-form-item label="密码">
      <el-input type="password" v-model="userDetail.loginPwd" disabled/>
    </el-form-item>-->

    <el-form-item label="姓名">
      <el-input v-model="userDetail.name" disabled/>
    </el-form-item>

    <el-form-item label="手机">
      <el-input v-model="userDetail.phone" disabled/>
    </el-form-item>

    <el-form-item label="邮箱">
      <el-input v-model="userDetail.email" disabled/>
    </el-form-item>

    <el-form-item label="账号未过期">
      <el-input :model-value="userDetail.accountNoExpired === 1 ? '是' : '否'" disabled/>
    </el-form-item>

    <el-form-item label="密码未过期">
      <el-input :model-value="userDetail.credentialsNoExpired === 1 ? '是' : '否'" disabled/>
    </el-form-item>

    <el-form-item label="账号未锁定">
      <el-input :model-value="userDetail.accountNoLocked === 1 ? '是' : '否'" disabled/>
    </el-form-item>

    <el-form-item label="账号是否可用">
      <el-input :model-value="userDetail.accountEnabled === 1 ? '是' : '否'" disabled/>
    </el-form-item>

    <el-form-item label="创建时间">
      <el-input v-model="userDetail.createTime" disabled/>
    </el-form-item>

    <el-form-item label="创建人">
      <el-input v-model="userDetail.createName" disabled/>
    </el-form-item>

    <el-form-item label="编辑时间">
      <el-input v-model="userDetail.editTime" disabled/>
    </el-form-item>

    <el-form-item label="编辑人">
      <el-input v-model="userDetail.editName" disabled/>
    </el-form-item>

    <el-form-item label="最近登录时间">
      <el-input v-model="userDetail.lastLoginTime" disabled/>
    </el-form-item>

    <el-form-item>
      <el-button type="success" @click="$router.back()">返 回</el-button>
    </el-form-item>
  </el-form>
</template>

<style scoped>

</style>