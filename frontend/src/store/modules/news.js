export default {
    namespaced: true,
    state: {
        news: []
    },
    getters: {
        getCountLikes: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    return state.news[i].likes.length;
                }
            }
        },
        getShowMoreComments: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    return state.news[i].currentPages + 1 < state.news[i].totalPages ? true : false;
                }
            }
        }
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
            console.log("news", data);
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
            console.log("comments", payload.data);
            for (let i in state.news) {
                if (state.news[i].id === payload.id) {
                    if (state.news[i].comments) {
                        state.news[i].comments.push(payload.data.comments);
                        state.news[i].totalComments = payload.data.totalItems;
                        state.news[i].currentPages = payload.data.currentPages;
                        state.news[i].totalPages = payload.data.totalPages;
                    } else {
                        state.news[i].comments = payload.data.comments;
                        state.news[i].totalComments = payload.data.totalItems;
                        state.news[i].currentPages = payload.data.currentPages;
                        state.news[i].totalPages = payload.data.totalPages;
                    }
                }
            }
        },
        showComments: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    if (state.news[i].comments.length !== 0) {
                        state.news[i].showComments = !state.news[i].showComments;
                    }
                }
            }
        },
        addLikes: (state, payload) => {
            console.log("likes", payload.data);
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