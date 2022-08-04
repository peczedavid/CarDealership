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
            <div class="mb-3">
                <label for="regionSelect" class="me-2">Region:</label>
                <select required v-model="carData.regions[0]" class="" id="regionSelect">
                    <option value="" disabled selected>Select a region</option>
                    <!--TODO: ask from the database the available regions-->
                    <option value="america">America</option>
                    <option value="germany">Germany</option>
                    <option value="japan">Japan</option>
                </select>
            </div>
            <button v-if="carEditData == null" type="submit" class="btn btn-primary">New car</button>
            <button v-else type="submit" class="btn btn-primary">Update car</button>
        </form>
    </div>
</template>

<script>
import axios from "@/http-common"

export default {
    props: {
        carEditData: null
    },
    watch: {
        carEditData: function (newVal) {
            if (newVal !== null) {
                this.carData.brand = newVal.brand;
                this.carData.model = newVal.model;
                this.carData.regions = newVal.regions.map(region => region.name.toLowerCase());
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
                regions: [""],
                stock: 0
            }
        }
    }
}
</script>

<style>
</style>