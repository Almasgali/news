<template>
    <v-form @submit.prevent="">
        <v-row>
            <v-col>
                <h3>Вход в личный аккаунт</h3>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="4">
                <v-text-field
                    type="email" 
                    label="Email"
                    v-model="person.email" 
                />
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="4">
                <v-text-field
                    type="password" 
                    label="Пароль"
                    v-model="person.password" 
                />
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <v-btn :to="{name: 'home'}">
                    Войти
                </v-btn>
            </v-col>
        </v-row>
        <v-row>
            <v-col>
                <router-link :to="{name: 'registration'}">Нет аккаунта? Создать</router-link>
            </v-col>
        </v-row>
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
        }
    }
</script>