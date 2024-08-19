<template>
    <v-container>
        <div v-for="item in news" :key="item.id">
            <v-row>
                <v-col>
                    <img :width="300" :src="item.img">
                </v-col>
                <v-col><h2>{{ item.title }}</h2></v-col>
            </v-row>
            <v-row>{{item.text}}</v-row>
        </div>
    </v-container>
</template>

<script>
    export default {
        computed: {
            news() {
                return this.$store.state.news.news;
            }
        }
    }
</script>