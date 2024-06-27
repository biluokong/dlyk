import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../view/Login.vue')
    },
    {
      path: '/',
      name: 'layout',
      component: () => import("../view/Layout.vue"),
      children: [
        {
          path: '',
          name: 'dashboard',
          component: () => import("../view/Dashboard.vue")
        },
        {
          path: '/user',
          name: 'user',
          component: () => import("../view/User.vue")
        },
        {
          path: '/user/:id',
          name: 'userDetail',
          component: () => import("../view/UserDetail.vue")
        },
        {
          path: '/activity',
          name: 'activity',
          component: () => import("../view/Activity.vue")
        },
        {
          path: '/activity/add',
          name: 'addActivity',
          component: () => import("../view/ActivityRecord.vue")
        },
        {
          path: '/activity/edit/:id',
          name: 'editActivity',
          component: () => import("../view/ActivityRecord.vue")
        },
        {
          path: '/activity/detail/:id',
          name: 'activityDetail',
          component: () => import("../view/ActivityDetail.vue")
        },
        {
          path: '/clue',
          name: 'clue',
          component: () => import("../view/Clue.vue")
        },
        {
          path: '/clue/add',
          name: 'addClue',
          component: () => import("../view/ClueRecord.vue")
        },
        {
          path: '/clue/edit/:id',
          name: 'editClue',
          component: () => import("../view/ClueRecord.vue")
        },
        {
          path: '/clue/detail/:id',
          name: 'detailClue',
          component: () => import("../view/ClueDetail.vue")
        },
        {
          path: '/customer',
          name: 'customer',
          component: () => import("../view/Customer.vue")
        }
      ]
    }
  ]
})

export default router
