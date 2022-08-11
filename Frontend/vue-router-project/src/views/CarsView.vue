<template>
  <div class="row m-0 p-0">
    <div class="col-3 p-0 m-0">
      <SideBarComponent @carsChanged="this.cars = $event" />
    </div>
    <div class="col-9">
      <div class="col-12 d-flex">
        <div class="col-8"></div>
        <div class="col-4">
          <select v-model="sortingType" @change="getFilteredCars" class="my-4">
            <option value="brand-a-z">Sort by: Brand (A-Z)</option>
            <option value="brand-z-a">Sort by: Brand (Z-A)</option>
            <option value="stock-desc">Stock High to Low </option>
            <option value="stock-asc">Stock Low to High</option>
          </select>
        </div>
      </div>
      <div class="col-12 m-0 p-0">
        <CarComponent class="col-6" style="margin-left: 225px;" v-for="car in cars" :key="car.id" :carData="car" />
        <h1>{{ this.message }}</h1>
      </div>
    </div>
    <div class="text-center my-3">
      <button class="btn text-white" style="background-color: #9BA3EB;" @click="backToTop">Back to top</button>
    </div>
  </div>
</template>

<script>
import CarComponent from "@/components/CarComponent.vue";
import SideBarComponent from "@/components/SideBarComponent.vue";
import axios from "@/http-common"
import { store } from "@/data/store"

export default {
  name: "CarsView",
  methods: {
    backToTop() {
      document.documentElement.scrollTop = 0;
    },
    getAllCars() {
      this.getFilteredCars();
    },
    getFilteredCars() {
      if (!this.activeUser)
        this.$router.push("/unauthorized");

      document.cookie = "sortingType=" + this.sortingType;

      let url = "/cars?"
      if (this.filters.brand !== "") url = url.concat("brand=" + this.filters.brand + "&");
      if (this.filters.model !== "") url = url.concat("model=" + this.filters.model + "&");
      if (!this.activeUser.admin) url = url.concat("region=" + this.activeUser.region.name + "&");
      else {
        if (this.filters.region !== "")
          url = url.concat("region=" + this.filters.region + "&");
      }
      url = url.concat("sort=" + this.sortingType);

      axios.get(url)
        .then((result) => {
          this.cars = result.data;

          // Filter on frontend by stock range:
          this.cars = this.cars.filter(car => car.stock >= this.filters.stockLow && car.stock <= this.filters.stockTop);
        })
        .catch((error) => {
          if (error.response.status == 401)
            this.$router.push("/unauthorized");
        });
    },
  },
  watch: {
    cars(newValue) {
      if (newValue.length == 0)
        this.message = "No cars found";
      else
        this.message = "";
    }
  },
  async beforeMount() {
    await store.loadCurrentUser();
    this.activeUser = store.currentUser;
    this.getAllCars();
  },
  created() {
    // Read in the sorting cookie
    const sortingTypeFromCookie = store.getCookie("sortingType");
    if (sortingTypeFromCookie !== "")
      this.sortingType = sortingTypeFromCookie;
  },
  mounted() {
    this.emitter.on("cars-filter-changed", filters => {
      this.filters = filters;
      this.getFilteredCars(this.filters);
    });
  },
  data() {
    return {
      cars: [],
      message: "",
      sortingType: "stock-desc",
      filters: {
        brand: "",
        model: "",
        region: "",
        stockLow: 0,
        stockTop: Infinity
      },
      activeUser: null
    };
  },
  components: {
    CarComponent,
    SideBarComponent,
  },
};
</script>

<style>
</style>
