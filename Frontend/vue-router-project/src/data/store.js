import { reactive } from "vue";
import axios from "@/http-common"

export const store = reactive({
    currentUse: null,

    async loadCurrentUser() {
        try {
            const result = await axios.get("user");
            this.currentUser = result.data;
        } catch (error) {
            if(error.status == 204)
                    this.currentUser = null;
        }
    }
});