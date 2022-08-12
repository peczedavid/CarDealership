<template>
  <div class="position-fixed col-3 ps-4 pt-5">
    <FiltersComponent :admin=currentUser.admin></FiltersComponent>
    <UserActionsComponent></UserActionsComponent>
    <AdminActionsComponent v-if="currentUser.admin"></AdminActionsComponent>
  </div>
</template>

<script>
import FiltersComponent from "@/components/FiltersComponent.vue";
import UserActionsComponent from "./UserActionsComponent.vue";
import AdminActionsComponent from "./AdminActionsComponent.vue";
import { store } from "@/data/store";

export default {
  data() {
    return {
      currentUser: {},
    }
  },
  async created() {
    await store.loadCurrentUser();
    this.currentUser = store.currentUser;
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
