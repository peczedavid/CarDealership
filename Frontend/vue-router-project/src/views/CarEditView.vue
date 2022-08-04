<template>
  <CarEditComponent :carEditData=this.carEditData />
</template>

<script>
import CarEditComponent from '@/components/CarEditComponent.vue'
import axios from "@/http-common"

export default {
    components: {
        CarEditComponent
    },
    data() {
        return {
            carEditData: null
        }
    },
    beforeCreate() {
        axios
            .get("/cars/" + this.$route.params.id)
            .then((result) => {
                this.carEditData = result.data;
            })
            .catch((error) => console.log(error));
    }
}
</script>

<style>

</style>