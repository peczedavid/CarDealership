<template>
<div class="container mx-auto col-9 mt-5 mh-100">
    <AdminBoardComponent />
</div>
</template>

<script>
import AdminBoardComponent from '@/components/AdminBoardComponent.vue';
import axios from "@/http-common";

export default {
    components: {
        AdminBoardComponent,
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