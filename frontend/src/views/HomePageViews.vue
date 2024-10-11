<template>
  <v-container>
    <AppBar/>
    <AdminPanel
      v-if="admin"
    />
    <NewsArticle
      v-else
    />
  </v-container>
</template>

<script>
  import AppBar from '../components/AppBar.vue'
  import NewsArticle from '../components/NewsArticle.vue'
  import AdminPanel from '../components/AdminPanel.vue';

  export default {
    computed: {
      admin() {
        return this.$store.state.person.person.admin;
      }
    },
    components: {
      AppBar,
      NewsArticle,
      AdminPanel
    }
  }
</script>
