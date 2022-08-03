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
import Cookies from "universal-cookie"
const cookies = new Cookies()
//import { store } from "../data/store"; 

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
    getCookie(cname) {
      var name = cname + '=';
      var decodedCookie = decodeURIComponent(document.cookie);
      var ca = decodedCookie.split(';');
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
          c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
          return c.substring(name.length, c.length);
        }
      }
      return '';
    },
    setCookie(cname, cvalue, exdays) {
      var d = new Date();
      d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
      var expires = 'expires=' + d.toUTCString();
      document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
    },
    async handleLogin() {
      axios
        .post("/auth/signin", this.user)
        .then((result) => {
          // successfull login
          // const userData = {
          //   username: result.data.username,
          //   admin: result.data.roles.includes("ROLE_ADMIN") ? true : false
          // }
          // const userDataString = JSON.stringify(userData);
          // this.setCookie("activeUser", userDataString, 1);
          this.$router.push("/");
        })
        .catch((error) => alert(error));
    },
  },
};
</script>

<style>
</style>
