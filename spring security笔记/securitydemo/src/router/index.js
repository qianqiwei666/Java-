import Vue from 'vue'
import VueRouter from 'vue-router'
import cookie from "js-cookie";


Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: ()=>import('../App')
  },
  {
    path: '/login',
    name:'login',
    component:()=>import('../views/login'),
    meta:{
      title:'登录'
    }
  },
  {
    path: '/register',
    name:'register',
    component:()=>import('../views/register'),
    meta:{
      title:'注册'
    }
  },
  {
    path: '/public',
    name:'public',
    component:()=>import('../views/app/public/App'),
    meta: {
      title: '主页面'
    }
  },
  {
    path: '/selectManager',
    name:'selectManager',
    component:()=>import('../views/app/public/App1'),
    meta: {
      title: '角色选择界面'
    }
  },
  {
    path: '/publicManager',
    name:'publicManager',
    component:()=>import('../views/app/manager/public_manager/SuperManager'),
    meta: {
      title: '超级管理员'
    }
  },
  {
    path: '/SuperManager',
    name:'SuperManager',
    component:()=>import('../views/app/manager/super_manager/publicManager'),
    meta: {
      title: '普通管理员'
    }
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(((to, from, next) => {
  //设置放行规则
  if (to.path=='/public'||to.path=='/login'||to.path=='/register'){
    document.title=to.meta.title;
    next();
    return;
  }
  //如果没有权限不能访问所有路径
  if (cookie.get('Authentication')==undefined||cookie.get('Authentication')==''){
    document.title=to.meta.title;
    next('/public');
    return;
  }
  document.title=to.meta.title;
  next();
}))

export default router
