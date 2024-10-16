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
                        item-value="name"
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
                    text: this.text,
                    themes: this.themes
                };
                console.log(data);
                if (this.$store.state.news.editNewsId) {
                    this.$store.dispatch('news/editNews', {
                        token: this.$store.state.person.person.token,
                        data: data
                    });
                } else {
                    this.$store.dispatch('news/createNews', {
                        token: this.$store.state.person.person.token,
                        data: data
                    });
                }
                let news = this.$store.state.news.news;
                for (let i in news) {
                    console.log(news[i].id);
                    this.$store.dispatch('news/loadLikesFromServer', news[i].id);
                    this.$store.dispatch('news/loadCommentsFromServer', news[i].id);
                    this.$store.dispatch('news/loadThemesFromServer', news[i].id);
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