<template>
  <div>
    <br><br>
    <nav class="navbar fixed-top navbar-expand-sm navbar-dark bg-gradient" style="background-color: #242F9B;">
      <div class="container-fluid">
        <router-link class="ms-1 me-3 navbar-brand" to="/">Dealership</router-link>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <router-link v-if="activeUser" to="/cars" class="nav-link active">Cars</router-link>
            </li>
            <li class="nav-item">
              <label class="nav-link active my-auto">></label>
            </li>
            <li class="nav-item">
              <label v-if="activeUser" class="nav-link active">Detalied</label>
            </li>
            <li class="nav-item">
              <label class="nav-link active my-auto">></label>
            </li>
            <li class="nav-item">
              <label v-if="activeUser" class="nav-link active">Edit</label>
            </li>
          </ul>
          <ul class="navbar-nav">
            <li v-if="activeUser" class="navbar-nav">
              <h6 class="text-light me-4 my-auto">{{ activeUser.username }}</h6>
            </li>
            <li v-if="activeUser && activeUser.admin" class="navbar-nav">
              <router-link to="/adminboard" class="btn btn-outline-light me-2" tag="button">Admin board</router-link>
            </li>
            <li v-if="activeUser" class="navbar-nav">
              <router-link to="/profile" class="btn btn-outline-light me-2" tag="button">Profile</router-link>
            </li>
            <li v-if="activeUser" class="navbar-nav">
              <router-link @click="signOut" to="/" class="btn btn-outline-light me-2" tag="button">Log out</router-link>
            </li>
            <li v-if="!activeUser" class="navbar-nav">
              <router-link to="/login" class="btn btn-outline-light me-2" tag="button">Log in</router-link>
            </li>
            <li v-if="!activeUser" class="navbar-nav">
              <router-link to="/register" class="btn btn-outline-light me-2" tag="button">Register</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
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
    this.emitter.on("sign-in", data => {
      this.activeUser = data;
    });
  }
};
</script>

<style lang="scss">
</style>
