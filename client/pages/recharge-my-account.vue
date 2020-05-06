<template>
    <div class="paypal-case">
        <paypal></paypal>

    </div>
</template>

<script>

    import paypal from '~/components/Paypal'
    export default {
        name: "recharge-my-account",
        components : {
            paypal
        }
    }

</script>

<style scoped>
    .paypal-case {
        margin-top: 5%;
    }
</style>


