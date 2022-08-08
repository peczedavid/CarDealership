<template>
  <div class="p-4 bg-white" style="width: 350px">
    <ul class="list-unstyled">
      <h2 class="">Filter</h2>
      <li class="border-top border-dark my-3"></li>
    </ul>
    <form @submit.prevent="handleSearch">
      <div class="form-group row">
        <label for="inputBrand" class="me-2 col-sm-2 col-form-label">Brand:</label>
        <div class="col-sm-9">
          <input v-model="filters.brand" type="text" class="form-control" id="inputBrand">
        </div>
      </div>
      <div class="form-group row">
        <label for="inputModel" class="me-2 col-sm-2 col-form-label">Model:</label>
        <div class="col-sm-9">
          <input v-model="filters.model" type="text" class="form-control" id="inputModel">
        </div>
      </div>
      <div v-if="activeUser.admin" class="form-group row">
        <label for="inputRegion" class="me-2 col-sm-2 col-form-label">Region:</label>
        <div class="col-sm-9">
          <select v-model="filters.region" class="mt-2" id="regionSelect">
            <option value="" disabled selected>Select a region</option>
            <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
            <option value="">All</option>
          </select>
        </div>
      </div>
      <button class="btn btn-primary my-2" type="submit">Search</button>
    </form>
    <ul class="list-unstyled mt-5">
      <div>
        <h2 class="">User actions</h2>
        <li class="border-top border-dark my-3"></li>
        <router-link to="/cars/new" class="btn btn-success mb-5" tag="button">New car</router-link>
      </div>
      <div v-if="activeUser.admin">
        <h2>Admin actions</h2>
        <li class="border-top border-dark my-3"></li>
        <button class="btn btn-warning">Reset database</button>
      </div>
    </ul>
  </div>
</template>

<script>
import axios from '@/http-common';
import { store } from '@/data/store'

export default {
  data() {
    return {
      filters: {
        brand: "",
        model: "",
        region: ""
      },
      regions: [],
      activeUser: {}
    }
  },
  async created() {
    axios
      .get("/regions")
      .then((result) => {
        this.regions = result.data;
      })
      .catch((error) => console.log(error));

    await store.loadCurrentUser();
    this.activeUser = store.currentUser;

    this.getFilteredCars();
  },
  methods: {
    getFilteredCars() {
      // TODO: CHECK THIS ON BACKEND TOO
      // A non admin user can only see cars from their region(this is checked in the backend too)
      let url = "/cars?"
      if (this.filters.brand !== "") url = url.concat("brand=" + this.filters.brand + "&");
      if (this.filters.model !== "") url = url.concat("model=" + this.filters.model + "&");
      if (!this.activeUser.admin) url = url.concat("region=" + this.activeUser.region.name + "&");
      else {
        if (this.filters.region !== "")
          url = url.concat("region=" + this.filters.region + "&");
      }
      url = url.slice(0, -1); // Remove last & symbol
      axios.get(url)
        .then((result) => {
          this.$emit("carsChanged", result.data);
        })
        .catch((error) => console.log(error));
    },
    handleSearch() {
      this.getFilteredCars();
    }
  }
};
</script>

<style>
</style>
