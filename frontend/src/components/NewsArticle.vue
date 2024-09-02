<template>
    <div>
        <v-container
          v-for="item in news"
          :key="item.id"
        >
            <v-row>
                <v-col cols="4">
                    <img :width="300" :src="item.img">
                </v-col>
                <v-col>
                    <h2>{{ item.title }}</h2>
                    <v-row class="text-start mt-11">{{item.text}}</v-row>
                </v-col>
                <v-btn @click="clickBtn(item.id)">Комментарии</v-btn>
                <v-container v-if="item.showComments">
                    <v-btn>еще</v-btn>
                </v-container>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    export default {
        computed: {
            news() {
                for (let i in this.$store.state.news.news) {
                    console.log(i)
                }
                return this.$store.state.news.news;
            }
        },
        methods: {
            clickBtn(id) {
                this.$store.commit('news/showComments', id);
                this.$store.dispatch('news/getCommentsFromServer', id);
            }
        },
    }
</script>