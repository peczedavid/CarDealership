<template>
  <div class="row">
    <SideBarComponent @carsChanged="this.cars = $event"/>
    <div class="container col-lg-6 col-sm-6">
      <CarComponent v-for="car in cars" :key="car.id" :carData="car" />
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
      axios
        .get("/cars")
        .then((result) => {
          this.cars = result.data;
        })
        .catch((error) => alert(error));
    }
  },
  beforeMount() {
    this.getCars()
  },
  data() {
    return {
      cars: []
    };
  },
  components: { CarComponent, SideBarComponent },
};
</script>

<style></style>
