<template>
    <AdminBoardComponent />
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