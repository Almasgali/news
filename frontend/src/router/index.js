import { createRouter, createWebHistory } from 'vue-router'
import HomePageViews from '../views/HomePageViews.vue'
import AuthenticationPageViews from '../views/AuthenticationPageViews.vue'
import RegistrationPageViews from '../views/RegistrationPageViews.vue'
import MessagePage from '../components/MessagePage.vue'


const routes = [
  {
    path: '',
    name: 'home',
    component: HomePageViews
  },
  {
    path: '/authentication',
    name: 'authentication',
    component: AuthenticationPageViews
  },
  {
    path: '/registration',
    name: 'registration',
    component: RegistrationPageViews
  },
  {
    path: '/warning',
    name: 'message',
    component: MessagePage
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router;
