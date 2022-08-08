import { createApp } from "vue";
import App from "./App.vue";
import mitt from "mitt";
const emitter = mitt();
import router from "./router";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// All icons
import { fas } from "@fortawesome/free-solid-svg-icons";
library.add(fas);

const app = createApp(App).component("fa", FontAwesomeIcon).use(router);
app.config.globalProperties.emitter = emitter;
app.mount("#app");
