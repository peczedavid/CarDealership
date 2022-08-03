<template>
    <div class="container">
        <div class="card col-lg-6 mx-auto mb-3">
            <div class="card-body d-inline-flex">
                <div class="me-4">
                    <img class="" src="https://placehold.jp/200x200.png" alt="Car image">
                </div>
                <div class="">
                    <h2 class="card-title">{{ carData.brand + " " + carData.model }}</h2>
                    <p class="card-text " style="font-size: 1.5rem;">Available in: {{ carData.regions[0].name }}</p>
                    <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Maiores accusantium sit ullam optio.
                        Repudiandae quis doloribus exercitationem nam explicabo, praesentium vel cumque labore nesciunt
                        illum iste odio, laudantium recusandae molestiae omnis placeat molestias quia quam reprehenderit
                        unde maiores? Quaerat et repudiandae reiciendis totam ipsam necessitatibus consectetur,
                        laudantium distinctio eius molestias.</p>
                </div>
            </div>
            <ul class="list-group list-group-flush align-items-end">
                <li class="list-group-item p-3">
                    <button class="btn btn-primary me-3">Edit</button>
                    <button class="btn btn-danger">Delete</button>
                </li>
            </ul>
        </div>
    </div>
</template>

<script>
import axios from '@/http-common'

export default {
    data() {
        return {
            carData:
            {
                id: -1,
                brand: "BrandDef",
                model: "ModelDef",
                regions: [
                    {
                        id: -1,
                        name: "RegionDef",
                    },
                ],
            }
        }
    },
    created() {
        axios.get("/cars/" + this.$route.params.id)
            .then((result) => { this.carData = result.data })
            .catch((error) => { console.log(error) });
    }
}
</script>

<style>
</style>