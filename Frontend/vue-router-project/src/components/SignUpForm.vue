<template>
  <div class="container">
    <div class="card p-5 col-lg-4 mt-5 mx-auto">
      <form @submit.prevent="handleRegistration">
        <div class="mb-3 form-group">
          <h2>Register new user</h2>
        </div>
        <div class="mb-3 form-group">
          <label for="usernameInput" class="form-label">Username:</label>
          <input v-model="user.username" required class="form-control" id="usernameInput" />
        </div>
        <div class="mb-3 form-group">
          <label for="passwordInput" class="form-label">Password:</label>
          <input v-model="user.password" required autocomplete="new-password" type="password" class="form-control"
            id="passwordInput" />
        </div>
        <div class="mb-3 form-group">
          <label for="passwordAgainInput" class="form-label">Password again:</label>
          <input v-model="passwordAgain" required type="password" class="form-control" id="passwordAgainInput" />
        </div>
        <div class="mb-3 form-group">
          <label for="regionSelect" class="me-2">Region:</label>
          <select required v-model="user.region" class="" id="regionSelect">
            <option value="" disabled selected>Select your region</option>
            <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
          </select>
        </div>
        <button type="submit" class="btn text-white" style="background-color: #646FD4;">Register</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "@/http-common"

export default {
  name: 'SignUpForm',
  data() {
    return {
      user: {
        username: '',
        password: '',
        region: '',
        admin: false
      },
      passwordAgain: '',
      regions: []
    }
  },
  created() {
    axios
      .get("/regions")
      .then((result) => {
        this.regions = result.data;
      })
      .catch((error) => console.log(error));
  },
  methods: {
    handleRegistration() {
      if (this.user.password != this.passwordAgain) {
        alert("Passwords are not matching!");
        return;
      }

      axios
        .post("/user/register", this.user)
        .then((result) => {
          // Tell navbar that someone logged in (NavBar.activeUser=result.data)
          this.emitter.emit("sign-in", result.data);
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  }
};
</script>

<style>
</style>
