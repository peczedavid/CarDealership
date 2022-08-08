<template>
  <div class="container">
    <div class="card p-4 col-lg-4 mt-5 mx-auto">
      <form @submit.prevent="handleLogin">
      <div class="mb-3 form-group">
          <h2>Log in to account</h2>
        </div>
      <div class="form-group mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input v-model="user.username" required class="form-control" id="usernameInput" />
      </div>
      <div class="form-group mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <div>
          <div class="col-11 d-inline-flex">
            <input v-if="passwordHidden" v-model="user.password" required type="password" class="d-inline form-control"
              id="passwordInput" />
            <input v-if="!passwordHidden" v-model="user.password" required type="text" class="d-inline form-control"
              id="passwordInput" />
          </div>
          <div class="col-1 d-inline-flex">
            <button class="btn" @click="togglePassword" type="button">
              <fa icon="eye"></fa>
            </button>
          </div>
        </div>
      </div>
      <button type="submit" class="btn text-white" style="background-color: #646FD4;">Login</button>
    </form>
    </div>
    
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
      passwordHidden: true
    };
  },
  methods: {
    togglePassword() {
      this.passwordHidden = !this.passwordHidden;
    },
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
