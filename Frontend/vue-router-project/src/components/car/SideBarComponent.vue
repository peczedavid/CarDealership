<template>
  <div>
    <FiltersComponent class="mb-3" :admin=currentUser.admin></FiltersComponent>
    <UserActionsComponent class="mb-3"></UserActionsComponent>
    <AdminActionsComponent v-if="currentUser.admin"></AdminActionsComponent>
  </div>
</template>

<script>
import FiltersComponent from "@/components/car/FiltersComponent.vue";
import UserActionsComponent from "../user/UserActionsComponent.vue";
import AdminActionsComponent from "../user/AdminActionsComponent.vue";
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
