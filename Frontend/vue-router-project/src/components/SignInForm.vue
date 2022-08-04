<template>
  <div class="container col-lg-4">
    <form @submit.prevent="handleLogin">
      <div class="mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input v-model="user.username" required class="form-control" id="usernameInput" />
      </div>
      <div class="mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <input v-model="user.password" required type="password" class="form-control" id="passwordInput" />
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
</template>

<script>
import axios from "@/http-common"
import { store } from "@/data/store"; 

export default {
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
    };
  },
  methods: {
    handleLogin() {
      axios
        .post("/auth/signin", this.user)
        .then((result) => {
          // Tell navbar that someone logged in (NavBar.activeUser=result.data)
          this.emitter.emit("sign-in-form", result.data);
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  },
};
</script>

<style>
</style>
