<template>
    <div>
        <table class="table table-striped table-bordered">
            <thead style="background-color: #9BA3EB;">
                <tr>
                    <th v-for="(userProperty, index) in userProperties" :key="userProperty[0]">
                        {{ userProperty }}
                        <button v-if="sortAsc" @click="setConfig(false, index)"
                            class="float-end bg-transparent border-0">
                            <fa icon="arrow-down"></fa>
                        </button>
                        <button v-else @click="setConfig(true, index)" class="float-end bg-transparent border-0">
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
        <vue-awesome-paginate :total-items="numAllUsers" :items-per-page="itemsPerPage"
        :current-page="currentPage" :on-click="onPageChanged"/>
    </div>
</template>

<script>
import axios from "@/http-common.js";

export default {
    data() {
        return {
            users: [{}],
            userProperties: [""],
            sortByIndex: 0, // id
            sortAsc: true,
            currentPage: 1,
            itemsPerPage: 5,
            numAllUsers: 9
        }
    },
    methods: {
        onPageChanged(page) {
            this.currentPage = page;

            this.refreshTable();
        },
        setConfig(ascending, colIndex) {
            this.sortAsc = ascending;
            this.sortByIndex = colIndex;

            this.refreshTable();
        },
        refreshTable() {
            let url = "/user/paging";
            url = url + "/" + (this.currentPage - 1);
            url = url + "/" + (this.itemsPerPage);
            url = url + "/" + this.userProperties[this.sortByIndex];
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
        // this.userProperties = Reflect.ownKeys(this.users[0]);
        this.userProperties = ["id", "username", "region", "admin"];
        this.refreshTable();
    }
}
</script>

<style>
.pagination-container {
    display: flex;
    column-gap: 10px;
}

.paginate-buttons {
    height: 40px;
    width: 40px;
    border-radius: 20px;
    cursor: pointer;
    background-color: rgb(242, 242, 242);
    border: 1px solid rgb(217, 217, 217);
    color: black;
}

.paginate-buttons:hover {
    background-color: #d8d8d8;
}

.active-page {
    background-color: #9BA3EB;
    border: 1px solid #9BA3EB;
    color: white;
}

.active-page:hover {
    background-color: #9BA3EB;
}
</style>