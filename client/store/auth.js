import Cookie from 'js-cookie'


export const state = () => ({

    token: '',
    username: '',
    shard : '',
    premium : false,

})

export const getters = {

    token: state => state.token,
    username: state => state.username,
    shard: state => state.shard,
    premium: state => state.premium,

}

export const mutations = {

    SET_TOKEN(state, token) {
        state.token = token
    },

    SET_USERNAME(state, username) {
        state.username = username
    },

    SET_SHARD(state, shard) {
        state.shard = shard
    },

    SET_PREMIUM(state, premium) {
        state.premium = premium
    },


}

export const actions = {
    loadToken({commit}, token) {
        commit('SET_TOKEN', token)
        Cookie.set('token', token, {expires : 1})
    },

    loadUsername({commit}, username) {
        commit('SET_USERNAME', username)
        Cookie.set('username', username, {expires : 1})
    },

    setShard({commit}, shard) {
        commit('SET_SHARD', shard)
    },

    setPremium({commit}, premium) {
        commit('SET_PREMIUM', premium)
    },
    logout( {commit}) {
        commit('SET_TOKEN', null)
        commit('SET_USERNAME', null)
        commit('SET_SHARD', null)
        commit('SET_PREMIUM', null)
        Cookie.remove('token')
        Cookie.remove('username')
    }


}
