<template>
  <div>
    <v-app-bar>
      <v-spacer/>
      {{ name }}
      <v-btn
        v-show="isAdmin"
        @click="settings"
        icon="mdi-cog-outline"
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
    <DialogSettings/>
  </div>
</template>

<script>
  import DialogYesNo from './DialogYesNo.vue'
  import DialogSettings from './DialogSettings.vue';

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
      isAdmin() {
        if (this.show) {
          return !this.$store.state.person.person.admin;
        }
        return false;
      }
    },
    methods: {
      exit() {
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти?"});
        this.$store.commit('person/changeDialogYesNo');
      },
      settings() {
        this.$store.commit('person/changeDialogSettings');
      }
    },
    components: {
      DialogYesNo,
      DialogSettings
    }
  }
</script>
