export default {
    namespaced: true,
    state: {
        news: []
    },
    getters: {
        getSortNews: (state) => {
            console.log(state.news);
            return state.news;
        }
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
        }
    },
    actions: {
        getNewsFromServer ({commit}) {
            fetch('http://localhost:8080/news').then(response => commit('addNews', response.json()));
            // let news = async response.json();
            // commit('addNews', news);
        }
    }
}