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

    SET_USERNAME(state, token) {
        state.username = token
    }


}

export const actions = {
    loadToken({commit, dispach}, token) {
        commit('SET_TOKEN', token)
    },

    loadUsername({commit, dispach}, token) {
        commit('SET_USERNAME', token)
    },


}
