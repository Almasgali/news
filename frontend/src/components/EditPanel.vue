<template>
    <div>
        <v-container>
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
                <v-btn
                    :disabled="!btnDisabled"
                    :to="{name: 'home'}"
                    @click="saveNews"
                >
                    Сохранить изменения
                </v-btn>
            </v-row>
        </v-container>
    </div>
</template>

<script>
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
                    ]
                },
                img: this.$store.getters['news/getNewsById'].image || '',
                title: this.$store.getters['news/getNewsById'].title || '',
                text: this.$store.getters['news/getNewsById'].text || ''
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
            }
        },
        methods: {
            saveNews() {
                let data = {
                    image: this.img,
                    title: this.title,
                    text: this.text
                }
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
                this.$store.commit('news/setEditNewsId', null);
            }
        }
    }
</script>

<style scoped>
</style>