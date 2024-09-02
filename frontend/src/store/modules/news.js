export default {
    namespaced: true,
    state: {
        news: []
    },
    getters: {
        getSortNews: (state) => {
            return state.news.sort((a, b) => {
                let aDate = new Date(a.date);
                let bDate = new Date(b.date);
                return bDate - aDate;
            });
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