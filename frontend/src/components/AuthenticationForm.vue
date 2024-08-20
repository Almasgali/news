<template>
    <v-form @submit.prevent="">
        <v-container class="container">
            <v-row>
                <v-col>
                    <h3>
                        Вход в личный аккаунт
                    </h3>
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <v-text-field
                      v-model="person.email"
                      type="email" 
                      label="Email"
                    />
                </v-col>
            </v-row>
            <v-row justify="center">
                <v-col>
                    <v-text-field
                      v-model="person.password"
                      type="password"
                      label="Пароль"
                    />
                </v-col>
            </v-row>
            <v-row>
                <v-col>
                    <v-btn
                      :to="{name: 'home'}"
                      @click="authentication"
                    >
                        Войти
                    </v-btn>
                </v-col>
            </v-row>
            <v-row justify="end">
                <v-col cols="6">
                    <router-link :to="{name: 'registration'}">
                        Нет аккаунта? Создать
                    </router-link>
                </v-col>
            </v-row>
        </v-container>
    </v-form>
</template>

<script>
    export default {
        data() {
            return {
                person: {
                    email: '',
                    password: ''
                }
            }
        },
        methods: {
            authentication() {
                this.$store.commit('person/setPerson', this.person)
            }
        },
    }
</script>