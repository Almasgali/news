<template>
    <div>
        <v-btn
          @click="setEditNewsId(null)"
          :to="{name: 'edit'}"
        >
            Создать статью
        </v-btn>
        <v-container
          v-for="item in news"
          :key="item.id"
        >
            <v-row>
                <v-col cols="4">
                    <img
                      :width="300"
                      :src="item.image"
                    >
                </v-col>
                <v-col>
                    <v-row>
                        <h2>
                            {{ item.title }}
                        </h2>
                    </v-row>
                    <v-row>
                        {{ this.$store.getters['news/getThemesForNews'](item.id) }}
                    </v-row>
                    <v-row
                      class="text-start mt-8"
                    >
                        <div
                          :class="{'clamped-text': !item.showFullText}"
                        >
                            {{ item.text }}
                        </div>
                    </v-row>
                    <v-container>
                        <v-row>
                            <v-col
                              class="text-body-2 pt-5"
                              cols="4"
                            >
                                Опубликовано {{ getDateTime(item.date) }}
                            </v-col>
                            <v-col>
                                <v-btn
                                  @click="showComments(item.id)"
                                  variant="text"
                                  class="text-caption"
                                  :disabled="showCommentsDisabled(item.totalComments)"
                                >
                                    {{ !item.showComments ? `Комментарии (${item.totalComments})` : 'Скрыть комментарии' }}
                                </v-btn>
                            </v-col>
                            <v-col>
                                <v-btn
                                  variant="text"
                                  class="text-caption"
                                  @click="addLike(item.id)"
                                  :disabled="!showMoreBtn"
                                >
                                    <v-icon>
                                        {{ getWhatLikesBtn(item.id, person.id) }}
                                    </v-icon>
                                    {{ countLikes(item.id) }}
                                </v-btn>
                            </v-col>
                            <v-col>
                                <v-btn
                                  variant="text"
                                  class="text-caption"
                                  @click="showMoreText(item.id)"
                                >
                                    {{ !item.showFullText ? 'Показать больше' : 'Показать меньше' }}
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-col>
                <v-col cols="2">
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-btn
                                    @click="delNews(item.id)"
                                    icon="mdi-delete-outline"
                                    size="small"
                                    elevation="0"                                
                                />
                            </v-col>
                        </v-row>
                        <v-row>
                            <v-col>
                                <v-btn
                                    @click="setEditNewsId(item.id)"
                                    :to="{name: 'edit'}"
                                    icon="mdi-pencil"
                                    size="small"
                                    elevation="0"
                                />
                            </v-col>
                        </v-row>
                    </v-container>
                    
                </v-col>
                <v-container
                  v-if="item.showComments"
                >
                    <v-row
                      v-for="comment in item.comments"
                      :key="comment.id"
                      class="comments pl-16 border-opacity-25"
                    >
                        <v-col
                          cols="10"
                          class="text-body-2 pb-1"
                        >
                            {{ comment.text }}
                        </v-col>
                        <v-col>
                            <v-btn
                                @click="delComment(item.id, comment.id)"
                                icon="mdi-delete-outline"
                                size="small"
                                elevation="0"
                            />
                        </v-col>
                        <v-col
                          cols="3"
                          class="text-caption pt-0"
                        >
                            {{ comment.name }} {{ comment.surname }}
                        </v-col>
                        <v-col
                          class="text-caption pt-0"
                        >
                            {{ getDateTime(comment.date) }}
                        </v-col>
                    </v-row>
                    <v-btn
                      v-if="showBtnMoreComments(item.id)"
                      variant="text"
                      class="text-caption"
                      @click="addComments(item.id, item.currentPage)"
                    >
                        ещё комментарии
                    </v-btn>
                </v-container>
            </v-row>
        </v-container>
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
                this.$store.dispatch('news/delNews', {
                    token: this.$store.state.person.person.token,
                    newsId: newsId
                });
            },
            delComment(newsId, commentId) {
                this.$store.dispatch('news/delComment', {
                    token: this.$store.state.person.person.token,
                    newsId: newsId,
                    commentId: commentId 
                });
            },
            setEditNewsId(id) {
                this.$store.commit('news/setEditNewsId', id);
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