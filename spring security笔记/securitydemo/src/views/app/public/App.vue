<template>
<div>
  <h1>欢迎来到主页面!</h1>
  <el-button type="primary" @click="login">登录</el-button>
  <el-button type="primary" @click="register">注册</el-button>
</div>
</template>

<script>
import router from "@/router";

export default {
  name: "App",
  methods:{
    login(){
      router.push({path:"/login"})
    },
    register(){
      router.push({path:'/register'})
    }
  }
}
</script>

<style scoped>
h1{
  display: block;
  margin-left: 530px;
  color: rebeccapurple;
}

</style>