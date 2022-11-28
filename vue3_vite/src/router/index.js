import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
    {
        path: '/',
        redirect: '/login'
    },
    {
        path:'/login',
        name:'Login',
        component: ()=>import('../pages/Login.vue')
    },
    {
        path: '/home',
        name:'Home',
        component: ()=>import('../pages/Home.vue')
    },
    {
        path:'/user',
        name:'User',
        component: ()=>import('../pages/User.vue')
    },
    {
        path:'/addorder',
        name:'addorder',
        component:()=>import('../pages/AddOrder.vue')
    },
    {
        path:'/address',
        name:'address',
        component:()=>import('../pages/Address.vue')
    },
    {
        path:'/order',
        name:'order',
        component:()=>import('../pages/Order.vue')
    },
    {
        path:'/edituser',
        name:'edituser',
        component:()=>import('../pages/Edituser.vue')
    },
    {
        path:'/addressedit',
        name:'addressedit',
        component:()=>import('../pages/AddressEdit.vue')
    },
]

const router = createRouter({
    history: createWebHashHistory(), // 路径格式
    routes: routes, // 路由数组
})

router.beforeEach(async (to, from) => {    //to:去往的路由 from:当前路由
    const phone = sessionStorage.getItem("userPhone")
        if (
            phone === null  &&
            to.name !== 'Login'
          ) {
            alert('未登录')
            // 将用户重定向到登录页面
            return { name: 'Login' }
          }
  })

export default router