<template>
    <div class="text-center">
        <v-dialog
          v-model="dialog"
          width="auto"
        >
            <v-card
              max-width="400"
              :text="message"
            >
                <template v-slot:actions>
                    <v-btn
                      :to="{name: link}"
                      text="OK"
                      @click="changeDialog"
                    />
                </template>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>
    export default {
        computed: {
            dialog() {
                return this.$store.state.person.dialogMessage;
            },
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
        },
        methods: {
            changeDialog() {
                this.$store.commit('person/changeDialogMessage');
            }
        }
    }
</script>