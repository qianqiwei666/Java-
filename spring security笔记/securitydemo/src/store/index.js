import Vue from 'vue'
import Vuex from 'vuex'
import {verification_code,register,login,agent} from '../network/send'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

  },
  getters: {

  },
  mutations: {

  },
  actions: {
    verification_code({state,commit}){
      return  verification_code();
    },
    login({state,commit},products){
      return login(products.username,products.password,products.code);
    },
    register({state,commit},products){
      return register(products.username,products.password,products.code);
    },
    agent({state,commit}){
      return agent();
    }

  }
})
