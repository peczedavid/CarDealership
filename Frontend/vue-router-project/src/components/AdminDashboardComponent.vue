<template>
    <div class="col">
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th :class="calcHeadColor(index)" v-for="(userProperty, index) in userProperties"
                        :key="userProperty[0]">
                        <button v-if="index > 0" class="bg-transparent border-0" @click="moveColumn(index, -1)"
                            :class="calcArrowColor(index)">
                            <fa class="user-nav-icon" icon="arrow-left"></fa>
                        </button>
                        {{ userProperty }}
                        <button v-if="sortAsc" @click="setConfig(false, index)" class="bg-transparent border-0"
                            :class="calcArrowColor(index)">
                            <fa class="user-nav-icon" icon="arrow-down"></fa>
                        </button>
                        <button v-else @click="setConfig(true, index)" class="bg-transparent border-0"
                            :class="calcArrowColor(index)">
                            <fa class="user-nav-icon" icon="arrow-up"></fa>
                        </button>
                        <button v-if="index < (userProperties.length - 1)" class="float-end bg-transparent border-0"
                            @click="moveColumn(index, 1)" :class="calcArrowColor(index)">
                            <fa class="user-nav-icon" icon="arrow-right"></fa>
                        </button>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td v-for="userProperty in userProperties" :key="userProperty[0]"
                        :class="{ 'table-warning': user.admin }" style="width: 0%">
                        <span class="mx-auto">
                            {{ user[userProperty] }}
                        </span>
                        <button class="admin-button btn-sm my-auto" @click="toggleAdmin(user)" v-if="userProperty === 'admin'">
                            <fa v-if="!user.admin" icon="plus"></fa>
                            <fa v-else icon="minus"></fa>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="col-12 d-flex">
            <div class="col-6 d-flex justify-content-start ps-3">
                <vue-awesome-paginate :total-items="numAllUsers" :items-per-page="itemsPerPage"
                    :current-page="currentPage" :on-click="onPageChanged" />
            </div>
            <div class="col-6 d-flex justify-content-end pe-4">
                <div class="form-group d-flex">
                    <label for="perPageInput" class="me-2 col-form-label text-dark">Users per page:</label>
                    <div>
                        <select @click="(event) => { refreshTable(); persistState(); }" v-model.number="itemsPerPage"
                            class="mt-2 rounded-2 text-end" id="perPageInput">
                            <option value="3">3</option>
                            <option selected value="5">5</option>
                            <option value="10">10</option>
                            <option value="15">15</option>
                            <option value="25">25</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "@/http-common.js";
import { store } from "@/data/store";

export default {
    data() {
        return {
            users: [{}],
            currentPage: 1,
            numAllUsers: 0, // Get from database

            // Persist
            itemsPerPage: 5,
            userProperties: [""],
            sortByIndex: 0, // id
            sortAsc: true,
        }
    },
    methods: {
        toggleAdmin(user) {
            if(store.currentUser.id === user.id)
                return;

            const copyUser = {...user};
            copyUser.admin = !user.admin;
            axios
                .put("/user", copyUser)
                .then((result) => {
                    if(copyUser.id === result.data.id) {
                        user.admin = result.data.admin;
                    }
                    this.refreshTable();
                })
                .catch((error) => {
                    console.log(error);
                });
        },
        calcHeadColor(index) {
            return index == this.sortByIndex ?
                "bg-secondary bg-opacity-50" : "bg-secondary bg-opacity-25";
        },
        calcArrowColor(index) {
            return index == this.sortByIndex ?
                "text-dark" : "text-secondary";
        },
        persistState() {
            localStorage.setItem("userProperties", JSON.stringify(this.userProperties));
            localStorage.setItem("sortAsc", this.sortAsc);
            localStorage.setItem("sortByIndex", this.sortByIndex);
            localStorage.setItem("itemsPerPage", this.itemsPerPage);
        },
        async loadState() {
            const itemsPerPage = localStorage.getItem("itemsPerPage")
            if (itemsPerPage != null)
                this.itemsPerPage = parseInt(itemsPerPage);
            else
                this.itemsPerPage = 5;

            const sortAsc = localStorage.getItem("sortAsc");
            if (sortAsc != null)
                this.sortAsc = sortAsc === "true";
            else
                this.sortAsc = true;

            const sortByIndex = localStorage.getItem("sortByIndex");
            if (sortByIndex != null)
                this.sortByIndex = parseInt(sortByIndex);
            else
                this.sortByIndex = 0; // first column

            const userProps = localStorage.getItem("userProperties");
            if (userProps != null)
                this.userProperties = JSON.parse(userProps);
            else {
                try {
                    const result = await axios.get("/user");
                    this.userProperties = Reflect.ownKeys(result.data);
                } catch (error) {
                    console.log(error);
                }
            }
        },
        moveColumn(columnIndex, indexDelta) {
            // Swap the two columns
            let temp = this.userProperties[columnIndex];
            this.userProperties[columnIndex] = this.userProperties[columnIndex + indexDelta];
            this.userProperties[columnIndex + indexDelta] = temp;

            if (columnIndex === this.sortByIndex)
                this.sortByIndex += indexDelta;

            this.$forceUpdate();
            this.persistState();
        },
        onPageChanged(page) {
            this.currentPage = page;

            this.refreshTable();
        },
        setConfig(ascending, colIndex) {
            this.sortAsc = ascending;
            this.sortByIndex = colIndex;

            this.persistState();
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
        logConfig() {
            console.log(this.sortAsc);
            console.log(this.sortByIndex);
            console.log(this.userProperties);
        },
    },
    created() {
        this.loadState();
        axios
            .get("/user/count")
            .then((result) => {
                this.numAllUsers = result.data;
                this.refreshTable();
            })
            .catch((error) => {
                console.log(error);
            });
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
    background-color: #adb5bd;
    border: 1px solid #adb5bd;
    color: white;
}

.active-page:hover {
    background-color: #adb5bd;
}

.user-nav-icon:hover {
    color: #d8d8d8;
}

.admin-button {
    background-color: transparent;
    border: 0;
    color: #464646;
}
</style>