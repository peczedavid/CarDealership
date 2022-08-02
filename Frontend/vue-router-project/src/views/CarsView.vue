<template>
  <div class="container">
    <CarComponent v-for="car in cars" :key="car.id" :carData="car" />
  </div>
</template>

<script>
import CarComponent from "@/components/CarComponent.vue";

export default {
  name: "CarsView",
  data() {
    return {
      cars: [
        {
          id: 1,
          brand: "Telsa",
          model: "Model S",
          regions: [
            {
              id: 1,
              name: "America",
            },
          ],
        },
        {
          id: 5,
          brand: "Volkswagen",
          model: "Golf 4",
          regions: [
            {
              id: 2,
              name: "Germany",
            },
          ],
        },
        {
          id: 3,
          brand: "Ford",
          model: "Focus RS",
          regions: [
            {
              id: 1,
              name: "America",
            },
          ],
        },
      ],
    };
  },
  components: { CarComponent },
};
</script>

<style></style>
