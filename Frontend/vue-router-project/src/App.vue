<template>
    <div class="h-100">
      <br><br>
      <NavigationBarComponent/>
      <router-view/>
      <FooterComponent/>
      <br><br><br>
    </div>
</template>

<script>
import NavigationBarComponent from "./components/NavigationBarComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
import { store } from "@/data/store";

export default {
  components: {
    NavigationBarComponent,
    FooterComponent
  },
  created() {
    store.loadCurrentUser();
  }
};
</script>

<style>
/* width */
::-webkit-scrollbar {
  width: 12px;
}

/* Track */
::-webkit-scrollbar-track {
  background: #f8f9fa;
  border-left: 1px solid #343a40;
  box-shadow: inset 0 0 5px #acacac;
}
 
/* Handle */
::-webkit-scrollbar-thumb {
  background: #343a40; 
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #6c757d; 
}
</style>
