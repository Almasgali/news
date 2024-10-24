<template>
    <div>
        <v-form @submit.prevent="">
            <v-container class="container">
                <v-row>
                    <v-btn
                        :to="{name: 'authentication'}"
                        icon="mdi-arrow-left"
                        variant="text"
                    />
                </v-row>
                <v-row>
                    <v-col>
                        <h3>Регистрация нового пользователся</h3>
                    </v-col>
                </v-row>
                <v-row justify="center">
                    <v-col>
                        <v-text-field
                        v-model="person.name"
                        :rules="rules.errorName"
                        type="text" 
                        label="Имя"
                        />
                    </v-col>
                    <v-col>
                        <v-text-field
                        v-model="person.surname"
                        :rules="rules.errorSurname"
                        type="text" 
                        label="Фамилия"
                        />
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
                        :disabled="btnDisabled"
                        @click="registration"
                        >
                            Зарегистрироваться
                        </v-btn>
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
                    errorName: [
                        v => !!v || 'Введите Имя',
                        v => {
                            const pattern = this.$store.state.person.validNameReg;
                            return pattern.test(v) || 'Имя может содержать только буквы';
                        }
                    ],
                    errorSurname: [
                        v => !!v || 'Введите Фамилию',
                        v => {
                            const pattern = this.$store.state.person.validNameReg;
                            return pattern.test(v) || 'Фамилия может содержать только буквы';
                        }
                    ],
                    errorEmail: [
                        v => !!v || 'Введите почту',
                        v => {
                            const pattern = this.$store.state.person.validEmailReg;
                            return pattern.test(v) || 'Почта введена неверно';
                        }
                    ],
                    errorPassword: [
                        v => !!v || 'Введите пароль',
                        v => {
                            const pattern = this.$store.state.person.validPasswordReg;
                            return pattern.test(v) || 'Пароль должен содержать не менее 8 символов, хотя бы одну заглавую и строчную буквы, цифру и спец символ #?!@$%^&*-';
                        }
                    ]
                }
            }
        },
        computed: {
            person() {
                return this.$store.state.person.person;
            },
            btnDisabled() {
                return this.$store.getters['person/getRegBtnDisabled'];
            }
        },
        methods: {
            registration() {
                this.$store.commit('person/changeDialogMessage');
                let person = this.person;
                this.$store.dispatch('person/sendRegInfoToServer', person);
            }
        },
        components: {
            DialogMessage
        }
    }
</script>