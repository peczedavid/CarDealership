<template>
  <NavBar />
  <SignUpForm />
</template>

<script>
import SignUpForm from "./components/SignUpForm.vue";
import NavBar from "./components/NavBar.vue";

export default {
  name: "App",
  components: {
    SignUpForm,
    NavBar,
  },
};
</script>

<style>
</style>
