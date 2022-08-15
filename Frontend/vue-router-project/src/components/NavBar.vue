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
          <div class="col-12 navbar-nav">
            <div class="col-3 d-flex">
              <router-link v-if="activeUser" to="/cars" class="nav-link active">Cars</router-link>
              <label v-if="activeUser" class="nav-link active my-auto">></label>
              <label v-if="activeUser" class="nav-link active">Detalied</label>
              <label v-if="activeUser" class="nav-link active my-auto">></label>
              <label v-if="activeUser" class="nav-link active">Edit</label>
            </div>
            <div class="col-3 d-flex">
              <form v-if="activeUser" class="flex-fill" @submit.prevent="handleSearch">
                <div class="form-group">
                  <input v-model="searchQuery" placeholder="Search" class="form-control" id="searchQueryInput" />
                </div>
              </form>
            </div>
            <div class="col-6 d-flex justify-content-end">
              <h6 v-if="activeUser" class="text-light me-4 my-auto">{{ activeUser.username }}</h6>
              <router-link v-if="activeUser && activeUser.admin" to="/adminboard" class="btn btn-outline-light me-2"
                tag="button">Admin board</router-link>
              <router-link v-if="activeUser" to="/profile" class="btn btn-outline-light me-2" tag="button">Profile
              </router-link>
              <router-link v-if="activeUser" @click="signOut" to="/" class="btn btn-outline-light me-2" tag="button">Log
                out</router-link>
              <router-link v-if="!activeUser" to="/login" class="btn btn-outline-light me-2" tag="button">Log in
              </router-link>
              <router-link v-if="!activeUser" to="/register" class="btn btn-outline-light me-2" tag="button">Register
              </router-link>
            </div>
          </div>
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
      activeUser: null,
      searchQuery: ""
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
    },
    handleSearch() {
      this.$router.push("cars");
      this.emitter.emit("cars-queried", this.searchQuery);
    },
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
