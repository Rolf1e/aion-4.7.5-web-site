<template>

    <div class="container">

        <img class="background" src="http://51.178.130.119:80/media/background3.jpg">

        <b-navbar class="navbar">

            <template slot="brand">
                <b-navbar-item tag="router-link" :to="{ path: '/' }">
                    <img class="logo"
                         src="http://51.178.130.119/media/logo.png"
                         alt="Logo du serveur"
                    >
                </b-navbar-item>
            </template>

            <template slot="start">

                <b-navbar-item class="link" v-for="link in links" tag="nuxt-link" :to="{ path: link.path }">
                    {{ link.title}}
                </b-navbar-item>

            </template>

            <template v-if="username" slot="end">


                <b-navbar-item class="link-shard"  tag="nuxt-link" :to="{ path: '/recharge-my-account' }">
                    Shards : {{ shards }}

                </b-navbar-item>

                <b-dropdown class="menu"
                        position="is-bottom-left"
                        append-to-body
                        aria-role="menu">
                    <a class="navbar-item"
                            slot="trigger"
                            role="button">
                        <span>Menu</span>
                        <b-icon icon="menu-down"></b-icon>
                    </a>

                    <b-dropdown-item custom aria-role="menuitem">
                        Logged as <b> {{ username}} </b>
                        <hr class="dropdown-divider" aria-role="menuitem">
                        <b>{{ premium ? 'Premium' : 'Free'}}</b> Account
                    </b-dropdown-item>
                    <hr class="dropdown-divider" aria-role="menuitem">
                    <b-dropdown-item  has-link aria-role="menuitem">
                        <nuxt-link :to="{ path: '/recharge-my-account' }">
                            <b-icon icon="settings"></b-icon>
                            Recharge my account
                        </nuxt-link>

                    </b-dropdown-item>
                    <b-dropdown-item value="logout" aria-role="menuitem" @click="logout">
                        <b-icon icon="logout"></b-icon>
                        Logout
                    </b-dropdown-item>
                </b-dropdown>
            </template>

            <template v-else slot="end">
                    <b-navbar-item tag="div">
                        <nuxt-link :to="{ path: '/register' }" class="button btn is-primary">
                            <strong>Sign up</strong>
                        </nuxt-link>
                        <nuxt-link :to="{ path: '/login' }" class="button btn is-light">
                            Log in
                        </nuxt-link>
                    </b-navbar-item>
            </template>

        </b-navbar>

        <nuxt></nuxt>

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
                        title: 'Rules',
                        path: '/rules',
                    },
                    {
                        title: 'Shop',
                        path: '/shop',
                    },
                    {
                        title: 'Download',
                        path: '/download',
                    }

                ]
            }
        },

        computed: {
            username() {
                return this.$store.state.auth.username
            },

            shards () {
                return this.$store.state.auth.shard
            },
            premium () {
                return this.$store.state.auth.premium
            }
        },

        methods: {
            logout() {
                this.$store.dispatch('auth/logout')
                this.$router.push({path: '/'})
            }
        }
    }
</script>

<style scoped>
    .container .background {
        position: fixed;
        right: 0;
        bottom: 0;
        min-width: 100%;
        min-height: 100%;
        width: auto;
        height: auto;
        z-index: -100;
        background-size: 100% auto;
    }

    .navbar {
        height: 10%;
        margin-top: 5%;
        background-color: rgba(0,0,0,0.6);
        padding-right: 3%;
        font-size: 120%;
    }

    .navbar .link {
        height: 50%;
        margin-top: 10%;
    }

    .navbar .link-shard {
        height: 50%;
        margin-top: 14%;
    }

    .logo {
        width: 100px;
        height: 100px;
        max-height: none !important;
    }

    .btn {
        margin: 3%;
    }

    .navbar-item {
        /*font-size: 115%;*/
        color: white;
    }
</style>
