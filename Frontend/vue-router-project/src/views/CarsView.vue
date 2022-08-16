<template>
  <div class="container bg-light">
    <div class="row mt-4">
      <div class="col">
        <p class="fw-semibold fs-5">Cars found: {{ this.cars.length }}</p>
      </div>
      <div class="col text-end">
        <select v-model="sortingType" @change="getFilteredCars">
          <option value="brand-a-z">Sort by: Brand (A-Z)</option>
          <option value="brand-z-a">Sort by: Brand (Z-A)</option>
          <option value="stock-desc">Stock High to Low </option>
          <option value="stock-asc">Stock Low to High</option>
        </select>
      </div>
    </div>
    <!-- <SideBarComponent class="col-6 col-xl-3 position-fixed" style="z-index: 10;" @carsChanged="this.cars = $event" /> -->
    <SideBarComponent class="position-fixed" style="z-index: 10;" @carsChanged="this.cars = $event" />
    <div class="row">
      <div class="col-6 mx-auto">
        <CarComponent class="mb-3" v-for="car in cars" :key="car.id" :carData="car" />
      </div>
    </div>
      <div class="fadeIn" v-if="showBackToTop" style=" position: fixed; left: 85%; bottom: 10%;">
        <button class="btn btn-primary text-white rounded-4" @click="backToTop">
            <fa class="me-2" icon="arrow-up"></fa>Back to top
        </button>
      </div>
  </div>
</template>

<script>
import CarComponent from "@/components/CarComponent.vue";
import SideBarComponent from "@/components/SideBarComponent.vue";
import axios from "@/http-common"
import { store } from "@/data/store"
import { useToast, POSITION } from "vue-toastification";

export default {
  name: "CarsView",
  methods: {
    handleScroll() {
      this.showBackToTop = document.documentElement.scrollTop > 400;
    },
    backToTop() {
      document.documentElement.scrollTop = 0;
    },
    getAllCars() {
      this.getFilteredCars();
    },
    getFilteredCars() {
      if (!this.activeUser)
        this.$router.push("/unauthorized");

      store.setCookie("sortingType", this.sortingType, 7);

      let url = "/cars/filtered?"
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
    getQueriedCars(query) {
      if (!this.activeUser)
        this.$router.push("/unauthorized");

      store.setCookie("sortingType", this.sortingType, 7);

      let url = "/cars?"
      if (query != "")
        url = url.concat("query=" + query + "&");
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

    window.addEventListener("scroll", this.handleScroll);
  },
  mounted() {
    this.toast = useToast();

    if (store.carEdited.status === "Deleted") {
      this.toast.success("Car deleted from database!", { position: POSITION.BOTTOM_CENTER, timeout: 2500 });
      store.carEdited.status = "None";
    }

    this.emitter.on("cars-filter-changed", filters => {
      this.filters = filters;
      this.getFilteredCars(this.filters);
    });

    this.emitter.on("cars-queried", query => {
      this.getQueriedCars(query);
    });
  },
  data() {
    return {
      cars: [],
      sortingType: "stock-desc",
      filters: {
        brand: "",
        model: "",
        region: "",
        stockLow: 0,
        stockTop: Infinity
      },
      showBackToTop: false,
      activeUser: null,
      toast: null
    };
  },
  components: {
    CarComponent,
    SideBarComponent,
  },
};
</script>

<style scoped>
@keyframes fadeIn {
    from {
        opacity: 0%;
        translate: 0px 50px;
    }

    to {
        opacity: 100%;
        translate: 0px 0px;
    }
}

.fadeIn {
    animation: fadeIn 0.2s;
}
</style>