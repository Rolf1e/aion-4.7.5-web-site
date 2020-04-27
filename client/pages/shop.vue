<template>
    <div>


        <div class="search-zone">

            <b-dropdown aria-role="list">
                <button class="button is-primary" slot="trigger" slot-scope="{ active }">
                    <span> {{ dropdownText }}</span>
                    <b-icon :icon="active ? 'menu-up' : 'menu-down'"></b-icon>
                </button>

                <b-dropdown-item aria-role="listitem" @click="dropdownSelected('All')">All</b-dropdown-item>

                <div v-for="category in categories">
                    <b-dropdown-item aria-role="listitem" @click="dropdownSelected(category)"> {{ category }}
                    </b-dropdown-item>
                </div>

            </b-dropdown>

        </div>

        <div class="columns is-multiline">

            <ShopItem class="column is-one-third"
                      v-for="(item, index) in items"
                      v-if="(item.itemCategory == dropdownText || dropdownText == 'All') && index < maxItems"
                      :title="item.itemName"
                      :description="item.itemDescription"
                      :picture="item.itemPathToImageColumn"
                      :price="item.itemPrice">

            </ShopItem>

        </div>

        <div class="load-more-item has-text-centered">
            <b-button rounded @click="loadMoreItems"> Charger plus d'items</b-button>
        </div>

    </div>
</template>

<script>
    const axios = require('axios');

    import ShopItem from "../components/ShopItem";

    export default {
        name: "shop",

        components: {
            ShopItem
        },

        async asyncData() {

            const {data: listsCategories} = await axios.get('http://51.178.130.119:8081/list-shop/category')

            const {data: response} = await axios.get('http://51.178.130.119:8081/list-shop')

            return {
                items: response,
                categories: listsCategories
            }
        },

        data() {
            return {
                items: null,
                dropdownText: 'All',
                maxItems: 30,
            }
        },

        methods: {

            dropdownSelected(category) {
                this.dropdownText = category
            },

            loadMoreItems() {
                this.maxItems = this.maxItems + 30
            }
        },

        computed: {},


    }
</script>

<style scoped>

    .search-zone {
        margin-bottom: 50px;
    }

    .load-more-item {
        margin : 75px 0px;
    }

</style>