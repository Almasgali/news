<template>
  <v-container>
    <AppBar/>
    <NewsArticle/>
  </v-container>
</template>

<script>
  import AppBar from '../components/AppBar.vue'
  import NewsArticle from '../components/NewsArticle.vue'

  export default {
    components: {
      AppBar,
      NewsArticle
    },
    mounted() {
      this.$store.dispatch('news/loadNewsFromServer');
      let news = this.$store.state.news.news;
      for (let i in news) {
         this.$store.dispatch('news/loadLikesFromServer', news[i].id);
      }
    },
  }
</script>
