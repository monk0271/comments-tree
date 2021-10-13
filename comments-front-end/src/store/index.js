import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token: ''
    },
    mutations: {
        Authorization(state, token){
            state.token = token;
            localStorage.token = token
        }

    },

})



//export default store;