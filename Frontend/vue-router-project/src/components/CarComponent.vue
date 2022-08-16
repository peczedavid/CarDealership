<template>
  <div class="card mb-3">
    <div class="row g-0">
      <div class="col-md-4">
        <img src="@/assets/images/cars/car-placeholder.png" class="img-fluid rounded-start" alt="...">
      </div>
      <div class="col-md-8">
        <div class="card-body p-0 ps-3 pt-3">
          <h5 class="card-title">{{ carData.brand + " " + carData.model }}</h5>
          <p class="card-text">Available in: {{ carData.region.name }}</p>
          <p class="card-text">Stock: {{ carData.stock }}</p>
          <router-link class="btn btn-info text-white" v-bind:to="'/cars/' + carData.id">
            Details</router-link>
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
      stock: -1,
      description: "DescriptionDefault"
    },
  },
};
</script>
