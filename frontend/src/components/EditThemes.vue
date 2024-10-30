<template>
    <div>
        <v-container>
            <v-row>
                <v-btn
                    icon="mdi-arrow-left"
                    variant="text"
                    @click="exit"
                />
            </v-row>
            <v-row>
                <v-col>
                    Для удаления тем выберите темы и нажмите на кнопку удалить. 
                </v-col>
            </v-row>
            <v-row>
                <v-spacer/>
                <v-col cols="5">
                    <v-select
                        label="Темы"
                        v-model="themesDel"
                        :items="allThemes"
                        item-title="name"
                        item-value="id"
                        multiple
                    />
                </v-col>
                <v-col>
                    <v-btn
                      :disabled="delBtnDisabled"
                      @click="delThemes"
                      icon="mdi-delete-outline"
                      size="small"
                      elevation="0"                                
                    />
                </v-col>
                <v-spacer/>
            </v-row>
            <v-row>
                <v-spacer/>
                <v-col ols="5">
                    <v-text-field
                      label="Тема"
                      :rules="rules.theme"
                      v-model="theme"
                    />
                </v-col>
                <v-col>
                    <v-btn
                        :disabled="!addBtnDisabled"
                        @click="addTheme"
                        size="small"
                        text="Добавить"
                    />
                </v-col>
                <v-spacer/>
            </v-row>
        </v-container>
        <DialogMessage/>
        <DialogYesNo/>
    </div>
</template>

<script>
    import DialogMessage from './DialogMessage.vue';
    import DialogYesNo from './DialogYesNo.vue';

    export default {
        data() {
            return {
                rules: {
                    theme: [
                        v => !!v || 'Введите тему'
                    ]
                },
                theme: '',
                themesDel: [],
            }
        },
        computed: {
            addBtnDisabled() {
                return this.theme;
            },
            delBtnDisabled() {
                return this.themesDel.length == 0;
            },
            allThemes() {
                return this.$store.state.news.allThemes;
            }
        },
        methods: {
            addTheme() {
                this.$store.dispatch('news/addTheme', {
                    token: this.$store.state.person.person.token,
                    theme: this.theme
                });
                this.theme = ''; 
                this.$store.commit('person/setMessage', {message: "Тема добавлена"});
                this.$store.commit('person/changeDialogMessage');
            },
            delThemes() {
                if (this.themesDel) {
                    for (let i in this.themesDel) {
                        this.$store.dispatch('news/delTheme', {
                            token: this.$store.state.person.person.token,
                            themeId: this.themesDel[i]
                        });
                    }
                }
                this.themesDel = [];
                this.$store.commit('person/setMessage', {message: "Темы удалены"});
                this.$store.commit('person/changeDialogMessage');
            },
            exit() {
                this.$store.commit('person/setMessage', {message: "Хотите вернуться на главную?"});
                this.$store.commit('person/changeDialogYesNo');
            }
        },
        components: {
            DialogMessage,
            DialogYesNo
        }
    }
</script>

<style scoped>
</style>