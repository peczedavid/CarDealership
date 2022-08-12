<template>
  <div class="app bg-light bg-gradient">
    <NavBar />
    <router-view />
  </div>
</template>

<script>
import NavBar from "@/components/NavBar.vue";

export default {
  components: {
    NavBar,
  }
};
</script>

<style>
</style>
