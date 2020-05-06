import Swal from "sweetalert2";
<template>
    <div>

        <b-field :type="error ? 'is-danger' : 'false'">
            <b-input placeholder="Amount"
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

    import Swal from 'sweetalert2'

    export default {

        name: "Paypal",

        data() {
            return {
                error: false,
                ammount: 1,
            }
        },

        computed: {

            numberOfShard() {
                return isNaN(Math.floor(this.ammount * 50)) ? 0 :  Math.floor(this.ammount * 50)
            }
        },

        mounted() {
            const script = document.createElement("script")
            script.src = "https://www.paypal.com/sdk/js?client-id=AVS8bzdunfsXRbvrkpFRmm0BVw1mZbJxuqF9gB4uOF9EXK72jDHMEx35UkTXwv4AdStIqwe04qdDPjJQ&currency=EUR"
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

                        console.log(paymentId)

                        const { data : response } = await this.$axios.post('http://51.178.130.119:8081/purchase/shards', {
                            'token' : token,
                            'transactionId' : paymentId
                        })

                        if (!response.error) {
                            Swal.fire(
                                'Great !',
                                'Shard successfully send',
                                'success',
                            )
                            this.$store.dispatch('auth/setShard', response.shardBalance )

                        }


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
        margin-bottom: 5%;
        color: white;
    }

</style>