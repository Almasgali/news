<template>
  <v-container>
    <AppBar/>
    <NewsArticle/>
  </v-container>
</template>

<script>
  import AppBar from '../components/AppBar.vue'
  import NewsArticle from '../components/NewsArticle.vue'
  import AdminPanel from '../components/AdminPanel.vue';

  export default {
    components: {
      AppBar,
      NewsArticle,
      AdminPanel
    }
  }
</script>
