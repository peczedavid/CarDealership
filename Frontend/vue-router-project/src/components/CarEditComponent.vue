<template>
    <div class="container col-lg-4 mt-5">
        <form @submit.prevent="handleSubmit" autocomplete="off">
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
                <label for="descriptionInput" class="form-label">Description:</label>
                <input v-model="carData.description" type="text" required class="form-control" id="descriptionInput" />
            </div>
            <div v-if="activeUser.admin" class="mb-3">
                <label for="regionSelect" class="me-2">Region:</label>
                <select required v-model="carData.region" class="" id="regionSelect">
                    <option value="" disabled selected>Select the region</option>
                    <option v-for="region in regions" :key="region.id" :value=region.name>{{ region.name }}</option>
                </select>
            </div>
            <div class="col-12 d-flex">
                <div class="col-6 d-flex justify-content-start">
                    <router-link v-if="carEditData == null" to="/cars" class="btn text-white"
                        style="background-color: #9BA3EB;">Back</router-link>
                    <router-link v-else v-bind:to="'/cars/' + carEditData.id" class="btn text-white"
                        style="background-color: #9BA3EB;">Back</router-link>
                </div>
                <div class="col-6 d-flex justify-content-end">
                    <button v-if="carEditData == null" type="submit" class="btn text-white"
                        style="background-color: #646FD4;">New car</button>
                    <button v-else type="submit" class="btn text-white" style="background-color: #646FD4;">Update
                        car</button>
                </div>
            </div>
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
                this.carData.description = newVal.description;
            }
        }
    },
    methods: {
        handleSubmit() {
            if (this.carEditData != null) {  // Editing
                axios
                    .put("/cars/" + this.carEditData.id, this.carData)
                    .then((result) => {
                        store.carEdited.status = "Edited";
                        this.$router.push("/cars/" + result.data.id);
                    })
                    .catch((error) => alert(error));
            } else { // Creating new
                if (!this.activeUser.admin)
                    this.carData.region = this.activeUser.region.name;
                axios
                    .post("/cars", this.carData)
                    .then((result) => {
                        store.carEdited.status = "Created";
                        this.$router.push("/cars/" + result.data.id);
                    })
                    .catch((error) => alert(error));
            }
        }
    },
    data() {
        return {
            carData: {
                id: 0,
                brand: "",
                model: "",
                region: "",
                stock: 0,
                description: "",
            },
            regions: [],
            activeUser: {}
        }
    }
}
</script>

<style>
</style>