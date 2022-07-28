<template>
<div>
  <h1>
    成功的访问超级管理员!
  </h1>
</div>
</template>

<script>
export default {
  name: "SuperManager",
  mounted() {
    this.$store.dispatch("agent").then(result=>{
      this.$message.info("访问成功!")
    }).catch(error=>{
      console.log(error)
    })
  }
}
</script>

<style scoped>

</style>