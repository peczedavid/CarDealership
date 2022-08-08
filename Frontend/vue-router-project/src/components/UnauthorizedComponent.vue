<template>
  <div class="container">
    <h1>{{ this.message }}</h1>
  </div>
</template>

<script>
import { store } from '@/data/store'

export default {
    created() {
        this.message = store.unauthorizedMessage;
    },
    data() {
        return {
            message: ""
        }
    }
}
</script>

<style>

</style>