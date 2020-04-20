<template>
    <div class="container">

        <h1 class="title"> Créer un compte </h1>

        <b-field label="Pseudo">
            <b-input v-model="username" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Email">
            <b-input v-model="email" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Mot de passe">
            <b-input type="password" v-model="password" @keyup.native="error.show = false" @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-field label="Comfirmer mot de passe">
            <b-input type="password" v-model="confirmPassword" @keyup.native="error.show = false"
                     @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-message v-if="error.show" type="is-danger">
            {{ this.error.message }}
        </b-message>


        <b-button type="button is-primary" @click="register">Connexion</b-button>

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
            }
        },

        methods: {
            async register() {


                if (this.password === this.confirmPassword && this.password != '') {

                    const {data: response} = await this.$axios.post('http://localhost:8080/register', {
                        "username": this.username,
                        "password": this.password,
                        "mail": this.email
                    })

                    console.log(response)

                    if (response.error === false) {
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
