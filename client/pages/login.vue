<template>
  <div class="container">

    <b-field label="Email">
      <b-input v-model="email" @keyup.native="showError = false" @keyup.native.enter="login" ></b-input>
    </b-field>

    <b-field label="Password">
      <b-input type="password" v-model="password" @keyup.native="showError = false" @keyup.native.enter="login" password-reveal></b-input>
    </b-field>

    <b-message v-if='showError' type="is-danger">
      L'email ou le mot de passe n'est pas valide.
    </b-message>

    <b-button type="button is-primary" @click="login" >Connexion</b-button>

  </div>
</template>

<script>
  export default {
    name: "login",

    data() {
      return {
        email: '',
        password : '',
        showError : false,
      }
    },

    methods : {
      login() {

        if (this.email == 'arthur' && this.password == 'mdp') {
          console.log('le mdp est bon')
          let token = 'mon_token'
          this.$store.dispatch('auth/loadToken', token )
          this.$router.push({path : '/'})
        } else {
          this.showError = true

        }
      },

    }
  }
</script>

<style scoped>

</style>
