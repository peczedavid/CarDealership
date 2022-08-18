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
                        <router-link v-if="activeUser" to="/cars" aria-current="page" class="nav-link active">
                            Cars
                        </router-link>
                    </li>

                    <li v-for="(element, index) in navigationElements" :key="element" class="nav-item py-2 me-2">
                        <span class="text-white">
                            <fa class="me-2" icon="angle-right"></fa>{{ navigationElements[index] }}
                        </span>
                    </li>
                </ul>
                <!-- TODO: if clicked away from /cars clear the query -->

                <!-- Always render the form so the Login and Register buttons are on the end of the
                    navbar, beacuse of the mx-auto on the form -->
                <form class="d-flex mx-auto" role="search" @submit.prevent="handleSearch">
                    <input v-if="activeUser" v-model="searchQuery" class="form-control searchbar-input" type="search"
                        placeholder="Search" aria-label="Search">
                    <button v-if="activeUser" class="btn btn-outline-light searchbar-button" type="submit">
                        <fa icon="search"></fa>
                    </button>
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
            searchQuery: "",

            // Only the extra
            navigationElements: []
        };
    },
    watch: {
        $route(to, from) {
            const urlElements = to.path.split("/");
            urlElements.shift(); // Remove first empty element
            console.log(urlElements);

            // Clear the nav elements
            this.navigationElements = [];
            if(urlElements[0] && urlElements[0] === "cars") {
                if(urlElements[1]) {
                    if(urlElements[1] === "edit") {
                        this.navigationElements.push("Details");
                        this.navigationElements.push("Edit");
                    }
                    else if(urlElements[1] === "new") {
                        this.navigationElements.push("New");
                    }
                    else if(urlElements[1] !== NaN) {
                         this.navigationElements.push("Details");
                    }
                }
            }
        },
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
            else {
                this.emitter.emit("cars-queried", this.searchQuery);
            }
        },
    },
};
</script>

<style scoped>
.searchbar-input {
    border-radius: 10px 0px 0px 10px;
}

.searchbar-button {
    border-radius: 0px 10px 10px 0px;
}
</style>