<template>
    <div class="container">
        <b-navbar class="navbar">
            <template slot="brand">
                <b-navbar-item tag="router-link" :to="{ path: '/' }">
                    <img class="logo"
                         src="~/assets/images/logo.png"
                         alt="Logo du serveur"
                    >
                </b-navbar-item>
            </template>

            <template slot="start">

                <b-navbar-item class="link" v-for="link in links" tag="router-link" :to="{ path: link.path }">
                    {{ link.title}}
                </b-navbar-item>

            </template>

            <template v-if="isConnected" slot="end">

                <b-dropdown
                        v-model="navigation"
                        position="is-bottom-left"
                        append-to-body
                        aria-role="menu">
                    <a
                            class="navbar-item"
                            slot="trigger"
                            role="button">
                        <span>Menu</span>
                        <b-icon icon="menu-down"></b-icon>
                    </a>

                    <b-dropdown-item custom aria-role="menuitem">
                        Logged as <b>Rafael Beraldo</b>
                    </b-dropdown-item>
                    <hr class="dropdown-divider" aria-role="menuitem">
                    <b-dropdown-item value="settings">
                        <b-icon icon="settings"></b-icon>
                        Settings
                    </b-dropdown-item>
                    <b-dropdown-item value="logout" aria-role="menuitem">
                        <b-icon icon="logout"></b-icon>
                        Logout
                    </b-dropdown-item>
                </b-dropdown>
            </template>

            <template v-else slot="end">
                <b-navbar-item class="button btn is-primary" tag="router-link" :to="{ path: '/register' }">
                    Inscription
                </b-navbar-item>

                <b-navbar-item class="button btn is-light" tag="router-link" :to="{ path: '/login' }">
                    Connexion
                </b-navbar-item>
            </template>


        </b-navbar>

        <nuxt ></nuxt>


    </div>


</template>

<script>
    export default {
        data() {
            return {

                links: [
                    {
                        title: 'Home',
                        path: '/',
                    },
                    {
                        title: 'Forum',
                        path: '/forum',
                    },
                    {
                        title: 'Rules',
                        path: '/rules',
                    },
                    {
                        title: 'Shop',
                        path: '/shop',
                    }

                ]
            }
        },

        computed : {
           isConnected () {
               return this.$store.state.auth.username
           }
        },

      methods : {

      }
    }
</script>

<style>

    .navbar {
        height: 100px;
        margin: 50px 0;
    }

    .logo {
        width: 90px;
        height: 90px;
        margin-right: 30px;
        max-height: none !important;
    }

    .link {
        margin: 20px 0;
        height: 50px;
    }

    .btn {
        margin: 5px;
    }

</style>
