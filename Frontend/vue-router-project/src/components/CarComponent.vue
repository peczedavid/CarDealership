<template>
  <div class="card" style="max-width: 540px;">
    <div class="row g-0">
      <div class="col-md-4">
        <router-link v-bind:to="'/cars/' + carData.id">
          <img src="@/assets/images/cars/car-placeholder.png" class="img-fluid rounded-start" alt="...">
        </router-link>
      </div>
      <div class="col-md-8">
        <div class="card-body">
          <router-link v-bind:to="'/cars/' + carData.id" class="text-dark" style="text-decoration: none;">
            <h5 class="card-title">{{ carData.brand + " " + carData.model }} </h5>
          </router-link>
          <p class="card-text mb-1">Available in: {{ carData.region.name }}</p>
          <div class="col mb-1">
            <p class="card-text"><small class="text-muted">Stock: {{ carData.stock }}</small></p>
          </div>
          <div class="col">
            <router-link class="btn btn-primary fw-semibold" v-bind:to="'/cars/' + carData.id">
              Details<fa class="ms-2" icon="angle-right"></fa>
            </router-link>
          </div>
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
