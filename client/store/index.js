const Cookie = require('js-cookie')
const axios = require('axios')

export const actions = {

    nuxtServerInit(context) {

    },

    async nuxtClientInit({commit}) {

        if (process.client) {

            const username = Cookie.get('username')
            const token = Cookie.get('token')

            if (username !== undefined && token !== undefined) {
                commit('auth/SET_USERNAME', username)
                commit('auth/SET_TOKEN', token)

                const { data : response} = await axios.post('http://aion-shard.com:8081/login', {
                    token : token
                })

                commit('auth/SET_SHARD', response.shard)

            }
        }

    }

}