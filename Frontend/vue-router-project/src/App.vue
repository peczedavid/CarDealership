<template>
    <div class="bg-light">
      <br><br>
      <!-- <br><br><br> -->
      <NavigationBarComponent/>
      <router-view />
    </div>
</template>

<script>
import NavigationBarComponent from "./components/NavigationBarComponent.vue";

export default {
  components: {
    NavigationBarComponent
}
};
</script>

<style>
</style>
