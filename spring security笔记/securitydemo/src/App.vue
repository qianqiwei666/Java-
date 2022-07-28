<template>
  <router-view></router-view>
</template>

<script>
import router from "@/router";

export default {
  name: "App",
  mounted() {
    router.push({path:'/public'})
  }

}
</script>

<style scoped>

</style>