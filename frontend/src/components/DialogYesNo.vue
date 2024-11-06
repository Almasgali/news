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
                      text="Да"
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
                return this.$store.state.person.dialogYesNo;
            },
            message() {
                return this.$store.state.person.message;
            },
        },
        methods: {
            changeDialog() {
                this.$store.commit('person/changeDialogYesNo');
            },
            changeDialogExit() {
                if (this.message === "Вы уверены, что хойтите выйти?") {
                    this.$store.commit('person/delPerson');
                    this.$store.dispatch('news/loadNewsFromServer');
                } else {
                    if (this.$store.state.person.dialogSettings) {
                        this.$store.commit('person/changeDialogSettings')
                    }
                    this.$router.push({name: 'home'});
                }
                this.$store.commit('person/changeDialogYesNo');
            }
        }
    }
</script>