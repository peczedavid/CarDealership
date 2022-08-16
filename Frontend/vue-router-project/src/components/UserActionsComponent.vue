<template>
  <div class="dropdown dropend">
      <button type="button" class="btn dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"
        data-bs-auto-close="outside">
        <fa class="display-4" icon="user"></fa>
      </button>
      <div class="dropdown-menu fadeIn">
        <router-link to="/cars/new" class="btn text-white mb-5" style="background-color: #70be75;" tag="button">New car
        </router-link>
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
    }

    to {
        opacity: 100%;
    }
}

.fadeIn {
    animation: fadeIn 0.3s;
}
</style>