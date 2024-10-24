import { registerPlugins } from '@/plugins'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import { createApp } from 'vue'

const app = createApp(App)

registerPlugins(app)

app.use(router).use(store).mount('#app')
