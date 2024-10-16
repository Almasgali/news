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
                      text="Сохранить"
                      @click="changeDialogExit"
                    />
                    <v-btn
                      text="Отмена"
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
        },
        methods: {
            changeDialog() {
                this.$store.commit('person/changeDialogMessage');
            },
            changeDialogExit() {
                if (this.message === "Вы уверены, что хойтите выйти?") {
                    this.$store.commit('person/delPerson');
                } else {
                    this.$router.push({name: 'home'});
                }
                this.$store.commit('person/changeDialogMessage');
            }
        }
    }
</script>