<template>
    <div>
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
                        :rules="rules.errorEmail"
                        type="email" 
                        label="Email"
                        />
                    </v-col>
                </v-row>
                <v-row justify="center">
                    <v-col>
                        <v-text-field
                        v-model="person.password"
                        :rules="rules.errorPassword"
                        type="password"
                        label="Пароль"
                        />
                    </v-col>
                </v-row>
                <v-row>
                    <v-col>
                        <v-btn
                        :disabled="bntDisabled"
                        @click="authentication"
                        >
                            Войти
                        </v-btn>
                    </v-col>
                </v-row>
                <v-row justify="end">
                    <v-col cols="6">
                        <router-link 
                        :to="{name: 'registration'}"
                        @click="clearPerson"
                        >
                            Нет аккаунта? Создать
                        </router-link>
                    </v-col>
                </v-row>
            </v-container>
        </v-form>
        <DialogMessage/>
    </div>
</template>

<script>
    import DialogMessage from './DialogMessage.vue'

    export default {
        data() {
            return {
                rules: {
                    errorEmail: [
                        v => !!v || 'Введите почту',
                        v => {
                            const pattern = this.$store.state.person.validEmailReg;
                            return pattern.test(v) || 'Почта введена неверно';
                        }
                    ],
                    errorPassword: [
                        v => !!v || 'Введите пароль'
                    ]
                }
            }
        },
        computed: {
            person() {
                return this.$store.state.person.person;
            },
            bntDisabled() {
                return this.$store.getters['person/getAuthBtnDisabled'];
            }
        },
        methods: {
            authentication() {
                this.$store.commit('person/changeDialogMessage');
                let person = {
                    email: this.person.email,
                    password: this.person.password
                };
                this.$store.dispatch('person/sendAuthInfoToServer', person);
            },
            clearPerson() {
                this.$store.commit('person/delPerson');
            }
        },
        components: {
            DialogMessage
        }
    }
</script>