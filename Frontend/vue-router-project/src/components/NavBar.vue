<template>
  <nav class="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">Dealership</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link v-if="activeUser" to="/cars" class="nav-link active">Cars</router-link>
          </li>
        </ul>
        <ul class="navbar-nav">
          <li v-if="activeUser" class="navbar-nav">
            <h6 class="text-light me-4 my-auto">{{ activeUser.username }}</h6>
          </li>
          <li v-if="activeUser" class="navbar-nav">
            <router-link to="/profile" class="btn btn-outline-light me-2" tag="button">Profile</router-link>
          </li>
          <li v-if="activeUser" class="navbar-nav">
            <router-link @click="signOut" to="/" class="btn btn-outline-light me-2" tag="button">Log out</router-link>
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
import axios from '@/http-common';
import { store } from '@/data/store';

export default {
  data() {
    return {
      activeUser: null
    };
  },
  methods: {
    signOut() {
      axios
      .post("/user/logout")
      .then((result) => {
        this.activeUser = null;
        store.currentUser = null;
        // Refresh page so cookie dissappears
        this.$router.go();
      });
    }
  },
  async created() {
    await store.loadCurrentUser();
    this.activeUser = store.currentUser;
  },
  mounted() {
    this.emitter.on("sign-in-form", data => {
      this.activeUser = data;
    });
  }
};
</script>

<style>
</style>
