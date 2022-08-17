<template>
    <div class="h-100">
      <br><br>
      <NavigationBarComponent/>
      <router-view/>
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
