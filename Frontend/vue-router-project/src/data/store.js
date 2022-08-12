import { reactive } from "vue";
import axios from "@/http-common";

export const store = reactive({
  carEdited: {
    // Edited, Failed, Deleted, None
    status: "None"
  },

  currentUser: null,

  async loadCurrentUser() {
    try {
      const result = await axios.get("user");
      this.currentUser = result.data;
    } catch (error) {
      if (error.status == 204) this.currentUser = null;
    }
  },
  getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(";");
    for (let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) == " ") {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  },
  setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  },

  unauthorizedMessage: "Unauthorized!",
});
