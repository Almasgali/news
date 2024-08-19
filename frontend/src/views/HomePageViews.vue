<template>
  <v-container>
    <AppBar/>
    <NewsArticle
      v-if="this.$store.getters['person/getName'].trim() !== ''"
    />
  </v-container>
</template>

<script>
  import AppBar from '../components/AppBar.vue'
  import NewsArticle from '../components/NewsArticle.vue'

  export default {
    components: {
      AppBar,
      NewsArticle
    }
  }
</script>
