<template>
    <div class="container">
        <div class="card col-6 mx-auto mt-5">
            <div class="card-body d-inline-flex">
                <div class="me-4">
                    <img class="" src="@/assets/images/cars/car-placeholder.png" alt="Car image">
                </div>
                <div class="">
                    <h2 class="card-title">{{ carData.brand + " " + carData.model }}</h2>
                    <p class="card-text " style="font-size: 1.5rem;">Available in: {{ carData.region.name }}</p>
                    <p class="card-text">Stock: {{ carData.stock }}</p>
                    <p>{{ carData.description }}</p>
                </div>
            </div>
            <ul class="list-group list-group-flush">
                <div class="col-12 d-flex p-3 px-4">
                    <div class="col-6 d-flex justify-content-start">
                        <router-link to="/cars" class="btn btn-secondary me-3">
                            <fa icon="arrow-left"></fa> Back
                        </router-link>
                    </div>
                    <div class="col-6 d-flex justify-content-end">
                        <router-link v-bind:to="'edit/' + carData.id" class="btn btn-primary me-3">
                        <fa icon="pen-to-square"></fa> Edit</router-link>
                        <button class="btn btn-danger" data-bs-toggle="modal"
                            data-bs-target="#confirmDeleteModal"><fa icon="trash-can"></fa> Delete</button>
                    </div>
                </div>
            </ul>
        </div>
        <div class="modal fade" id="confirmDeleteModal" tabindex="-1" area-labelledby="modal-title" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modal-title">Delete car</h5>
                        <button class="btn btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="class modal-body">
                        Are you sure want to delete this car from the database?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"
                            data-dismiss="modal">Close</button>
                        <button @click="deleteCar" type="button" class="btn btn-danger" data-bs-dismiss="modal"
                            data-dismiss="modal">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from '@/http-common'
import { useToast, POSITION } from "vue-toastification";

export default {
    methods: {
        deleteCar() {
            axios.delete("/cars/" + this.carData.id)
                .then((result) => {
                    this.$router.push({
                        name: "cars",
                        params: {
                            action: "delete"
                        }
                        });
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    },
    data() {
        return {
            carData:
            {
                id: 0,
                brand: "",
                model: "",
                region: {
                    id: 0,
                    name: "",
                },
                stock: 0,
                description: ""
            },
            toast: null
        }
    },
    mounted() {
        this.toast = useToast();

        const action = this.$route.params.action;
        console.log(action);
        if(action) {
            if(action === "edit")
                this.toast.success("Car edited successfully!", { position: POSITION.BOTTOM_CENTER, timeout: 2500 });
            else if(action === "create")
                this.toast.success("Car created successfully!", { position: POSITION.BOTTOM_CENTER, timeout: 2500 });
        }

        axios.get("/cars/" + this.$route.params.id)
            .then((result) => { this.carData = result.data })
            .catch((error) => {
                if (error.response.status == 401 || error.response.status == 404)
                    this.$router.push("/unauthorized");
            });
    }
}
</script>

<style>
</style>