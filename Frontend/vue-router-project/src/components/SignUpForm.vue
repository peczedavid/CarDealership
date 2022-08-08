<template>
  <div class="container col-lg-4 mt-5">
    <form @submit.prevent="handleRegistration">
      <div class="mb-3">
        <label for="usernameInput" class="form-label">Username:</label>
        <input v-model="user.username" required class="form-control" id="usernameInput" />
      </div>
      <div class="mb-3">
        <label for="passwordInput" class="form-label">Password:</label>
        <input v-model="user.password" required type="password" class="form-control" id="passwordInput" />
      </div>
      <div class="mb-3">
        <label for="regionSelect" class="me-2">Region:</label>
        <select required v-model="user.region" class="" id="regionSelect">
          <option value="" disabled selected>Select your region</option>
          <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
        </select>
      </div>
      <button type="submit" class="btn text-white" style="background-color: #646FD4;">Register</button>
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
        password: '',
        region: '',
        admin: false
      },
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
      axios
        .post("/user/register", this.user)
        .then((result) => {
          // Tell navbar that someone logged in (NavBar.activeUser=result.data)
          this.emitter.emit("sign-in-form", result.data);
          // Go back 1 page
          //this.$router.go(-1);
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  }
};
</script>

<style></style>
