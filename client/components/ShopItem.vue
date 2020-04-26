<template>
    <div>

        <div class="box">
            <article class="media" style="margin-bottom: 30px">
                <div class="media-left">
                    <figure class="image is-96x96">
                        <img class="picture" :src="picture">
                    </figure>
                </div>

                <div class="media-content">
                    <div>
                        <p>
                            <strong> {{ title }}</strong>
                            <!--                            <small>@johnsmith</small> <small>31m</small>-->
                            <br>
                            {{ description }}
                        </p>

                    </div>


                </div>
            </article>

            <div class="price-block">
                <div>
                    <b-button type="is-dark btn" @click="buy" outlined> Acheter</b-button>
                    <b-button type="is-dark btn" @click="gift" outlined> Cadeau</b-button>
                </div>
                <p class="price"> {{ price }} € </p>
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
                title: 'Potion',
                price: 19.99,
                description: 'Je suis la potion Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean efficitur sit amet massa fringilla egestas. Nu',
                //picture: 'http://aion-shard.com/img/cbt_as_alertstance_g2.png'
                picture: 'https://bulma.io/images/placeholders/128x128.png'

            }
        },

        methods : {

            buy(){
                Swal.fire(
                    'Yeah!',
                    'Stylé ton nouveau item',
                    'success',
                )
            },

            gift() {

                Swal.fire({
                    title: 'Username de la personne',
                    input: 'text',
                    inputAttributes: {
                        autocapitalize: 'off'
                    },
                    showCancelButton: true,
                    confirmButtonText: 'Envoyer',
                    showLoaderOnConfirm: true,
                    preConfirm: (login) => {
                        return fetch(`//api.github.com/users/${login}`)
                            .then(response => {
                                if (!response.ok) {
                                    throw new Error('Utilisateur non trouvé')
                                }
                                return response.json()
                            })
                            .catch(error => {
                                Swal.showValidationMessage(
                                    `${error}`
                                )
                            })
                    },
                    allowOutsideClick: () => !Swal.isLoading()
                }).then((result) => {
                    if (result.value) {
                        Swal.fire(
                            'Cool !',
                            'Ton ami a de la chance',
                            'success',
                        )
                    }
                })
            }

        }
    }
</script>

<style scoped>

    .price-block {
        display: flex;
        justify-content: space-between;
    }

    .price {
        font-size: 22px;
    }

    .price-block  .btn {
        margin-left: 10px;
    }

    .picture {
        height: 100%;
        width: 100%;
    }

</style>