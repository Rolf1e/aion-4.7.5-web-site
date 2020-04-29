const Cookie = require('js-cookie')

export const actions = {

    nuxtServerInit(context) {

    },

    nuxtClientInit({commit}) {

        if (process.client) {

            const username = Cookie.get('username')
            const token = Cookie.get('token')

            if (username !== undefined && token !== undefined) {
                commit('auth/SET_USERNAME', username)
                commit('auth/SET_TOKEN', token)
            }
        }

    }

}