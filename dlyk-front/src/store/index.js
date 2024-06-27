import { defineStore } from 'pinia'
import { ref } from 'vue'

const useUserStore = defineStore('user', () => {
  const userInfo = ref({
    name: '',
    jwt: '',
    rememberMe: false
  })
  const setUserInfo = (data) => {
    userInfo.value = data
  }
  const clearUserInfo = () => {
    userInfo.value = {
      name: '',
      jwt: '',
      rememberMe: false
    }
  }
  return { userInfo, setUserInfo, clearUserInfo }
}, {
  persist: true
})

export default useUserStore