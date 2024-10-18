<template>
  <div>
    <v-app-bar>
      <v-spacer/>
      {{ name }}
      <v-btn
        v-if="show"
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
          v-model="showSettigs"
          width="auto"
          class="pa-4 text-center"
        >
            <v-card
              max-width="600"
            >
              <v-row>
                <v-col>
                  Выберите любимые темы
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-select
                  label="Темы"
                  v-model="favouriteThemes"
                  :items="allThemes"
                  item-title="name"
                  item-value="name"
                  multiple
                />
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  Выберите запретные темы, статьи с выбранными темами отображаться не будут
                </v-col>
              </v-row>
              <v-row>
                <v-col>
                  <v-select
                  label="Темы"
                  v-model="forbiddenThemes"
                  :items="allThemes"
                  item-title="name"
                  item-value="name"
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
        showSettigs: false,
        favouriteThemes: [],
        forbiddenThemes: []
      }
    },
    computed: {
      btnDisabled() {
        if (this.favouriteThemes.length == 0 || this.forbiddenThemes.length == 0) {
          return true;
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
      allThemes() {
          return this.$store.state.news.allThemes;
      }
    },
    methods: {
      exit() {
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти?"});
        this.$store.commit('person/changeDialogMessage');
      },
      settings() {
        this.showSettigs = !this.showSettigs;
      },
      saveSettings() {
        console.log(this.favouriteThemes, this.forbiddenThemes);
        for (let i in this.favouriteThemes) {
          this.$store.dispatch('person/addFavoriteTheme', this.favouriteThemes[i]);
        }
        for (let i in this.forbiddenThemes) {
          this.$store.dispatch('person/addForbiddenTheme', this.forbiddenThemes[i]);
        }
        this.showSettigs = !this.showSettigs;
      },
      exitSettings() {
        this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти? Изменения не сохранятся"});
        this.showSettigs = !this.showSettigs;
      }
    },
    components: {
      DialogYesNo
    }
  }
</script>
