export default {
    namespaced: true,
    state: {
        news: []
    },
    getters: {
        getCountLikes: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    console.log(state.news[i].likes)
                    return state.news[i].likes.length;
                }
            }
        }
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
            console.log(data);
            for (let i in state.news) {
                state.news[i].showComments = false;
                state.news[i].showFullText = false;
            }
        },
        showFullText: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    state.news[i].showFullText = !state.news[i].showFullText;
                }
            }
        },
        addComments: (state, payload) => {
            console.log(payload.data);
            for (let i in state.news) {
                if (state.news[i].id === payload.id) {
                    state.news[i].comments = payload.data;
                }
            }
        },
        showComments: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    if (state.comments.length !== 0) {
                        state.news[i].showComments = !state.news[i].showComments;
                    }
                }
            }
        },
        addLikes: (state, payload) => {
            console.log(payload.data);
            for (let i in state.news) {
                if (state.news[i].id === payload.id) {
                    state.news[i].likes = payload.data;
                }
            }
        }
    },
    actions: {
        loadNewsFromServer ({commit}) {
            fetch('http://localhost:8080/news')
              .then(response => response.json())
              .then(responseJson => commit('addNews', responseJson));
        },
        loadCommentsFromServer({commit}, id) {
            fetch(`http://localhost:8080/news/${id}/comments`)
              .then(response => response.json())
              .then(responseJson => commit('addComments', {id: id, data: responseJson}));
        },
        loadLikesFromServer({commit}, id) {
            fetch(`http://localhost:8080/news/${id}/likes`)
              .then(response => response.json())
              .then(responseJson => commit('addLikes', {id: id, data: responseJson}));
        }
    }
}