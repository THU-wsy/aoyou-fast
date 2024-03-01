import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../Layout/index.vue'
import menu from '../views/system/menu/index.vue'
import role from '../views/system/role/index.vue'
import user from '../views/system/user/index.vue'
import Index from '../views/index.vue'

// 配置路由规则
const constRouter = [
  // 重定向
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/home',
    component: Layout,
    children: [
      {
        path: 'system/menu/index',
        name: '菜单管理',
        meta: {title: '菜单管理'},
        component: menu
      },
      {
        path: 'system/role/index',
        name: '角色管理',
        meta: {title: '角色管理'},
        component: role
      },
      {
        path: 'system/user/index',
        name: '用户管理',
        meta: {title: '用户管理'},
        component: user
      },
      {
        path: 'index',
        name: '首页',
        meta: {title: '首页'},
        component: Index
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'not-found',
    component: () => import('../views/NotFoundView.vue')
  }
]

// 创建路由
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: constRouter
})

export default router
