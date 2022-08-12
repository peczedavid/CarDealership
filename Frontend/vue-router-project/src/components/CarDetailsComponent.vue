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
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Maiores accusantium sit ullam optio.
                        Repudiandae quis doloribus exercitationem nam explicabo, praesentium vel cumque labore nesciunt
                        illum iste odio, laudantium recusandae molestiae omnis placeat molestias quia quam reprehenderit
                        unde maiores?</p>
                </div>
            </div>
            <ul class="list-group list-group-flush">
                <div class="col-12 d-flex p-3 px-4" style="background-color: 0;">
                    <div class="col-6 d-flex justify-content-start" style="background-color: 0;">
                        <router-link to="/cars" class="btn text-white me-3" style="background-color: #9BA3EB;">Back
                        </router-link>
                    </div>
                    <div class="col-6 d-flex justify-content-end" style="background-color: 0;">
                        <router-link v-bind:to="'edit/' + carData.id" class="btn text-white me-3"
                            style="background-color: #646FD4;">Edit</router-link>
                        <button class="btn text-white" style="background-color: #EB5353;" data-bs-toggle="modal"
                            data-bs-target="#confirmDeleteModal">Delete</button>
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
                        <button type="button" class="btn text-white" data-bs-dismiss="modal"
                            style="background-color: #9BA3EB;" data-dismiss="modal">Close</button>
                        <button @click="deleteCar" type="button" class="btn text-white" data-bs-dismiss="modal"
                            style="background-color: #EB5353;" data-dismiss="modal">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from '@/http-common'

export default {
    methods: {
        deleteCar() {
            axios.delete("/cars/" + this.carData.id)
                .then((result) => {
                    this.$router.push("/cars");
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
                stock: 0
            }
        }
    },
    mounted() {
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