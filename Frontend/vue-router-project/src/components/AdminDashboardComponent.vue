<template>
    <div>
        <table class="table table-striped table-bordered">
            <thead style="background-color: #9BA3EB; width: 100%;">
                <tr>
                    <th v-for="(userProperty, index) in userProperties" :key="userProperty[0]">
                        <button v-if="index > 0" class="bg-transparent border-0 text-dark"
                            @click="moveColumn(index, -1)">
                            <fa icon="arrow-left"></fa>
                        </button>
                        {{ userProperty }}
                        <button v-if="sortAsc" @click="setConfig(false, index)"
                            class="bg-transparent border-0 text-dark">
                            <fa icon="arrow-down"></fa>
                        </button>
                        <button v-else @click="setConfig(true, index)" class="bg-transparent border-0  text-dark">
                            <fa icon="arrow-up"></fa>
                        </button>
                         <button v-if="index < (userProperties.length - 1)" class="float-end bg-transparent border-0"
                            @click="moveColumn(index, 1)">
                            <fa icon="arrow-right"></fa>
                        </button>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td v-for="userProperty in userProperties" :key="userProperty[0]"
                        :class="{ 'table-warning': user.admin }" style="width: 0%">
                        {{ user[userProperty] }}
                    </td>
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
            currentPage: 1,
            itemsPerPage: 5,
            numAllUsers: 9,

             // Persist
            userProperties: [""],
            sortByIndex: 0, // id
            sortAsc: true,
        }
    },
    methods: {
        persistState() {
            localStorage.userProperties = JSON.stringify(this.userProperties);
            localStorage.sortAsc = this.sortAsc;
            localStorage.sortByIndex = this.sortByIndex;
        },
        moveColumn(columnIndex, indexDelta) {
            // Swap the two columns
            let temp = this.userProperties[columnIndex];
            this.userProperties[columnIndex] = this.userProperties[columnIndex + indexDelta];
            this.userProperties[columnIndex + indexDelta] = temp;

            if(columnIndex === this.sortByIndex)
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
    },
    created() {
        this.sortAsc = Boolean(parseInt(localStorage.sortAsc));
        this.sortByIndex = parseInt(localStorage.sortByIndex);

        console.log(this.sortAsc);
        console.log(this.sortByIndex);

        const userProps = JSON.parse(localStorage.userProperties);
        if(userProps != null)
            this.userProperties = userProps;
        else
            this.userProperties = ["id", "username", "region", "admin"];
        // this.userProperties = Reflect.ownKeys(this.users[0]);

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