<template>
  <div class="app">
    <NavBar />
    <router-view />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";
import { store } from "@/data/store"

export default {
  components: {
    NavBar,
  }
};
</script>
  
<style>
</style>
