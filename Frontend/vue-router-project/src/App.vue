<template>
  <div class="app bg-light bg-gradient">
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
