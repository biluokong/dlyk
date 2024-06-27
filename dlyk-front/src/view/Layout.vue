<script setup>
import { ref } from 'vue'
import useUserStore from '../store/index.js'
import { logoutApi } from '../api/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'


const isCollapse = ref(false)
const userStore = useUserStore()
const router = useRouter()
const logout = async () => {
  await ElMessageBox.confirm('确定要退出吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await logoutApi()
    userStore.clearUserInfo()
    ElMessage.success('退出成功')
    await router.push('/login')
  })
}

// 获取菜单权限列表，需要先获取响应式的用户信息对象
const { userInfo } = storeToRefs(userStore)

const getRoutePath = (path) => {
  const pathArray = path.split('/')
  if (pathArray.length > 2) {
    return pathArray.slice(0, 2).join('/')
  }
  return path
}
</script>

<template>
  <div class="container">
    <el-container>
      <!--左侧菜单-->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="menuTitle" @click="dashboard">@动力云客管理系统</div>
        <el-menu
            active-text-color="#ffd04b"
            background-color="#334157"
            :default-active="getRoutePath($route.path)"
            text-color="#ffffff"
            :unique-opened="true"
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            style="border-right: 0">

          <el-sub-menu :index="index+1" v-for="(menu, index) in userInfo.menuPermissionList" :key="menu.id">
            <template #title>
              <el-icon><component :is="menu.icon"></component></el-icon>
              <span>{{menu.name}}</span>
            </template>
            <el-menu-item :index="subMenu.url" v-for="subMenu in menu.subPermissionList" :key="subMenu.id">
              <el-icon><component :is="subMenu.icon"></component></el-icon>
              {{subMenu.name}}
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>

      <!--主体-->
      <el-container class="rightContainer">
        <!--头部-->
        <el-header>
          <el-icon style="cursor: pointer" @click="isCollapse = !isCollapse"><Fold /></el-icon>

          <el-dropdown @command="handleCommand" style="float: right; line-height: 38px;">
          <span class="el-dropdown-link">
            {{ userStore.userInfo.name }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
          </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="a">我的资料</el-dropdown-item>
                <el-dropdown-item command="b">修改密码</el-dropdown-item>
                <el-dropdown-item command="e" divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </el-header>

        <!--内容-->
        <el-main>
          <router-view />
        </el-main>

        <!--底部-->
        <el-footer>
          @版权所有 2022-2099 碧落 黄石市黄石港区磁湖路11号
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<style lang="scss" scoped>
.el-aside {
  background: black;
}
.el-header {
  background: azure;
  height: 38px;
  line-height: 38px;
}
.el-footer {
  background: aliceblue;
  height: 38px;
  line-height: 38px;
  text-align: center;
}
.rightContainer {
  height: 100vh;
}
.menuTitle {
  color: white;
  height: 38px;
  line-height: 38px;
  text-align: center;
  cursor: pointer;
}
</style>