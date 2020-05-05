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
                    <b-dropdown-item aria-role="listitem" @click="dropdownSelected(category)"> {{ category }}</b-dropdown-item>
                </div>

            </b-dropdown>

        </div>

        <div class="columns is-multiline">

            <ShopItem class="column is-one-third"
                      v-for="(item) in itemsToShow.slice(0, maxItems)"
                      :title="item.itemName"
                      :description="item.itemDescription"
                      :picture="item.itemPathToImageColumn"
                      :idItem="item.itemId"
                      :price="item.itemPrice">

            </ShopItem>

        </div>

        <div v-if="maxItems < itemsToShow.length " class="load-more-item has-text-centered">
            <b-button rounded @click="loadMoreItems">More items</b-button>
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

            const {data: categories} = await axios.get('http://51.178.130.119:8081/list-shop/category')

            const {data: items} = await axios.get('http://51.178.130.119:8081/list-shop')

            return {items, categories}
        },

        data() {
            return {
                items: null,
                filteredItems: null,
                dropdownText: 'All',

                maxItemsDefault: 30,
                maxItems: 30,
            }
        },

        computed: {
            itemsToShow() {
                return this.filteredItems || this.items
            }
        },

        methods: {

            dropdownSelected(category) {
                this.maxItems = 30
                this.dropdownText = category
                if (this.dropdownText === 'All') this.filteredItems = null
                else this.filteredItems = this.items.filter(x => x.itemCategory === this.dropdownText)
            },

            loadMoreItems() {
                this.maxItems = this.maxItems + 30
            }
        },


    }
</script>

<style scoped>

    .search-zone {
        margin-bottom: 50px;
    }

    .load-more-item {
        margin: 75px 0px;
    }

</style>