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
                    <v-text-field
                      label="Ссылка на картинку"
                      :rules="rules.img"
                      :counter="255"
                      v-model="img"
                      :value="img"
                      type="url"
                    />
                    <img
                      :width="300"
                      :src="img"
                    >
                </v-col>
                <v-col>
                    <v-textarea
                        label="Заголовок"
                        variant="outlined"
                        :rules="rules.title"
                        :counter="255"
                        v-model="title"
                        :model-value="title"
                        clearable
                    />
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-select
                        label="Темы"
                        :rules="rules.themes"
                        v-model="themes"
                        :items="allThemes"
                        item-title="name"
                        item-value="id"
                        multiple
                    />
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-textarea
                        label="Текст статьи"
                        variant="outlined"
                        :rules="rules.text"
                        v-model="text"
                        :model-value="text"
                        clearable
                        rows="5"
                        no-resize
                    />
                </v-col>
            </v-row>
            <v-row>
                <v-col cols="4">
                    <v-btn
                        :disabled="!btnDisabled"
                        :to="{name: 'home'}"
                        @click="saveNews"
                    >
                        Сохранить изменения
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
        <DialogYesNo/>
    </div>
</template>

<script>
    import DialogYesNo from './DialogYesNo.vue'

    export default {
        data() {
            return {
                rules: {
                    title: [
                        v => !!v || 'Введите заголовок',
                        v => (v && v.length <= 255) || 'Максимум 255 символов'
                    ],
                    text: [v => !!v || 'Введите текст'],
                    img: [
                        v => (v && v.length <= 255) || 'Максимум 255 символов',
                        v => (!!v && !this.imgLoad) || 'Введите ссылку на картинку'
                    ],
                    themes: [
                        v => v.length !== 0 || 'Выберите темы'
                    ]
                },
                img: this.$store.getters['news/getNewsById'].image || '',
                title: this.$store.getters['news/getNewsById'].title || '',
                text: this.$store.getters['news/getNewsById'].text || '',
                themes: this.$store.getters['news/getNewsById'].themes || []
            }
        },
        computed: {
            btnDisabled() {
                return this.title && this.title.length <= 255 && this.text;
            },
            imgLoad() {
                let img = new Image();
                img.src = this.img;
                img.onload = () => {
                    return true;
                }
                return false;
            },
            allThemes() {
                this.$store.dispatch('news/loadAllThemesFromServer');
                return this.$store.state.news.allThemes;
            }
        },
        methods: {
            saveNews() {
                let data = {
                    image: this.img,
                    title: this.title,
                    text: this.text
                };
                let before = this.$store.getters['news/getNewsById'].themes;
                if (this.$store.state.news.editNewsId) {
                    this.$store.dispatch('news/editNews', {
                        token: this.$store.state.person.person.token,
                        data: data
                    });
                    if (before) {
                        for (let i in before) {
                            if (!this.themes.find(item => item === before[i].id)) {
                                this.$store.dispatch('news/delThemesInNews', {
                                    token: this.$store.state.person.person.token,
                                    themeId: before[i].id
                                });
                            }
                        }
                    }
                    for (let i in this.themes) {
                        if (before && !before.find(item => item.id === this.themes[i])) {
                            this.$store.dispatch('news/addThemesInNews', {
                                token: this.$store.state.person.person.token,
                                themeId: this.themes[i]
                            });
                        }
                    }
                } else {
                    this.$store.dispatch('news/createNews', {
                        token: this.$store.state.person.person.token,
                        data: data
                    });
                }
                this.$store.commit('news/setEditNewsId', null);
            },
            exit() {
                this.$store.commit('person/setMessage', {message: "Вы уверены, что хойтите выйти из редактора? Изменения не сохранятся"});
                this.$store.commit('person/changeDialogMessage');
            }
        },
        components: {
            DialogYesNo
        }
    }
</script>

<style scoped>
</style>