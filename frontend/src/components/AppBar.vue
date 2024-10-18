<template>
  <div>
    <v-app-bar>
      <v-spacer/>
      {{ name }}
      <v-btn
        v-if="show"
        @click="settings"
        icon="cog-outline"
      />
      <v-btn
        v-if="show"
        @click="exit"
        icon="mdi-login"
      />
      <v-btn
        v-else
        :to="{name: 'authentication'}"
      >
        Войти
      </v-btn>
    </v-app-bar>
    <DialogYesNo/>
  </div>
</template>

<script>
  import DialogYesNo from './DialogYesNo.vue'

  export default {
    data() {
      return {
        showSettigs: false
      }
    },
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
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти?"});
        this.$store.commit('person/changeDialogMessage');
      },
      settings() {
        this.showSettigs = !this.showSettigs;
      }
    },
    components: {
      DialogYesNo
    }
  }
</script>
