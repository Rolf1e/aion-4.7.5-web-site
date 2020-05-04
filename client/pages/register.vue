<template>
    <div class="container">

        <h1 class="title"> Créer un compte </h1>

        <b-field label="Username">
            <b-input v-model="username" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Email">
            <b-input v-model="email" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Password">
            <b-input type="password" v-model="password" @keyup.native="error.show = false" @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-field label="Confirm your password">
            <b-input type="password" v-model="confirmPassword" @keyup.native="error.show = false"
                     @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-message v-if="error.show" type="is-danger">
            {{ this.error.message }}
        </b-message>

        <b-button type="button is-primary" :loading='loading' :disabled='loading' @click="register">Connexion</b-button>

    </div>
</template>

<script>

    import Swal from 'sweetalert2'

    export default {
        name: "register",

        data() {
            return {
                username: '',
                email: '',
                password: '',
                confirmPassword: '',
                error: {
                    show: false,
                    message: ''
                },
                loading: false

            }
        },

        methods: {
            async register() {

                this.loading = true

                if (this.password === this.confirmPassword && this.password != '') {

                    this.loading = true

                    const {data: response} = await this.$axios.post('http://aion-shard.com:8081/register', {
                        "username": this.username,
                        "password": this.password,
                        "mail" : this.email
                    })

                    this.loading = false

                    if (response.error === 'Successfully register user') {
                        Swal.fire({
                            title: 'Votre compte a été créé',
                            text: 'Veuillez vérifier votre boite mail pour valider votre compte',
                            icon: 'success',
                            confirmButtonText: 'Cool'
                        }).then((result) => {
                            if (result.value) {
                                this.$router.push({path: '/'})
                            }
                        })

                    } else {
                        this.error.message = 'Mot de passe ou email non valide'
                        this.error.show = true
                        this.username = ''
                        this.email = ''
                        this.password = ''
                        this.confirmPassword = ''
                    }

                } else {
                    this.error.message = 'Les mots de passe ne sont pas identiques'
                    this.error.show = true
                }

            },


        }
    }
</script>

<style scoped>

</style>
