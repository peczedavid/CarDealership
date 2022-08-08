<template>
  <div class="row">
    <SideBarComponent @carsChanged="this.cars = $event"/>
    <div class="container col-lg-6 col-sm-6">
      <CarComponent v-for="car in cars" :key="car.id" :carData="car" />
      <h1>{{ this.message }}</h1>
    </div>
  </div>
</template>

<script>
import CarComponent from "@/components/CarComponent.vue";
import SideBarComponent from "@/components/SideBarComponent.vue";
import axios from "@/http-common"

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
            this.message = "Unauthorized, log into your account or create one!";
        });
    }
  },
  watch: {
    cars(newValue) {
      if(newValue.length == 0)
            this.message = "No cars found";
    }
  },
  beforeMount() {
    this.getCars()
  },
  data() {
    return {
      cars: [],
      message: ""
    };
  },
  components: { CarComponent, SideBarComponent },
};
</script>

<style></style>
