import { reactive } from "vue";

export const store = reactive({
    loggedIn: false,

    getCookie(cname) {
        var name = cname + '=';
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for (var i = 0; i < ca.length; i++) {
          var c = ca[i];
          while (c.charAt(0) == ' ') {
            c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
          }
        }
        return '';
      },
      setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        var expires = 'expires=' + d.toUTCString();
        document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
      },
});

// export const store = reactive({
//     activeUser: {
//         username: "",
//         email: "",
//         admin: false,
//         loggedIn: false
//     },
//     setActiveUser(data) {
//         this.activeUser.username = data.username;
//         this.activeUser.email = data.email;
//         if(data.roles.includes("ROLE_ADMIN")) this.activeUser.admin = true;
//         this.activeUser.loggedIn = true;
//     },
//     resetActiveUser() {
//         this.activeUser.username = "";
//         this.activeUser.email = "";
//         this.activeUser.admin = false;
//         this.activeUser.loggedIn = false
//     }
// });