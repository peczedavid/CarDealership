<template>
  <div class="card mb-3">
    <div class="card-body">
      <h5 class="card-title">{{ carData.brand + " " + carData.model }}</h5>
      <p class="card-text">Available in: {{ carData.regions[0].name }}</p>
      <a href="#" class="btn btn-primary">Details</a>
    </div>
  </div>
</template>

<script>
export default {
  name: "CarComponent",
  props: {
    carData: {
      id: -1,
      brand: "BrandDef",
      model: "ModelDef",
      regions: [
        {
          id: -1,
          name: "RegionDef",
        },
      ],
    },
  },
};
</script>

<style></style>
