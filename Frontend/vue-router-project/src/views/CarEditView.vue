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
    created() {
        // TODO: Set the carEditData to pass into the carEditComponent
        
    }
}
</script>

<style>

</style>