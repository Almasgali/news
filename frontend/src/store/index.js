import { createStore } from 'vuex'
import person from './modules/person'
import news from './modules/news'

export default createStore({
  modules: {
    person,
    news
  }
})
