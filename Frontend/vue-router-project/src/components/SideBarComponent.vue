<template>
  <div>
    <FiltersComponent class="mb-3" :admin=currentUser.admin></FiltersComponent>
    <UserActionsComponent class="mb-3"></UserActionsComponent>
    <AdminActionsComponent v-if="currentUser.admin"></AdminActionsComponent>
  </div>
</template>

<script>
import FiltersComponent from "@/components/FiltersComponent.vue";
import UserActionsComponent from "./UserActionsComponent.vue";
import AdminActionsComponent from "./AdminActionsComponent.vue";
import { store } from "@/data/store";

export default {
  computed: {
    currentUser() {
      return store.currentUser;
    }
  },
  components: {
    FiltersComponent,
    UserActionsComponent,
    AdminActionsComponent
}
};
</script>

<style>
</style>
