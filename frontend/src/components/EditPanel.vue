<template>
    <div>
        edit
    </div>
</template>

<script>
    export default {
        data() {
            return {
                rules: [
                    v => !!v || 'Введите комментарий',
                    v => (v && v.length <= 1000) || 'Максимум 1000 символов'
                ],
                textComment: ''
            }
        },
        computed: {
            news() {
                return this.$store.state.news.news;
            },
            person() {
                return this.$store.state.person.person;
            },
            showMoreBtn() {
                return this.$store.getters['person/getBullToken'];
            }
        },
        methods: {
            showComments(id) {
                this.$store.commit('news/showComments', id);
            },
            showBtnMoreComments(id) {
                return this.$store.getters['news/getShowMoreComments'](id);
            },
            addComments(id, currentPage) {
                this.$store.dispatch('news/loadMoreCommentsFromServer', id, currentPage + 1);
            },
            showMoreText(id) {
                this.$store.commit('news/showFullText', id);
            },
            getDateTime(date) {
                return this.$store.getters['news/getDateTime'](date);
            },
            countLikes(id) {
                return this.$store.getters['news/getCountLikes'](id);
            },
            showCommentsDisabled(count) {
                return count === 0 && !this.showMoreBtn ? true : false;
            },
            getWhatLikesBtn(newsId, personId) {
                let getCheckIdInLikes = this.$store.getters['news/getCheckIdInLikes']({newsId: newsId, personId: personId});
                return getCheckIdInLikes ? 'mdi-heart' : 'mdi-heart-outline';
            },
            addLike(id) {
                this.$store.dispatch('news/sendLikeToServer', {
                    id: id,
                    token: this.person.token,
                    person: {
                        id: this.person.id,
                        name: this.person.name,
                        surname: this.person.surname
                    }
                });
            },
            delNews(newsId) {
                this.$store.dispatch('news/delComment', newsId);
            },
            delComment(newsId, commentId) {
                this.$store.dispatch('news/delComment', {
                    newsId: newsId,
                    commentId: commentId 
                });
            }
        },
        created() {
            this.$store.dispatch('news/loadNewsFromServer');
        },
        updated() {
            let news = this.$store.state.news.news;
            for (let i in news) {
                this.$store.dispatch('news/loadLikesFromServer', news[i].id);
                this.$store.dispatch('news/loadCommentsFromServer', news[i].id);
            }
        }
    }
</script>

<style scoped>
    .clamped-text {
        display: -webkit-box;
        -webkit-line-clamp: 4;
        -webkit-box-orient: vertical;
        overflow: hidden;
    }
</style>