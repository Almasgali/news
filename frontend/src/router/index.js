import { createRouter, createWebHistory } from 'vue-router'
import HomePageViews from '../views/HomePageViews.vue'
import AuthenticationPageViews from '../views/AuthenticationPageViews.vue'
import RegistrationPageViews from '../views/RegistrationPageViews.vue'
import EditPanel from '../components/EditPanel.vue'
import EditThemes from '../components/EditThemes.vue'

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
    path: '/edit',
    name: 'edit',
    component: EditPanel
  },
  {
    path: '/themes',
    name: 'themes',
    component: EditThemes
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router;
