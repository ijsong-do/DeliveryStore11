<template>

    <v-card outlined>
        <v-card-title>
            Pay
        </v-card-title>

        <v-card-text>
            <String label="OrderId" v-model="value.orderId" :editMode="editMode"/>
            <String label="Amount" v-model="value.amount" :editMode="editMode"/>
            <String label="Status" v-model="value.status" :editMode="editMode"/>
            <String label="CustomerId" v-model="value.customerId" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="pay"
            >
                Pay
            </v-btn>
            
            <v-btn
                    color="deep-purple lighten-2"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'PayCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.orderId = '';
            this.value.amount = '';
            this.value.status = '';
            this.value.customerId = '';
        },
        watch: {
        },
        methods: {
            pay() {
                this.$emit('pay', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

