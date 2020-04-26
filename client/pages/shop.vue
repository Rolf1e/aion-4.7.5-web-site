<template>
    <div>
        <div class="columns is-multiline">
            <div class="column is-one-third">
                <ShopItem></ShopItem>
            </div>


        </div>
    </div>
</template>

<script>

    import ShopItem from "../components/ShopItem";

    export default {
        name: "shop",

        components: {
            ShopItem
        },
    }
</script>

<style scoped>

</style>