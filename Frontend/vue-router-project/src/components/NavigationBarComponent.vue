<template>
    <nav class="navbar fixed-top navbar-expand-lg bg-dark navbar-dark">
        <div class="container">
            <router-link class="navbar-brand" to="/">
                <fa icon="car"></fa> Dealership
            </router-link>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mb-2 mb-lg-0 me-5">
                    <li class="nav-item">
                        <router-link v-if="activeUser" to="/cars" aria-current="page" class="nav-link active">Cars
                        </router-link>
                    </li>

                </ul>
                <!-- TODO: if clicked away from /cars clear the query -->
                <!-- TODO: if searched outside of /cars, navigate to /cars and search -->
                <form v-if="activeUser" class="d-flex mx-auto" role="search" @submit.prevent="handleSearch">
                    <input v-model="searchQuery" class="form-control searchbar-input" type="search" placeholder="Search"
                        aria-label="Search">
                    <button class="btn btn-outline-light searchbar-button" type="submit"><fa icon="search"></fa></button>
                </form>
                <ul class="navbar-nav mt-2 mt-lg-0">
                    <li>
                        <!-- User logged in -->
                        <fa v-if="activeUser && activeUser.admin" class="text-white me-1" icon="user-shield"></fa>
                        <fa v-if="activeUser && !activeUser.admin" class="text-white me-1" icon="user"></fa>
                        <router-link v-if="activeUser && activeUser.admin" to="/adminboard" tag="button"
                            class="btn btn-outline-light ms-2">
                            Users
                        </router-link>
                        <router-link v-if="activeUser" to="/profile" tag="button" class="btn btn-outline-light ms-2">
                            Profile
                        </router-link>
                        <router-link v-if="activeUser" to="/" @click="signOut" tag="button"
                            class="btn btn-outline-light ms-2">
                            Log out
                        </router-link>

                        <!-- User logged out-->
                        <router-link v-if="!activeUser" to="/login" class="btn btn-outline-light ms-2" tag="button">
                            Log in
                        </router-link>
                        <router-link v-if="!activeUser" to="/register" class="btn btn-outline-light ms-2" tag="button">
                            Register
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</template>

<script>
import axios from '@/http-common';
import { store } from '@/data/store';

export default {
    data() {
        return {
            activeUser: null,
            searchQuery: ""
        };
    },
    async created() {
        await store.loadCurrentUser();
        this.activeUser = store.currentUser;
    },
    mounted() {
        this.emitter.on("sign-in", data => {
            this.activeUser = data;
        });
    },
    methods: {
        signOut() {
            axios
                .post("/user/logout")
                .then(() => {
                    this.activeUser = null;
                    store.currentUser = null;

                    // Refresh page so cookie dissappears
                    this.$router.go();
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        handleSearch() {
            // Redirect to /cars with the query passed through params
            if (this.$router.currentRoute.value.name !== "cars") {
                this.$router.push({ name: "cars", params: { query: this.searchQuery } });
            }
        },
    },
};
</script>

<style>
.searchbar-input {
    border-radius: 10px 0px 0px 10px;
}

.searchbar-button {
    border-radius: 0px 10px 10px 0px;
}
</style>