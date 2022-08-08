<template>
  <div class="container col-lg-4 mt-5">
    <form @submit.prevent="handleLogin">
      <div class="mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input v-model="user.username" required class="form-control" id="usernameInput" />
      </div>
      <div class="mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <input v-model="user.password" required type="password" class="form-control" id="passwordInput" />
      </div>
      <button type="submit" class="btn text-white" style="background-color: #646FD4;">Login</button>
    </form>
  </div>
</template>

<script>
import axios from "@/http-common"

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
        .post("/user/login", this.user)
        .then((result) => {
          // Tell navbar that someone logged in (NavBar.activeUser=result.data)
          this.emitter.emit("sign-in", result.data);
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  },
};
</script>

<style>
.fill { 
    min-height: 100%;
    height: 100%;
}
</style>
