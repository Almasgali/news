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
            </v-row>
        </v-container>
    </div>
</template>

<script>
    export default {
        computed: {
            news() {
                this.$store.getters['news/getSortNews'];
                return this.$store.state.news.news;
            }
        }
    }
</script>