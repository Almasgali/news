<template>
  <v-app-bar>
    <v-spacer/>
    {{ name }}
    <v-btn
      v-if="show"
      @click="exit"
    >
      <v-icon icon="mdi-login"/>
    </v-btn>
    <v-btn
      v-else
      :to="{name: 'authentication'}"
    >
      Войти
    </v-btn>
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
        let name = this.$store.getters['person/getFullName'];
        if (name !== `${undefined} ${undefined}`) {
          this.show = true;
          return name;
        }
      },
    },
    methods: {
      exit() {
        this.$store.commit('person/delPerson');
        this.show = false;
      }
    }
  }
</script>
