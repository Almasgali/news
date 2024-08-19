<template>
    <v-form @submit.prevent="">
        <v-row>
            <v-col>
                <h3>Регистрация нового пользователся</h3>
            </v-col>
        </v-row>
        <v-row>
            <v-col cols="2">
                <v-text-field
                    type="text" 
                    label="Имя"
                    v-model="person.name" 
                />
            </v-col>
            <v-col cols="2">
                <v-text-field
                    type="text" 
                    label="Фамилия"
                    v-model="person.lastName" 
                />
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
                <v-btn :to="{name: 'home'}"
                    @click="authenti">
                    Зарегистрироваться
                </v-btn>
            </v-col>
        </v-row>
    </v-form>
</template>

<script>
    export default {
        data() {
            return {
                person: {
                    name: '',
                    lastName: '',
                    email: '',
                    password: ''
                }
            }
        },
        methods: {
            authenti() {
                this.$store.commit('addPerson', this.person)
            }
        },
    }
</script>