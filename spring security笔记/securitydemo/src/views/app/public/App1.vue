<template>
<div>
  <h1>选泽权限进行测试</h1>
  <el-button type="primary" @click="superManager">超级管理员</el-button>
  <el-button type="primary" @click="publicManager">普通管理员</el-button>
</div>
</template>

<script>
import router from "@/router";

export default {
  name: "App1",
  methods:{
    superManager(){
      router.push({path:'/publicManager'})
    },
    publicManager(){
      router.push({path:'/SuperManager'})
    }

  }
}
</script>

<style scoped>

</style>