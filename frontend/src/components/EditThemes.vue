<template>
    <div>
        <v-container>
            <v-row>
                <v-btn
                    icon="mdi-arrow-left"
                    variant="text"
                    :to="{name: 'home'}"
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
                    />
                </v-col>
                <v-spacer/>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                rules: {
                    theme: [
                        v => !!v || 'Введите тему'
                    ]
                },
                theme: '',
                themesDel: []
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
                console.log(this.theme);
                this.$store.dispatch('news/addTheme', {
                    token: this.$store.state.person.person.token,
                    theme: this.theme
                });
            },
            delThemes() {
                console.log(this.themesDel);
                if (this.themesDel) {
                    for (let i in this.themesDel) {
                        this.$store.dispatch('news/delTheme', {
                            token: this.$store.state.person.person.token,
                            themeId: this.themesDel[i]
                        });
                    }
                }
                this.themesDel = [];
            }
        }
    }
</script>

<style scoped>
</style>