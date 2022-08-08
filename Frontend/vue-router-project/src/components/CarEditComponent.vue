<template>
    <div class="container col-lg-4">
        <form @submit.prevent="handleSubmit">
            <div class="mb-3">
                <label for="brandInput" class="form-label">Brand:</label>
                <input v-model="carData.brand" required class="form-control" id="brandInput" />
            </div>
            <div class="mb-3">
                <label for="modelInput" class="form-label">Model:</label>
                <input v-model="carData.model" required class="form-control" id="model" />
            </div>
            <div class="mb-3">
                <label for="stockInput" class="form-label">stock:</label>
                <input v-model="carData.stock" type="number" min="0" required class="form-control" id="stockInput" />
            </div>
            <div v-if="activeUser.admin" class="mb-3">
                <label for="regionSelect" class="me-2">Region:</label>
                <select required v-model="carData.region" class="" id="regionSelect">
                    <option value="" disabled selected>Select the region</option>
                    <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
                </select>
            </div>
            <button v-if="carEditData == null" type="submit" class="btn btn-secondary">New car</button>
            <button v-else type="submit" class="btn btn-secondary">Update car</button>
        </form>
    </div>
</template>

<script>
import { store } from "@/data/store";
import axios from "@/http-common"

export default {
    props: {
        carEditData: null,
    },
    async created() {
        axios
            .get("/regions")
            .then((result) => {
                this.regions = result.data;
            })
            .catch((error) => console.log(error));
        await store.loadCurrentUser();
        this.activeUser = store.currentUser;
    },
    watch: {
        carEditData: function (newVal) {
            if (newVal !== null) {
                this.carData.brand = newVal.brand;
                this.carData.model = newVal.model;
                this.carData.region = newVal.region.name;
                this.carData.stock = newVal.stock;
            }
        }
    },
    methods: {
        handleSubmit() {
            if (this.carEditData != null) {  // Editing
                axios
                    .put("/cars/" + this.carEditData.id, this.carData)
                    .then((result) => {
                        this.$router.push("/cars/" + result.data.id);
                    })
                    .catch((error) => alert(error));
            } else { // Creating new
                if(!this.activeUser.admin)
                    this.carData.region = this.activeUser.region.name;
                axios
                    .post("/cars", this.carData)
                    .then((result) => {
                        this.$router.push("/cars/" + result.data.id);
                    })
                    .catch((error) => alert(error));
            }
        }
    },
    data() {
        return {
            carData: {
                brand: "",
                model: "",
                region: "",
                stock: 0
            },
            regions: [],
            activeUser: {}
        }
    }
}
</script>

<style>
</style>