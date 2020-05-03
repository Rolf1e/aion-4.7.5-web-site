<template>
    <div>

        <b-field :type="error ? 'is-danger' : 'false'">
            <b-input placeholder="Ammount"
                     @keyup.native="verifyAmmount"
                     v-model="ammount"
                     icon-right="currency-eur">
            </b-input>
        </b-field>

        <h1 class="shards"> Number of Shards : {{ numberOfShard }} </h1>

        <div class="has-text-centered" ref="paypal"></div>

    </div>
</template>

<script>

    export default {

        name: "Paypal",

        data() {
            return {
                error: false,
                ammount: 4,
            }
        },

        computed: {

            numberOfShard() {
                return isNaN(Math.floor(this.ammount * 50)) ? 0 :  Math.floor(this.ammount * 50)
            }
        },

        mounted() {
            const script = document.createElement("script")
            script.src = "https://www.paypal.com/sdk/js?client-id=AZ2iwgULZ5w_pjyDSJ53UnTNOIIHEC-TDMJyJBJAa9dg6Mqlu3ZKMP-gtzc9DadQaXwUnq6CrqnIk_Ad&currency=EUR"
            script.addEventListener("load", this.setLoaded)
            document.body.appendChild(script)
        },

        methods: {
            setLoaded() {

                window.paypal.Buttons({
                    style: {
                        shape: 'rect',
                        color: 'silver',
                        layout: 'vertical',
                        label: 'paypal',

                    },
                    createOrder: (data, actions) => {
                        return actions.order.create({
                            purchase_units: [
                                {
                                    description: 'buy shards',
                                    amount: {
                                        currency_code: "EUR",
                                        value: this.error ? 0 : parseFloat(this.ammount).toFixed(2)
                                    }
                                }
                            ]
                        })
                    },

                    onApprove: async (data, actions) => {
                        const order = await actions.order.capture()
                        const paymentId = order.purchase_units[0].payments.captures[0].id
                        const orderId = order.id
                        const token = this.$store.state.auth.token

                    },

                    onError: err => {
                        console.log(err)
                    }

                }).render(this.$refs.paypal)
            },
            verifyAmmount() {

                this.ammount = this.ammount.replace(',', '.')

                if (isNaN(this.ammount)) {
                    this.error = true

                } else {
                    this.error = false
                }
            }
        },


    }

</script>

<style scoped>

    .shards {
        margin-bottom: 40px;
    }

</style>