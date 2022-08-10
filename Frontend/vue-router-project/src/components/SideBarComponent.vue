<template>
  <div class="p-3">
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
      <div class="form-group row">
        <label for="inputStock" class="me-2 col-sm-2 col-form-label">Stock:</label>
        <div class="col-sm-9">
          <input v-model="filters.stock" type="text" class="form-control" id="inputStock">
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

      <button class="btn bg-blue-2 text-white my-2" style="background-color: #646FD4;" type="submit">Search</button>
      <button class="btn bg-blue-2 text-white ms-2 my-2" style="background-color: #9BA3EB;" @click="clearFilters"
        type="button">Clear</button>
    </form>
    <ul class="list-unstyled mt-5">
      <div>
        <h2 class="">User actions</h2>
        <li class="border-top border-dark my-3"></li>
        <router-link to="/cars/new" class="btn text-white mb-5" style="background-color: #70be75;" tag="button">New car
        </router-link>
      </div>
      <div v-if="activeUser.admin">
        <h2>Admin actions</h2>
        <li class="border-top border-dark my-3"></li>
        <li>
          <button class="btn text-dark mb-3" style="background-color: #EBD671;">Reset database</button>
        </li>
        <li>
          <button class="btn btn-danger" style="background-color: #EB5353;">Flush database</button>
        </li>
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
        region: "",
        stock: ""
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
  },
  methods: {
    clearFilters() {
      this.filters.brand = "";
      this.filters.model = "";
      this.filters.region = "";
      this.filters.stock = "";
      this.emitter.emit("cars-filter-changed", this.filters);
    },
    handleSearch() {
      this.emitter.emit("cars-filter-changed", this.filters);
    }
  }
};
</script>

<style>
</style>
