import Vue from 'vue'
//import Vuex from 'vuex'
import VueRouter from "vue-router";
import login from "@/views/login";
import comment from "@/views/comment";
import register from "@/views/register";
import store from '@/store/index.js'

Vue.use(VueRouter)

// 页面刷新时，重新赋值token
if (localStorage.getItem('token')) {
  store.commit('Authorization', localStorage.getItem('token'))
}


const routes = [
  {
    path:'/',
    name:'/',
    component:comment
  },
  {
    path:'/comment',
    name:'comment',
    component:comment,
  },
  {
    path:'/login',
    name:'login',
    component:login
  },
  {
    path: '/register',
    name: 'register',
    component: register
  }
]



const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



export default router
