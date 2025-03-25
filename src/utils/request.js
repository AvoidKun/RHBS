//定制请求的实例
import { ElMessage } from 'element-plus'
//导入axios  npm install axios
import axios from 'axios';

import { useTokenStore } from '@/stores/token.js';
// import { useRoute } from 'vue-router';
// const router = useRoute();
import router from '@/router'
//定义一个变量,记录公共的前缀  ,  baseURL
// const baseURL = 'http://localhost:8082';
const baseURL = '/api';

const instance = axios.create({ baseURL })

//添加请求拦截器
instance.interceptors.request.use(
    (config) => {
        //请求前的回调
        //添加token
        const tokenStore = useTokenStore();
        if (tokenStore.token) {
            config.headers.Authorization = tokenStore.token
        }
        return config
    },
    (err) => {
        //请求失败的回调
        Promise.reject(err)
    }
)


//添加响应拦截器
instance.interceptors.response.use(
    result => {
        //成功
        if (result.data.code === 0) {
            return result.data;
        }
        //失败
        ElMessage.error(result.data.msg ? result.data.msg : '服务失败')
        //异步操作状态转为失败
        return Promise.reject(result.data)
    },
    err => {
        //判断相应状态码，如果是401，则证明未登录
        if (err.response.status === 401) {
            ElMessage.error('请先登录')
            router.push('/login')
        } else {
            ElMessage.error('服务异常');
        }
        return Promise.reject(err);//异步的状态转化成失败的状态
    }
)

export default instance;