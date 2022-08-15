<template>
    <table class="table table-striped table-bordered">
        <thead style="background-color: #b2b7df;">
            <tr>
                <th v-for="userProperty in userProperties" :key="userProperty[0]">
                    {{ userProperty }}
                    <button class="float-end bg-transparent border-0"><fa icon="arrow-down"></fa></button>
                    <button class="float-end bg-transparent border-0"><fa icon="arrow-up"></fa></button>
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
        }
    },
    created() {
        axios
            .get("/user/all")
            .then((result) => {
                this.users = result.data.map(user => {
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
                console.log(error);
            });
    }
}
</script>

<style>
</style>