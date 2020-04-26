<template>
    <div class="container">

        <b-field label="Username">
            <b-input v-model="username" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Password">
            <b-input type="password" v-model="password" @keyup.native="error.show = false" @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-message v-if='error.show' type="is-danger">
            L'email ou le mot de passe n'est pas valide.
        </b-message>

        <b-button type="button is-primary" @click="login">Connexion</b-button>

    </div>
</template>

<script>
    export default {
        name: "login",

        data() {
            return {
                username: '',
                password: '',
                error: {
                    show: false,
                    message: ''
                },
            }
        },

        methods: {
            async login() {

                if (this.username != '' && this.password != '') {

                    const {data: response} = await this.$axios.post('http://localhost:8080/token', {
                        "username": this.username,
                        "password": this.password
                    })

                    this.$store.dispatch('auth/loadToken', response.token)
                    this.$store.dispatch('auth/loadUsername', response.username)
                    this.$emit('refresh')
                    this.$router.push({path: '/'})

                } else {
                    this.error.show = true
                }
            },

        }
    }
</script>

<style scoped>

</style>
