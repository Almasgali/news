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
                    <v-row class="text-start mt-11">
                        {{item.text}}
                    </v-row>
                    <v-container>
                        <v-row>
                            <v-col>
                                Опубликовано {{ item.date }}
                            </v-col>
                            <v-col>
                                <v-btn
                                  @click="showComments(item.id)"
                                  variant="text"
                                >
                                    Комментарии
                                </v-btn>
                            </v-col>
                            <v-col>
                                <v-btn
                                  variant="text"
                                  
                                >
                                    like
                                </v-btn>
                            </v-col>
                            <v-col>
                                <v-btn
                                  variant="text"
                                >
                                    Показать больше
                                </v-btn>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-col>
                <v-container v-if="item.showComments">
                    <v-row
                      v-for="comment in comments"
                      :key="comment.id"
                    >
                        <p>{{ comment.text }}</p>
                        <v-col>
                            {{  }}
                        </v-col>
                        <v-col>
                            {{ comment.date }}
                        </v-col>
                    </v-row>
                    <v-btn
                      variant="text"
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
            },
            comments() {
                return this.$store.state.news.comments;
            }
        },
        methods: {
            showComments(id) {
                this.$store.commit('news/showComments', id);
                this.$store.dispatch('news/loadCommentsFromServer', id);
            },
            addComments(id) {
                this.$store.dispatch('news/loadCommentsFromServer', id);
            }
        },
    }
</script>