<template>
    <v-card
      class="mx-auto pa-10"
      max-width="500px"
    >
        <v-card-text>
            {{ message }}
        </v-card-text>
        <v-card-actions>
            <v-btn
              :to="{name: link}"
              text="OK"
            />
        </v-card-actions>
    </v-card>
</template>

<script>
    export default {
        computed: {
            message() {
                return this.$store.state.person.message;
            },
            link() {
                if (this.message === "Пользователь успешно авторизован.") {
                    return 'home';
                }
                this.$store.commit('person/delPerson');
                return 'authentication';
            }
        }
    }
</script>