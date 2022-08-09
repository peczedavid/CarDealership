<template>
  <div class="card mb-3">
    <!-- <div class="card mb-3" style="max-width: 540px;"> -->
    <div class="row g-0">
      <div class="col-md-4">
        <!-- <img src="https://placehold.jp/200x200.png" class="img-fluid rounded-start" alt="..."> -->
        <img src="../data/car-placeholder.png" class="img-fluid rounded-start" alt="...">
      </div>
      <div class="col-md-8">
        <div class="card-body p-0 ps-3 pt-3">
          <h5 class="card-title">{{ carData.brand + " " + carData.model }}</h5>
          <p class="card-text">Availabel in: {{ carData.region.name }}</p>
          <p class="card-text">Stock: {{ carData.stock }}</p>
          <router-link class="btn text-white" style="background-color: #646FD4;" v-bind:to="'/cars/' + carData.id">Details</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "CarComponent",
  props: {
    carData: {
      id: -1,
      brand: "BrandDefault",
      model: "ModelDefault",
      region: {
        id: -1,
        name: "RegionDefault"
      },
      stock: -1
    },
  },
};
</script>
