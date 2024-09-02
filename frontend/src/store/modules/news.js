export default {
    namespaced: true,
    state: {
        news: [],
        comments: []
    },
    getters: {
        
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
            for (let i in state.news) {
                state.news[i].showComments = false;
            }
        },
        addComments: (state, data) => {
            state.comments = data;
            console.log(state.comments);
        },
        showComments: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    state.news[i].showComments = true;
                }
            }
        }
    },
    actions: {
        getNewsFromServer ({commit}) {
            fetch('http://localhost:8080/news')
              .then(response => response.json())
              .then(responseJson => commit('addNews', responseJson));
        },
        getCommentsFromServer({sommit}, id) {
            fetch($`http://localhost:8080/news/{id}/comments`)
              .then(response => response.json())
              .then(responseJson => commit('addComments', responseJson));
        }
    }
}