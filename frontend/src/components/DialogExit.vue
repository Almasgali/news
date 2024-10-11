<template>
    <div class="text-center">
        <v-dialog
          v-model="dialog"
          width="auto"
        >
            <v-card
              max-width="400"
              text="Вы уверены, что хойтите выйти?"
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
                return this.$store.state.person.dialogMessage;
            }
        },
        methods: {
            changeDialog() {
                this.$store.commit('person/changeDialogMessage');
            },
            changeDialogExit() {
                this.$store.commit('person/changeDialogMessage');
                this.$store.commit('person/delPerson');
            }
        }
    }
</script>