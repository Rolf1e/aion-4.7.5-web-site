
export const state = () => ({

  token: '',

})

export const getters =  {

  token : state => state.token
}

export const mutations = {

  SET_TOKEN(state, token) {
    state.token = token
  }

}

export const actions = {
  loadToken({commit, dispach}, token) {
    commit('SET_TOKEN', token)
  }
}
