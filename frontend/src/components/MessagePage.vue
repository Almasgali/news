<template>
    <v-card
      class="mx-auto pa-10"
      max-width="500px"
    >
        <v-card-title>
            {{ message.title }}
        </v-card-title>
        <v-card-subtitle>
            {{ message.subTitle }}
        </v-card-subtitle>
        <v-card-text>
            {{ message.text }}
        </v-card-text>
        <v-card-actions>
            <v-btn
              :to="{name: message.link}"
              text="OK"
            />
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        computed: {
            message() {
                let status = this.$store.state.person.message;
                let res = {
                    title: '',
                    subTitle: '',
                    text: '',
                    link: ''
                }
                if (status == 201) {
                    res.title = "Успех"
                    res.subTitle = "Вы успешно зарегистрированы";
                    res.text = "Теперь вы можете войти в свой аккаунт, нажмите кнопку OK чтобы перейти к странице авторизации";
                    res.link = "authentication";
                } else if (status == 200) {
                    res.title = "Ошибка";
                    res.subTitle = "Пользователь с такой почтой уже зарегистрирован";
                    res.text = "Вы можете войти в свой аккаунт, нажмите кнопку OK чтобы перейти к странице авторизации";
                    res.link = "authentication";
                } 
                return res;
            }
        }
    }
</script>