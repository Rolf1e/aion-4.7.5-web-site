<template>
    <div>

        <div class="box">

            <article class="media">

                <div class="media-left">
                    <figure class="image is-96x96">
                        <img class="picture" :src="pictureLink">
                    </figure>
                </div>

                <div class="media-content">

                    <div>
                        <h3 class="title"> {{ title }}</h3>
                        <p class="description">
                            <a target="_blank" :href="this.itemLink"> Show more information about item </a>
                        </p>
                    </div>

                </div>

            </article>

            <div class="price-block">
                <div>
                    <b-button type="is-light btn" :disabled="btnDisabled" @click="buy" outlined> Buy item</b-button>
                </div>
                <p class="price"> {{ price }} Shards </p>
            </div>

        </div>

    </div>
</template>

<script>
    import Swal from 'sweetalert2'

    export default {
        name: "ShopItem",

        data() {
            return {
                btnDisabled: !this.$store.state.auth.token,
                lightDescription: this.description.substr(0, 40),
                showDescription: false
            }
        },

        props: {
            title: String,
            description: String,
            picture: String,
            price: Number,
            idItem: Number,
        },

        computed: {
            pictureLink() {
                return `http://51.178.130.119:80/img/${this.picture}.png`
            },

            itemLink() {
                return `https://aioncodex.com/4x/item/${this.idItem}`
            }
        },

        methods: {

            buy() {

                Swal.fire({
                    title: 'Username',
                    input: 'text',
                    inputAttributes: {
                        autocapitalize: 'off'
                    },

                    showCancelButton: true,
                    confirmButtonText: 'Send',
                    showLoaderOnConfirm: true,

                    preConfirm: async (pseudo) => {

                        return this.$axios.get(`http://51.178.130.119:8081/check-players-exist?name=${pseudo}`)
                            .then(response => {

                                if (!response.data) {
                                    throw new Error('User not found')
                                }
                                return pseudo
                            })
                            .catch(error => {
                                Swal.showValidationMessage(
                                    `${error}`
                                )
                            })
                    },
                    allowOutsideClick: () => !Swal.isLoading()
                }).then(async (result) => {

                    if (result.value) {

                        this.$axios.post('http://51.178.130.119:8081/buy', {
                            'token': this.$store.state.auth.token,
                            'idItem': this.idItem,
                            'countItem': 1,
                            'recipient': result.value
                        })
                            .then(response => {

                                response = response.data

                                if (!response.error) {

                                    this.$store.dispatch('auth/setShard', response.shardBalance)

                                    Swal.fire(
                                        'Great !',
                                        'Object successfully send',
                                        'success',
                                    )
                                } else {
                                    Swal.fire(
                                        'Error',
                                        response.message,
                                        'error',
                                    )
                                }
                            })

                            .catch(error => {
                                Swal.showValidationMessage(
                                    `${error}`
                                )
                            })

                        console.log(response)


                    }
                })
            },


        }
    }
</script>

<style scoped>

    .box{
        color: white;
        background-color: rgba(0,0,0,0.8);
    }

    .title {
        color: white;
    }

    .price-block {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .price {
        font-size: 22px;
        margin-top: 13px;
    }

    .picture {
        height: 100%;
        width: 100%;
    }

    .title {
        font-size: 18px;
        margin-bottom: 10px;
    }

    .description {
        font-size: 15px;
    }

    .description:hover {
        cursor: pointer;
    }

    .box {
        min-height: 200px;
        margin: 5px;
    }

</style>