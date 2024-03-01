// 封装axios，做请求处理
// 导入axios
import axios from 'axios'
// 导入router
import router from '@/router'

import { ElMessage } from 'element-plus';
import { getToken, removeToken } from './token';

// 创建axios
const request = axios.create({
    // 根请求地址
    baseURL: 'http://127.0.0.1:8088/',
    // baseURL: '/api/',
    withCredentials: false, // 用于配置请求接口跨域时是否需要凭证
    timeout: 30000 // 超时时间，单位ms
})

// 配置请求头的参数类型和编码格式
axios.defaults.headers['Content-Type'] = 'application/json?charset=utf-8'

// 配置请求的拦截器
request.interceptors.request.use((config) => {
    // 在请求头添加token, 判断是否需要发送token
    if (getToken('aoyouToken')) {
        // 添加Bearer 前缀
        config.headers['Authorization'] = 'Bearer ' + getToken('aoyouToken');
    }
    return config;
}, (error) => {
    // 发生异常
    console.log("请求异常====>",error);
    return Promise.reject(error);
})

// 配置响应拦截器
request.interceptors.response.use((response) => {
    // 判断响应码，后端返回的数据是code, data, msg
    let {msg, code} = response.data;
    console.log("code=====>", code, 'msg====>', msg);
    if (code == null) {
        return response;
    } else if (code == 200) {
        return response;
    } else if (code == 500) {
        ElMessage.error(msg);
    } else if (code == 403) {
        ElMessage.error('没有操作权限！');
    } else if (code == 401) {
        ElMessage.error('登录过期！');
        // 需要重新登陆，清除用户的相关数据，然后跳转到登录页面
        window.sessionStorage.clear();
        window.localStorage.clear();
        router.push('/login');
    }
    return Promise.reject(msg);
}, (error) => {
    // 出现异常
    ElMessage.error('error=====>', error);
    window.sessionStorage.clear();
    window.localStorage.clear();
    router.push('/login');
    return Promise.reject(error);
})

// 导出
export default request;
