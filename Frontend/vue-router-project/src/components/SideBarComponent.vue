<template>
  <div class="felx-shrink-0 p-4 bg-white" style="width: 350px">
    <ul class="list-unstyled">
      <h2 class="">Filter</h2>
      <li class="border-top border-dark my-3"></li>
    </ul>
    <form @submit.prevent="handleSearch">
      <div class="form-group row">
        <label for="inputBrand" class="me-2 col-sm-2 col-form-label">Brand:</label>
        <div class="col-sm-9">
          <input v-model="brand" type="text" class="form-control" id="inputBrand">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputModel" class="me-2 col-sm-2 col-form-label">Model:</label>
        <div class="col-sm-9">
          <input v-model="model" type="text" class="form-control" id="inputModel">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputRegion" class="me-2 col-sm-2 col-form-label">Region:</label>
        <div class="col-sm-9">
          <select v-model="region" class="mt-2" id="regionSelect">
            <option value="" disabled selected>Select region</option>
            <!--TODO: ask from the database the available regions-->
            <option value="america">America</option>
            <option value="germany">Germany</option>
            <option value="japan">Japan</option>
            <option value="">All</option>
          </select>
        </div>
      </div>
      <button class="btn btn-primary mb-2" type="submit">Search</button>
    </form>
    <ul class="list-unstyled mt-5">
      <h2 class="">Admin actions</h2>
      <li class="border-top border-dark my-3"></li>
      <router-link to="/cars/new" class="btn btn-success" tag="button">New car</router-link>
    </ul>
  </div>
</template>

<script>
import axios from '@/http-common';

export default {
  data() {
    return {
      brand: "",
      model: "",
      region: ""
    }
  },
  methods: {
    handleSearch() {
      let url = "/cars?"
      if (this.brand !== "") url = url.concat("brand=" + this.brand + "&");
      if (this.model !== "") url = url.concat("model=" + this.model + "&");
      if (this.region !== "") url = url.concat("region=" + this.region + "&");
      url = url.slice(0, -1); // Remove last & symbol
      axios.get(url)
        .then((result) => {
          this.$emit("carsChanged", result.data);
        })
        .catch((error) => console.log(error));
    }
  }
};
</script>

<style>
</style>
