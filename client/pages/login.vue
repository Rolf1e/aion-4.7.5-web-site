<template>
    <div class="container">

        <b-field label="Username" custom-class="has-text-white">
            <b-input v-model="username" @keyup.native="error.show = false" @keyup.native.enter="login"></b-input>
        </b-field>

        <b-field label="Password" custom-class="has-text-white">
            <b-input type="password" v-model="password" @keyup.native="error.show = false" @keyup.native.enter="login"
                     password-reveal></b-input>
        </b-field>

        <b-message v-if='error.show' type="is-danger">
            {{ error.message}}
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

                if (this.username === '' || this.password === '') {
                    this.error.show = true
                    this.error.message = 'All fields are required.'
                    return
                }

                const {data: response} = await this.$axios.post('http://51.178.130.119:8081/login', {
                    "username": this.username,
                    "password": this.password
                })

                if (response.error === 'Successfully getting token') {
                    this.$store.dispatch('auth/loadToken', response.token)
                    this.$store.dispatch('auth/loadUsername', response.username)
                    this.$store.dispatch('auth/setShard', response.shard)
                    this.$store.dispatch('auth/setPremium', response.premium)
                    this.$emit('refresh')
                    this.$router.push({path: '/'})
                    return
                }

                this.error.show = true
                this.error.message = response.error
            },

        }
    }
</script>

<style scoped>

    .container {
        background-color: rgba(0,0,0,0.6);
        color: white;
        padding: 2%;
        margin-top: 5%;
    }

</style>
