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
    <v-dialog
      v-model="showSettings"
      width="auto"
      class="pa-4 text-center"
    >
      <v-card
        max-width="600"
      >
        <v-row class="px-4 ma-4">
          <v-col>
            Выберите любимые темы
          </v-col>
        </v-row>
        <v-row class="mx-4">
          <v-col>
            <v-select
            label="Темы"
            v-model="newFavouriteThemes"
            :items="allThemes"
            item-title="name"
            return-object
            multiple
          />
          </v-col>
        </v-row>
        <v-row class="px-4 ma-4">
          <v-col>
            Выберите запретные темы, статьи с выбранными темами отображаться не будут
          </v-col>
        </v-row>
        <v-row class="mx-4">
          <v-col>
            <v-select
            label="Темы"
            v-model="newForbiddenThemes"
            :items="allThemes"
            item-title="name"
            return-object
            multiple
          />
          </v-col>
        </v-row>
        <template v-slot:actions>
          <v-btn
            :disabled="btnDisabled"
            text="Сохранить"
            @click="saveSettings"
          />
          <v-btn
            text="Отмена"
            @click="exitSettings"
          />
        </template>
      </v-card>
    </v-dialog>
    <DialogYesNo/>
  </div>
</template>

<script>
  import DialogYesNo from './DialogYesNo.vue'

  export default {
    data() {
      return {
        showSettings: false,
        newFavouriteThemes: this.$store.state.person.favouriteThemes,
        newForbiddenThemes: this.$store.state.person.forbiddenThemes
      }
    },
    computed: {
      btnDisabled() {
        for (let i in this.newFavouriteThemes) {
          if (this.newForbiddenThemes.find(item => item.name == this.newFavouriteThemes[i].name)) {
            return true;
          }
        }
        return false;
      },
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
      },
      allThemes() {
        return this.$store.state.news.allThemes;
      }
    },
    methods: {
      exit() {
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти?"});
        this.$store.commit('person/changeDialogYesNo');
      },
      settings() {
        console.log(this.showSettings)
        this.showSettings = !this.showSettings;
      },
      saveSettings() {
        console.log("save settings");
        let favouriteThemes = this.$store.state.person.favouriteThemes;
        let forbiddenThemes = this.$store.state.person.forbiddenThemes;

        console.log("new fav", this.newFavouriteThemes);
        console.log("new for", this.newForbiddenThemes);
        console.log("old fav", favouriteThemes);
        console.log("old for", forbiddenThemes);

        for (let i in favouriteThemes) {
          this.$store.dispatch('person/delFavouriteTheme', {theme: favouriteThemes[i].name});
        }
        for (let i in this.newFavouriteThemes) {
          this.$store.dispatch('person/addFavouriteTheme', {theme: this.newFavouriteThemes[i].name});
        }
        for (let i in forbiddenThemes) {
            this.$store.dispatch('person/delForbiddenTheme', {theme: forbiddenThemes[i].name});
        }
        for (let i in this.newForbiddenThemes) {
          this.$store.dispatch('person/addForbiddenTheme', {theme: this.newForbiddenThemes[i].name});
        }
        this.$store.dispatch('person/getFavouriteThemes');
        this.$store.dispatch('person/getForbiddenThemes'); 

        // this.$store.dispatch('news/loadNewsFilterFromServer', 
        //   this.$store.getters['person/getIdThemes']
        // )
        this.showSettings = !this.showSettings;
      },
      exitSettings() {
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти? Изменения не сохранятся"});
        this.showSettings = !this.showSettings;
        }
    },
    components: {
      DialogYesNo
    }
  }
</script>
