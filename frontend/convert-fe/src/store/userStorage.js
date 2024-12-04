import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => (
        {
            uid: null,
            token: null,
            userName: null
        }
    ),
    getters: {
        getToken(state) {
            return state.token
        },

        getUId(state) {
            return state.uid
        },

        isLoggedIn(state) {
            return state.token != null
        },

        getUserName(state) {
            return state.userName
        }
    },
    actions: {
        setUId(uid) {
            this.uid = uid;
        },
        setToken(jwt) {
            this.token = jwt;
        },
        setName(userName) {
            this.userName = userName;
        }
    }
})