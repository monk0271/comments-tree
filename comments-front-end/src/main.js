import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import Vuex from 'vuex'
import VueAxios from "vue-axios"; //引入状态管理

Vue.use(ElementUI)
Vue.prototype.$axios= axios
//Vue.use=(VueAxios,axios)
Vue.use(Vuex)
Vue.config.productionTip = false

//全局默认配置
//axios.defaults.headers.common['Authorization'] = store.state.Authorization;
axios.defaults.headers['Authorization'] = store.state.token

axios.interceptors.request.use(
    config => {
      // if (localStorage.getItem('Authorization')) {
      //   config.headers.common['Authorization'] = store.state.Authorization
      // }
      if(store.state.token){
          config.headers['Authorization']=store.state.token
          //config.headers.common['Authorization']=localStorage.getItem('token')
          return config;
      }


      return config;
    },
    error => {
      return Promise.reject(error);
    });



//异步请求后，判断token是否过期
axios.interceptors.response.use (
    response =>{
      return response;
    },
    async (error) => {
     if(error.response){
       const self=this;
        switch (error.response.status) {
          case 401:
            //localStorage.removeItem('Authorization');
              localStorage.removeItem('token');
            await self.$router.push('/');
        }
      }
    }
)

// 导航守卫
// 使用 router.beforeEach 注册一个全局前置守卫，判断用户是否登陆
router.beforeEach((to, from, next) => {
  if(to.meta.requiredAuth){//判断该路由是否需要登录权限
    if(localStorage.getItem('token')!=null){
      next()
    }else {
      next('/login')
    }
  }else {
    if(to.path==='/login' || to.path==='/register' || to.path==='/comment'|| to.path==='/'){
      next()
    }else {
      next('/login')
    }
  }
  // if (to.path === '/login') {
  //   next();
  // } else {
  //   let token = localStorage.getItem('Authorization');
  //   if (token === 'null' || token === '') {
  //     next('/login');
  //   } else {
  //     next();
  //   }
  // }
});

// router.beforeEach((to,from,next) =>{
//   //console.log(to);
//   //console.log(from);
//   if (to.meta.requiredAuth){//判断该路由是否需要登录权限
//     if(localStorage.getItem('username')){//判断本地是否存在username
//       next()
//     }else {
//       if (to.path=='/login'){//是否为登录注册页
//         next()
//       }else {
//         next({
//           path:'/login'
//         })
//       }
//     }
//   }
//   else {
//     next()
//   }
//   /*如果本地存在username则不允许直接跳转到登录页面*/
//   if (to.fullPath=='/login'){
//     if (localStorage.getItem('username')){
//       next({
//         path:from.fullPath
//       })
//     }else {
//       next()
//     }
//   }
//
//
// })


new Vue({
  router,
  store:store,
  render: h => h(App)
}).$mount('#app')
