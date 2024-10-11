<template>
  <div>
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
    <DialogExit/>
  </div>
</template>

<script>
  import DialogExit from './DialogExit.vue'

  export default {
    computed: {
      name() {
        let name = this.$store.getters['person/getFullName'];
        if (name !== `${undefined} ${undefined}`) {
          return name;
        }
      },
      show() {
        let name = this.$store.getters['person/getFullName'];
        if (name !== `${undefined} ${undefined}`) {
          return true;
        }
        return false;
      },
    },
    methods: {
      exit() {
        this.$store.commit('person/changeDialogMessage');
      }
    },
    components: {
      DialogExit
    }
  }
</script>
