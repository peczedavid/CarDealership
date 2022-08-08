<template>
  <div class="d-inline-flex col-12">
    <SideBarComponent class="col-lg-3 col-md-6 col-sm-6" @carsChanged="this.cars = $event"/>
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
      // TODO: sort by brand or stock
      axios
        .get("/cars")
        .then((result) => {
          this.cars = result.data;
        })
        .catch((error) => {
          if(error.response.status == 401)
            this.$router.push("/unauthorized");
        });
    }
  },
  watch: {
    cars(newValue) {
      if(newValue.length == 0)
            this.message = "No cars found";
    }
  },
  async beforeMount() {
    await store.loadCurrentUser();
    this.activeUser = store.currentUser;
    this.getCars()
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

<style></style>
