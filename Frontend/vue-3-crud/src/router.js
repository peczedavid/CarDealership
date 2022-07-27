import { createWebHistory, createRouter } from "vue-router";
const routes =  [
  {
    path: "/",
    alias: "/helloworld",
    name: "helloworld",
    component: () => import("./components/HelloWorld")
  }
];
const router = createRouter({
  history: createWebHistory(),
  routes,
});
export default router;