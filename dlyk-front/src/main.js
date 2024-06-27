import { createApp } from 'vue'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import router from './router/router.js'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import { createPinia, storeToRefs } from 'pinia'
import persist from 'pinia-plugin-persistedstate'
import useUserStore from './store/index.js'

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.directive('hasPermission', {
  mounted(el, binding) {
    const { value } = binding
    const { userInfo } = useUserStore()
    if (!userInfo.permistionList.find(item => item === value)) {
      el.style.display = 'none'
    }
  }
})

app.use(ElementPlus, { locale: zhCn })
    .use(router)
    .use(createPinia().use(persist))
    .mount('#app')
