<template>
<div class="container mx-auto mt-5 mh-100">
    <AdminDashboardComponent />
</div>
</template>

<script>
import AdminDashboardComponent from '@/components/AdminDashboardComponent.vue';
import { store } from "@/data/store";

export default {
    components: {
    AdminDashboardComponent
},
    created() {
        if(!this.currentUser.admin)
            this.$router.push("/unauthorized");
        // axios
        //     .get("/user")
        //     .then((result) => {
        //         if(result.status == 204 || !result.data.admin)
        //             this.$router.push("/unauthorized");
        //     })
        //     .catch((error) => console.log(error));
        
    },
    computed: {
        currentUser() {
            return store.currentUser;
        }
    },
}
</script>

<style>
</style>