export default {
    namespaced: true,
    state: {
        news: [],
        comments: [],
        likes: []
    },
    getters: {
        getCountComments: (state) => {
            return state.comments.length;
        },
        getCountLikes: (state) => {
            return state.likes.length;
        }
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
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
        addComments: (state, data) => {
            state.comments = data;
        },
        showComments: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    state.news[i].showComments = true;
                }
            }
        },
        addLikes: (state, data) => {
            state.likes = data;
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
              .then(responseJson => commit('addComments', responseJson));
        },
        loadLikesFromServer({commit}, id) {
            fetch(`http://localhost:8080/news/${id}/likes`)
              .then(response => response.json())
              .then(responseJson => commit('addLikes', responseJson));
        }
    }
}