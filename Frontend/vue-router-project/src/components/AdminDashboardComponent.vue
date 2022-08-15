<template>
    <table class="table table-striped table-bordered">
        <thead style="background-color: #b2b7df;">
            <tr>
                <th v-for="(userProperty, index) in userProperties" :key="userProperty[0]">
                    {{ userProperty }}
                    <button v-if="sortAsc" @click="refreshTable(false, index)"
                        class="float-end bg-transparent border-0">
                        <fa icon="arrow-down"></fa>
                    </button>
                    <button v-else @click="refreshTable(true, index)" class="float-end bg-transparent border-0">
                        <fa icon="arrow-up"></fa>
                    </button>
                </th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in users" :key="user.id">
                <td v-for="userProperty in userProperties" :key="userProperty[0]"
                    :class="{ 'table-warning': user.admin }">{{ user[userProperty] }}</td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import axios from "@/http-common.js";

export default {
    data() {
        return {
            users: [{}],
            userProperties: [""],
            sortByIndex: 0, // id
            sortAsc: true
        }
    },
    methods: {
        refreshTable(ascending, colIndex) {
            this.sortAsc = ascending;
            this.sortByIndex = colIndex;
            let url = "/user/paging/0/15/";

            url = url + this.userProperties[this.sortByIndex];
            url = url + "?direction=" + (this.sortAsc ? "asc" : "desc");
            axios
                .get(url)
                .then((result) => {
                    this.users = result.data.content.map(user => {
                        return {
                            id: user.id,
                            username: user.username,
                            region: user.region.name,
                            admin: user.admin
                        }
                    });
                })
                .catch((error) => {
                    console.log("Error: ", error);
                });
        },
    },
    created() {
        axios
            .get("/user/paging/0/15/id")
            .then((result) => {
                this.users = result.data.content.map(user => {
                    return {
                        id: user.id,
                        username: user.username,
                        region: user.region.name,
                        admin: user.admin
                    }
                });
                this.userProperties = Reflect.ownKeys(this.users[0]);
            })
            .catch((error) => {
                console.log("Error: ", error);
            });
    }
}
</script>

<style>
</style>