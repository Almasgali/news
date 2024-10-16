export default {
    namespaced: true,
    state: {
        news: [],
        editNewsId: null,
        allThemes: []
    },
    getters: {
        getDateTime: (state) => (data) => {
            let date = new Date(data);
            let res = '';
            res += `${date.getDate() < 10 ? '0' + date.getDate() : date.getDate()}.`;
            res += `${date.getMonth() < 10 ? '0' + date.getMonth() : date.getMonth()}.`;
            res += `${date.getFullYear()} `;
            res += `${date.getHours() < 10 ? '0' + date.getHours() : date.getHours()}:`;
            res += `${date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()}`;
            return res;
        },
        getCountLikes: (state) => (id) => {
            if (state.news.find(item => item.id === id).likes) {
                return state.news.find(item => item.id === id).likes.length;
            }
        },
        getShowMoreComments: (state) => (id) => {
            let currentPage = state.news.find(item => item.id === id).currentPage;
            let totalPages = state.news.find(item => item.id === id).totalPages;
            return currentPage + 1 < totalPages ? true : false;
        },
        getCheckIdInLikes: (state) => (data) => {
            let likes = state.news.find(item => item.id === data.newsId).likes;
            if (likes) {
                return likes.find(item => item.id === data.personId);
            }
        },
        getNewsById: (state) => {
            return state.news.find(item => item.id === state.editNewsId) || {};
        },
        getThemesForNews: (state) => (id) => {
            let allThemes = state.news.find(item => item.id === id).themes;
            let res = '';
            for (let i in allThemes) {
                res += `#${allThemes[i].name} `;
            }
            return res;
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
            let val = state.news.find(item => item.id === id).showFullText;
            state.news.find(item => item.id === id).showFullText = !val;
        },
        addComments: (state, payload) => {
            if (!state.news.find(item => item.id === payload.id).comments || payload.data.currentPage === 0) {
                state.news.find(item => item.id === payload.id).comments = [];
            }
            for (let i in payload.data.comments) {
                state.news.find(item => item.id === payload.id).comments.push(payload.data.comments[i]);
            }
            state.news.find(item => item.id === payload.id).totalComments = payload.data.totalItems;
            state.news.find(item => item.id === payload.id).currentPage = payload.data.currentPage;
            state.news.find(item => item.id === payload.id).totalPages = payload.data.totalPages;
        },
        showComments: (state, id) => {
            let val = state.news.find(item => item.id === id).showComments;
            state.news.find(item => item.id === id).showComments = !val;
        },
        addLikes: (state, payload) => {
            state.news.find(item => item.id === payload.id).likes = payload.data;
        },
        addNewLike: (state, payload) => {
            state.news.find(item => item.id === payload.id).likes.push(payload.person);
        },
        setEditNewsId: (state, id) => {
            state.editNewsId = id;
        },
        addThemes: (state, payload) => {
            state.news.find(item => item.id === payload.id).themes = payload.data;
        },
        addAllThemes: (state, payload) => {
            state.allThemes = payload;
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
        },
        sendCommentToServer({dispatch}, data) {
            fetch(`http://localhost:8080/news/${data.id}/comments?userId=${data.personId}`, {
              method: 'PATCH',
              headers: {
                'Authorization': `Bearer ${data.token}`,
                'Content-Type': 'application/json'
              },
              body: JSON.stringify(data.text)
            })
              .then(response => dispatch('loadCommentsFromServer', data.id))
        },
        sendLikeToServer({dispatch}, data) {
            fetch(`http://localhost:8080/news/${data.id}/likes?userId=${data.person.id}`, {
                method: 'PATCH',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                },
            })
              .then(response => dispatch('loadLikesFromServer', data.id))
        },
        delNews({dispatch}, data) {
            fetch(`http://localhost:8080/news/${data.newsId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => dispatch('loadNewsFromServer'))
        },
        delComment({dispatch}, data) {
            fetch(`http://localhost:8080/news/comments/${data.commentId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => dispatch('loadCommentsFromServer', data.newsId))
        },
        createNews({dispatch}, data) {
            fetch(`http://localhost:8080/news`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data.data)
            })
                .then(response => dispatch('loadNewsFromServer'))
        },
        editNews({state, dispatch}, data) {
            fetch(`http://localhost:8080/news/${state.editNewsId}`, {
                method: 'PATCH',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data.data)
            })
                .then(response => dispatch('loadNewsFromServer'))
        },
        loadThemesFromServer({commit}, id) {
            fetch(`http://localhost:8080/news/${id}/themes`)
              .then(response => response.json())
              .then(responseJson => commit('addThemes', {id: id, data: responseJson}));
        },
        loadAllThemesFromServer({commit}) {
            fetch(`http://localhost:8080/news/themes`)
              .then(response => response.json())
              .then(responseJson => commit('addAllThemes', responseJson));
        },
        // addThemesInNews({state}, data) {
        //     fetch(`http://localhost:8080/news/${state.editNewsId}/themes?themeId=${data.themeId}`, {
        //         method: 'PATCH',
        //         headers: {
        //             'Authorization': `Bearer ${data.token}`,
        //             'Content-Type': 'application/json'
        //         }
        //     })
        // },
        // delThemesInNews({state}, data) {
        //     fetch(`http://localhost:8080/news/${state.editNewsId}/themes?themeId=${data.themeId}`, {
        //         method: 'DELETE',
        //         headers: {
        //             'Authorization': `Bearer ${data.token}`,
        //             'Content-Type': 'application/json'
        //         }
        //     })
        // },
        delTheme({dispatch}, data) {
            fetch(`http://localhost:8080/news/themes/${data.themeId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => dispatch('loadAllThemesFromServer'))
        },
        addTheme({dispatch}, data) {
            fetch(`http://localhost:8080/news/themes`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${data.token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data.theme)
            })
                .then(response => dispatch('loadAllThemesFromServer'))
        }
    }
}