import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import RegisterView from "@/views/RegisterView.vue";
import LoginView from "@/views/LoginView.vue";
import CarsView from "@/views/CarsView.vue";
import CarDetailsView from "@/views/CarDetailsView.vue";
import CarNewView from "@/views/CarNewView.vue";
import CarEditView from "@/views/CarEditView.vue";
import ProfileView from "@/views/ProfileView.vue";
import UnauthorizedView from "@/views/UnauthorizedView.vue";

const routes = [
  { path: "/", name: "home", component: HomeView },
  { path: "/register", name: "register", component: RegisterView },
  { path: "/login", name: "login", component: LoginView },
  { path: "/cars", name: "cars", component: CarsView },
  { path: "/cars/:id", name: "carDetail", component: CarDetailsView },
  { path: "/cars/new", name: "carNew", component: CarNewView },
  { path: "/cars/edit/:id", name: "carEdit", component: CarEditView },
  { path: "/profile", name: "profile", component: ProfileView },
  { path: "/unauthorized", name: "unauthorized", component: UnauthorizedView },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
