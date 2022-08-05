<template>
  <div class="container col-lg-4">
    <form @submit.prevent="handleRegistration">
      <div class="mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input v-model="user.username" required class="form-control" id="usernameInput" />
      </div>
      <div class="mb-3">
        <label for="emailInput" class="form-label">Email:</label>
        <input v-model="user.email" required type="email" class="form-control" id="emailInput" />
      </div>
      <div class="mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <input v-model="user.password" required type="password" class="form-control" id="passwordInput" />
      </div>
      <div class="mb-3">
        <label for="regionSelect" class="me-2">Region:</label>
        <select required v-model="user.regions[0]" class="" id="regionSelect">
          <option value="" disabled selected>Select your region</option>
          <!--TODO: ask from the database the available regions-->
           <option value="america">America</option>
           <option value="germany">Germany</option>
           <option value="japan">Japan</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary">Register</button>
    </form>
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
        email: '',
        password: '',
        regions: ['']
      }
    }
  },
  methods: {
    handleRegistration() {
      axios
        .post("/auth/signup", this.user)
        .then((result) => {
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  }
};
</script>

<style></style>
