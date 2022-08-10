<template>
    <div class="container">
        <div class="mx-auto col-9 my-5">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th scope="col">Id</th>
                        <th scope="col">Username</th>
                        <th scope="col">Region</th>
                        <th scope="col">Role</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="user in users" :key="user.id">
                        <th scope="row">{{ user.id }}</th>
                        <td>{{ user.username }}</td>
                        <td>{{ user.region.name }}</td>
                        <td v-if="user.admin" class="table-warning">{{ user.admin ? "Admin" : "User" }}</td>
                        <td v-else scope="row">{{ user.admin ? "Admin" : "User" }}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import axios from "@/http-common.js";

export default {
    data() {
        return {
            users: [{ region: {} }]
        }
    },
    created() {
        axios
            .get("/user/all")
            .then((result) => {
                this.users = result.data;
            })
            .catch((error) => {
                console.log(error);
            });
    }
}
</script>

<style>
</style>