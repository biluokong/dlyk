<script setup>
import { ref } from 'vue'
import { loginApi } from '../api/user.js'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import useUserStore from '../store/index.js'

const loginRules = {
  //验证登录账号字段
  loginAct: [
    { required: true, message: '请输入登录账号', trigger: 'blur' }
  ],
  //验证登录密码字段
  loginPwd: [
    { required: true, message: '请输入登录密码', trigger: 'blur' }
  ]
}

const user = ref({
  loginAct: '',
  loginPwd: '',
  rememberMe: false
})
const router = useRouter()
const userStore = useUserStore()
const loginRefForm = ref(null)
const login = async () => {
  await loginRefForm.value.validate()
  const {data } = await loginApi(user.value)
  userStore.setUserInfo({
    ...data.data,
    token: data.msg,
    rememberMe: user.value.rememberMe
  })
  ElMessage.success('登录成功')
  await router.replace('/')
}

</script>

<template>
  <el-container>
    <!--左侧-->
    <el-aside width="200px">
      <img src="../assets/loginBox.svg" class="asideImg">
      <p class="asideTitle">
        欢迎使用动力云客系统
      </p>
    </el-aside>

    <!--右侧-->
    <el-main>
      <div class="mainTitle">欢迎登录</div>
      <div class="mainForm">
        <el-form ref="loginRefForm" :model="user" label-width="120px" :rules="loginRules">
          <el-form-item label="账号" prop="loginAct">
            <el-input v-model.trim="user.loginAct" />
          </el-form-item>

          <el-form-item label="密码" prop="loginPwd">
            <el-input type="password" v-model.trim="user.loginPwd" />
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="login">登 录</el-button>
          </el-form-item>

          <el-form-item>
            <el-checkbox v-model="user.rememberMe" label="记住我" size="large" />
          </el-form-item>
        </el-form>
      </div>
    </el-main>
  </el-container>
</template>

<style lang="scss" scoped>
.el-aside {
  background: black;
  width: 40%;
  text-align: center;
}
.el-main {
  height: calc(100vh);
}
.asideImg {
  width: 400px;
}
.asideTitle {
  color: white;
  font-size: 28px;
}
.mainTitle {
  text-align: center;
  margin-top: 100px;
  margin-bottom: 25px;
  font-weight: bold;
}
.mainForm {
  text-align: center;
  width: 60%;
  margin: auto;
}
.el-button {
  width: 100%;
}
</style>