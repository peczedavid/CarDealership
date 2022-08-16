<template>
  <div class="dropdown dropend">
    <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
      data-bs-auto-close="outside">
      <fa class="display-4" icon="user"></fa>
    </button>
    <div class="dropdown-menu fadeIn">
      <div class="container p-2">
        <div class="d-flex justify-content-center align-items-center">
          <router-link to="/cars/new" class="btn text-white" style="background-color: #70be75;" tag="button">
          New car
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
@keyframes fadeIn {
  from {
    opacity: 0%;
    translate: 0px 50px;
  }

  to {
    opacity: 100%;
    translate: 0px 0px;
  }
}

.fadeIn {
  animation: fadeIn 0.3s;
}
</style>