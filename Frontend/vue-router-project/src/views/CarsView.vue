<template>
  <div class="d-inline-flex col-12 bg-light bg-gradient">
    <SideBarComponent class="col-lg-3 col-md-6 col-sm-6" @carsChanged="this.cars = $event" />
    <div class="container mt-4">
      <CarComponent class="col-sm-6" style="margin-left: 200px;" v-for="car in cars" :key="car.id" :carData="car" />
      <h1>{{ this.message }}</h1>
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
    getCars() {
      axios
        .get("/cars")
        .then((result) => {
          this.cars = result.data;
        })
        .catch((error) => {
          if (error.response.status == 401)
            this.$router.push("/unauthorized");
        });
    },
    isUnsignedInteger(string) {
      let n = Math.floor(Number(string));
      return n !== Infinity && String(n) == string && n >= 0;
    },
    getFilteredCars(filters) {
      let url = "/cars?"
      if (filters.brand !== "") url = url.concat("brand=" + filters.brand + "&");
      if (filters.model !== "") url = url.concat("model=" + filters.model + "&");
      if (!this.activeUser.admin) url = url.concat("region=" + this.activeUser.region.name + "&");
      else {
        if (filters.region !== "")
          url = url.concat("region=" + filters.region + "&");
      }
      if (filters.stock !== "" && this.isUnsignedInteger(filters.stock))
        url = url.concat("stock=" + filters.stock + "&");
      url = url.slice(0, -1); // Remove last & symbol
      axios.get(url)
        .then((result) => {
          this.cars = result.data;
        })
        .catch((error) => console.log(error));
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
    this.getCars();
  },
  mounted() {
    this.emitter.on("cars-filter-changed", filters => {
      //console.log("Filters changed");
      this.getFilteredCars(filters);
    });
  },
  data() {
    return {
      cars: [],
      message: "",
      activeUser: null
    };
  },
  components: { CarComponent, SideBarComponent },
};
</script>

<style>
</style>
