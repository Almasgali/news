<template>
    <div>
        <v-dialog
          v-model="dialog"
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
            newFavouriteThemes: this.$store.state.person.favouriteThemes,
            newForbiddenThemes: this.$store.state.person.forbiddenThemes
        }
      },
      computed: {
        dialog() {
            return this.$store.state.person.dialogSettings;
        },
        btnDisabled() {
            for (let i in this.newFavouriteThemes) {
                if (this.newForbiddenThemes.find(item => item.name == this.newFavouriteThemes[i].name)) {
                    return true;
                }
            }
          return false;
        },
        allThemes() {
            return this.$store.state.news.allThemes;
        }
      },
      methods: {
        saveSettings() {
            (async () => {
                await this.$store.dispatch('person/getFavouriteThemes')
                await this.$store.dispatch('person/getForbiddenThemes')
                
                let favouriteThemes = this.$store.state.person.favouriteThemes;
                let forbiddenThemes = this.$store.state.person.forbiddenThemes;
    
                for (let i in favouriteThemes) {
                    await this.$store.dispatch('person/delFavouriteTheme', {theme: favouriteThemes[i].name});
                }
                for (let i in this.newFavouriteThemes) {
                    await this.$store.dispatch('person/addFavouriteTheme', {theme: this.newFavouriteThemes[i].name});
                }
                for (let i in forbiddenThemes) {
                    await this.$store.dispatch('person/delForbiddenTheme', {theme: forbiddenThemes[i].name});
                }
                for (let i in this.newForbiddenThemes) {
                    await this.$store.dispatch('person/addForbiddenTheme', {theme: this.newForbiddenThemes[i].name});
                }
                await this.$store.dispatch('person/getFavouriteThemes')
                await this.$store.dispatch('person/getForbiddenThemes')
                await this.$store.dispatch('news/loadNewsFilterFromServer',
                    this.$store.getters['person/getIdThemes']);
            })();
            this.$store.commit('person/changeDialogSettings');
        },
        exitSettings() {
            this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти? Изменения не сохранятся"});
            this.$store.commit('person/changeDialogYesNo');
        }
      },
      components: {
        DialogYesNo
      },
      created() {
        (async () => {
            await this.$store.dispatch('person/getFavouriteThemes')
            await this.$store.dispatch('person/getForbiddenThemes')
            this.newFavouriteThemes = this.$store.state.person.favouriteThemes;
            this.newForbiddenThemes = this.$store.state.person.forbiddenThemes;
        })();
      }
    }
  </script>
  