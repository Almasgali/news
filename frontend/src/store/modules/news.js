export default {
    namespaced: true,
    state: {
        news: []
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
            console.log(payload)
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
        }
    },
    actions: {
        loadNewsFromServer ({commit}) {
            fetch('http://localhost:8080/news')
              .then(response => response.json())
              .then(responseJson => commit('addNews', responseJson));
        },
        loadCommentsFromServer({commit}, id) {
            console.log('load')
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
            console.log("send");
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
        }
    }
}