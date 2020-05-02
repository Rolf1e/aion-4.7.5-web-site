import Cookie from 'js-cookie'


export const state = () => ({

    token: '',
    username: ''

})

export const getters = {

    token: state => state.token,
    username: state => state.username
}

export const mutations = {

    SET_TOKEN(state, token) {
        state.token = token
    },

    SET_USERNAME(state, username) {
        state.username = username
    }


}

export const actions = {
    loadToken({commit, dispach}, token) {
        commit('SET_TOKEN', token)
        Cookie.set('token', token, {expires : 1})
    },

    loadUsername({commit, dispach}, username) {
        commit('SET_USERNAME', username)
        Cookie.set('username', username, {expires : 1})
    },


}
