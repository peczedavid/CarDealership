import { createApp } from 'vue'
import App from './App.vue'
import mitt from 'mitt';
const emitter = mitt();
import router from './router'
import "bootstrap"
import "bootstrap/dist/css/bootstrap.min.css"

const app = createApp(App).use(router)
app.config.globalProperties.emitter = emitter;
app.mount("#app");

