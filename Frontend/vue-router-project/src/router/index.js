import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import SignUpView from '@/views/SignUpView.vue'
import SignInView from '@/views/SignInView.vue'
import CarsView from '@/views/CarsView.vue'
import CarDetailsView from '@/views/CarDetailsView.vue'
import CarNewView from '@/views/CarNewView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/signup',
    name: 'signup',
    component: SignUpView
  },
  {
    path: '/signin',
    name: 'signin',
    component: SignInView
  },
  {
    path: '/cars',
    name: 'cars',
    component: CarsView
  },
  {
    path: '/cars/:id',
    name: 'carDetail',
    component: CarDetailsView
  },
  {
    path: '/cars/new',
    name: 'carNew',
    component: CarNewView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
