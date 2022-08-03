<!-- TODO: refactor to be able to use when editing existing one -->
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
            <button type="submit" class="btn btn-primary">New car</button>
        </form>
    </div>
</template>

<script>
import axios from "@/http-common"

export default {
    props: {
        carEditData: null
    },
    created() {
        if (this.carEditData != null) {
            console.log("EDITING");
            // TODO: Set the form values from the given carEditData
            this.carData.brand = this.carEditData.brand;
            this.carData.model = this.carEditData.model;
            this.carData.regions = this.carEditData.regions;
            this.carData.stock = this.carEditData.stock;
        }
    },
    methods: {
        handleSubmit() {

            if (this.carEditData != null) {  // Editing
                axios
                    .put("/cars", this.carData)
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