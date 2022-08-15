<template>
<div class="container mx-auto col-9 mt-5 mh-100">
    <!-- <AdminBoardComponent /> -->
    <AdminDashboardComponent />
</div>
</template>

<script>
import axios from "@/http-common";
import AdminDashboardComponent from '@/components/AdminDashboardComponent.vue';

export default {
    components: {
    AdminDashboardComponent
},
    created() {
        axios
            .get("/user")
            .then((result) => {
                if(result.status == 204 || !result.data.admin)
                    this.$router.push("/unauthorized");
            })
            .catch((error) => console.log(error));
        
    },
    data() {
        return {
            activeUser: null
        }
    }
}
</script>

<style>
</style>