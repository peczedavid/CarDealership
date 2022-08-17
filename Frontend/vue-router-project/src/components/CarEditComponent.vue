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
                    <router-link v-if="carEditData == null" to="/cars" class="btn btn-secondary">
                        <fa icon="arrow-left"></fa> Back
                    </router-link>
                    <router-link v-else v-bind:to="'/cars/' + carEditData.id" class="btn btn-secondary">
                        <fa icon="arrow-left"></fa> Back
                    </router-link>
                </div>
                <div class="col-6 d-flex justify-content-end">
                    <button v-if="carEditData == null" type="submit" class="btn btn-primary">
                     <fa icon="file-circle-plus"></fa> New car</button>
                    <button v-else type="submit" class="btn btn-primary">
                        <fa icon="pencil"></fa> Update car
                    </button>
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
            // Editing
            if (this.carEditData != null) {
                axios
                    .put("/cars/" + this.carEditData.id, this.carData)
                    .then((result) => {
                        this.$router.push({
                            name: "carDetail",
                            params: {
                                id: result.data.id,
                                action: "edit"
                            }
                        });
                    })
                    .catch((error) => alert(error));
            }
            // Creating new
            else {
                if (!this.activeUser.admin)
                    this.carData.region = this.activeUser.region.name;
                axios
                    .post("/cars", this.carData)
                    .then((result) => {
                        this.$router.push({
                            name: "carDetail",
                            params: {
                                id: result.data.id,
                                action: "create"
                            }
                        });
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