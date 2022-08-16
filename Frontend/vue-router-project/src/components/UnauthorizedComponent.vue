<template>
  <div class="container">
    <div class="col text-center mt-5">
      <h1>{{ this.message }}</h1>
    </div>
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