export default {
    namespaced: true,
    state: {
        news: []
    },
    getters: {
        getDateTime: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    let date = new Date(state.news[i].date);
                    let res = '';
                    res += `${date.getDate() < 10 ? '0' + date.getDate() : date.getDate()}.`;
                    res += `${date.getMonth() < 10 ? '0' + date.getMonth() : date.getMonth()}.`;
                    res += `${date.getFullYear()} `;
                    res += `${date.getHours()}:`;
                    res += `${date.getMinutes()}`;
                    return res;
                }
            }
        },
        getCountLikes: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id && state.news[i].likes) {
                    return state.news[i].likes.length;
                }
            }
        },
        getShowMoreComments: (state) => (id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    console.log(state.news[i].currentPage);
                    console.log(state.news[i].totalPages);
                    return state.news[i].currentPage + 1 < state.news[i].totalPages ? true : false;
                }
            }
        }
    },
    mutations: {
        addNews: (state, data) => {
            state.news = data;
            for (let i in state.news) {
                state.news[i].showComments = false;
                state.news[i].showFullText = false;
            }
            // console.log("news", state.news);
        },
        showFullText: (state, id) => {
            for (let i in state.news) {
                if (state.news[i].id === id) {
                    state.news[i].showFullText = !state.news[i].showFullText;
                }
            }
        },
        addComments: (state, payload) => {
            // console.log(payload.id);
            // console.log("comments", payload.data.comments);
            for (let i in state.news) {
                if (state.news[i].id === payload.id) {
                    if (state.news[i].comments) {
                        for (let j in payload.data.comments) {
                            state.news[i].comments.push(payload.data.comments[i]);
                        }
                        state.news[i].totalComments = payload.data.totalItems;
                        state.news[i].currentPage = payload.data.currentPage;
                        state.news[i].totalPages = payload.data.totalPages;
                    } else {
                        state.news[i].comments = payload.data.comments;
                        state.news[i].totalComments = payload.data.totalItems;
                        state.news[i].currentPage = payload.data.currentPage;
                        state.news[i].totalPages = payload.data.totalPages;
                    }
                }
            }
            // console.log("add comm", state.news);
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
            // console.log(payload.id);
            // console.log("likes", payload.data);
            for (let i in state.news) {
                if (state.news[i].id === payload.id) {
                    state.news[i].likes = payload.data;
                }
            }
            // console.log("add likes", state.news);
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
        loadMoreCommentsFromServer({state, commit}, id) {
            let currentPage = state.news.find(item => item.id === id).currentPage + 1;
            fetch(`http://localhost:8080/news/${id}/comments?page=${currentPage}`)
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