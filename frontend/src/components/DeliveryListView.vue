<template>

    <v-data-table
        :headers="headers"
        :items="deliveryList"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'DeliveryListView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            deliveryList : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/deliveryLists'))

            temp.data._embedded.deliveryLists.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.deliveryList = temp.data._embedded.deliveryLists;
        },
        methods: {
        }
    }
</script>

