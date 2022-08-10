<template>
  <div class="app bg-light bg-gradient vh-100">
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
.fillHeight {
  min-height: 100%;
  height: 100%;
}
</style>
