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
      <b-input type="password" v-model="confirmPassword" @keyup.native="error.show = false" @keyup.native.enter="login"
               password-reveal></b-input>
    </b-field>

    <b-message v-if="error.show" type="is-danger">
      {{ this.error.message }}
    </b-message>




    <b-button  type="button is-primary" @click="login">Connexion</b-button>

  </div>
</template>

<script>
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
      async login() {

        if (this.password === this.confirmPassword && this.password != '') {

          const response = await this.$axios.post('http://localhost:8080/register', {
            "username": this.username,
            "password": this.password,
            "mail": this.email
          })

          this.openToast('Un mail a été envoyé !!')

        } else {
          this.error.message = 'Les mots de passe ne sont pas identiques'
          this.error.show = true
        }

        /*
        if (this.email == 'arthur' && this.password == 'mdp') {
          console.log('le mdp est bon')
          let token = 'mon_token'
          this.$store.dispatch('auth/loadToken', token )
          this.$router.push({path : '/'})
        } else {
          this.error.show = true
        }
        */

      },

      openToast(message) {
        this.$buefy.toast.open({
          message: message,
          type: 'is-success',
          duration : 15000
        })
      }

    }
  }
</script>

<style scoped>

</style>
