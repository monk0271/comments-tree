import axios from 'axios'
import config from '@/config/index'
// import store from '@/store'
// import { getToken,removeToken} from '@/utils/auth'

// create an axios instance
const service = axios.create({
 // baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
  baseURL:config.BASE_URL,
  withCredentials: true, // need to be false when cross-domain requests *
  timeout: 5000 // request timeout
})


// response interceptor
service.interceptors.request.use(
    config => {
      if (localStorage.getItem('token')) {
        config.headers.token = localStorage.getItem('token');
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    });

export default service
