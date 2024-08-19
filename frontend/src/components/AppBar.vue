<template>
  <v-app-bar>
    <v-spacer></v-spacer>
    {{ name }}
    <v-btn v-if="show" @click="exit"><v-icon icon="mdi-login"></v-icon></v-btn>
    <v-btn v-else :to="{name: 'authentication'}">Войти</v-btn>
    
  </v-app-bar>
  
</template>

<script>
  export default {
    data() {
      return {
        show: false
      }
    },
    computed: {
      name() {
        let name = this.$store.getters['getName'].trim();
        if (name !== '') {
          this.show = true;
        }
        return name;
      },
    },
    methods: {
      exit() {
        this.$store.commit('delPerson');
        this.show = false;
      }
    }
  }
</script>
