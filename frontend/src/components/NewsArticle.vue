<template>
    <div>
        <v-container
          v-for="item in news"
          :key="item.id"
        >
            <v-row>
                <v-col cols="4">
                    <img
                      :width="300"
                      :src="item.img"
                    >
                </v-col>
                <v-col>
                    <h2>
                        {{ item.title }}
                    </h2>
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
                                Опубликовано {{ item.date }}
                            </v-col>
                            <v-col>
                                <v-btn
                                  @click="showComments(item.id)"
                                  variant="text"
                                  class="text-caption"
                                >
                                    {{ !item.showComments ? "Комментарии" : 'Скрыть комментарии' }}
                                </v-btn>
                            </v-col>
                            <v-col>
                                <v-btn
                                  variant="text"
                                  class="text-caption"
                                >
                                    like {{ countLikes(item.id) }}
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
                <v-container
                  v-if="item.showComments"
                >
                    <v-row
                      v-for="comment in item.comments"
                      :key="comment.id"
                      class="comments pl-16"
                    >
                        <v-col
                          cols="12"
                          class="text-body-2 pb-1"
                        >
                            {{ comment.text }}
                        </v-col>
                        <v-col
                          cols="3"
                          class="text-caption pt-0"
                        >
                            {{ comment.person }}
                        </v-col>
                        <v-col
                          class="text-caption pt-0"
                        >
                            {{ comment.date }}
                        </v-col>
                    </v-row>
                    <v-btn
                      variant="text"
                      class="text-caption"
                      @click="addComments(item.id)"
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
        computed: {
            news() {
                return this.$store.state.news.news;
            }
        },
        methods: {
            showComments(id) {
                this.$store.commit('news/showComments', id);
                this.$store.dispatch('news/loadCommentsFromServer', id);
            },
            addComments(id) {
                this.$store.dispatch('news/loadCommentsFromServer', id);
            },
            showMoreText(id) {
                this.$store.commit('news/showFullText', id);
            },
            countLikes(id) {
                return this.$store.getters['news/getCountLikes'](id);
            }
        },
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