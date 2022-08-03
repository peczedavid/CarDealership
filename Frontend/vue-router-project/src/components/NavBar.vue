<template>
  <nav class="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
    <div class="container-fluid">
      <a class="navbar-brand" href="/">Dealership</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link to="/" class="nav-link active">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link to="/cars" class="nav-link active">Cars</router-link>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li v-if="activeUser" class="navbar-nav">
            <router-link to="/" class="btn btn-outline-light me-2" tag="button">Profile</router-link>
          </li>
          <li v-if="activeUser" class="navbar-nav">
            <router-link @click="signOut" to="#" class="btn btn-outline-light me-2" tag="button">Log out</router-link>
          </li>
          <li v-if="!activeUser" class="navbar-nav">
            <router-link to="/signin" class="btn btn-outline-light me-2" tag="button">Log in</router-link>
          </li>
          <li v-if="!activeUser" class="navbar-nav">
            <router-link to="/signup" class="btn btn-outline-light me-2" tag="button">Register</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>

export default {
  data() {
    return {
      activeUser: null
    };
  }
};
</script>

<style>
</style>
