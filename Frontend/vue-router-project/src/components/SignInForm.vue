<template>
  <div class="container col-lg-4">
    <form @submit.prevent="handleLogin">
      <div class="mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input
          v-model="user.username"
          required
          class="form-control"
          id="usernameInput"
        />
      </div>
      <div class="mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <input
          v-model="user.password"
          required
          type="password"
          class="form-control"
          id="passwordInput"
        />
      </div>
      <button type="submit" class="btn btn-primary">Login</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

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
    async handleLogin() {
      axios
        .post("http://localhost:8080/api/auth/signin", this.user, {
          withCredentials: true,
        })
        .then((result) => {
          console.log(result.data.username);
          this.$router.push('/');
        })
        .catch((error) => alert("Wrong username or password!"));
    },
  },
};
</script>

<style></style>
