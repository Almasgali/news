<template>
    <div>
        <v-container>
            <v-row>
                <v-col>
                    image
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
                    text: [v => !!v || 'Введите текст']
                },
                img: '',
                title: this.$store.getters['news/getNewsById'].title,
                text: this.$store.getters['news/getNewsById'].text
            }
        },
        computed: {
            btnDisabled() {
                return this.title && this.title.length <= 255 && this.text;
            }
        },
        methods: {
            saveNews() {
                this.$store.dispatch('news/saveNews', {
                    title: title,
                    text: text
                });
                this.$store.commit('news/setEditNewsId', null);
            }
        }
    }
</script>

<style scoped>
</style>