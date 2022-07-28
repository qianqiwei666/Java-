import axios from "axios";
import cookie from 'js-cookie'
import {Message} from "element-ui";
import router from "@/router";




let network = axios.create({
    baseURL:'http://localhost:8081',
});


// 添加请求拦截器
network.interceptors.request.use(function (config) {
    let token=cookie.get('Authentication');
    config.headers.Authentication=token;
    console.log(config);
    return config;
}, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
});

// 添加响应拦截器
network.interceptors.response.use(function (response) {
    return response;
}, function (error) {
    if (error.response.status==401){
        cookie.remove('Authentication');
        Message.error("未验证,请登录!");
        router.push({path:'/login'})
    }else if (error.response.status==403){
        Message.error("您无权限访问!")
        //返回到上一个访问界面
        history.back();
    }
    return Promise.reject(error);
});


function verification_code(){
    return network({
        method:'GET',
        url:'/getCode',
    })
}

function login(username,password,code){
    return network({
        method:'post',
        url:'/app/login',
        params:{
            username,password,code
        }
    })
}

function register(username,password,code){
    return network({
        method:'post',
        url:'/app/register',
        params:{
            username,password,code
        }
    })
}

function agent(){
    return network({
        method:'get',
        url:'/agent',
    })
}
export {verification_code,login,register,agent}

