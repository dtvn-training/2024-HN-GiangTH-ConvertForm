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
            return !!state.token
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
        },
        saveToLocal(uid, userName, jwt) {
            this.uid = uid;
            this.userName = userName;
            this.token = jwt;
            const userData = {
                uid: uid,
                token: jwt,
                userName: userName
            }
            localStorage.setItem("userStore", JSON.stringify(userData))
        },
        loadFromStorage() {
            const userData = localStorage.getItem("userStore");
            if (userData) {
                const { uid, token, userName } = JSON.parse(userData);
                this.uid = uid;
                this.token = token;
                this.userName = userName;
            }
        },
        clearStorage() {
            localStorage.removeItem("userStore");
            this.uid = null;
            this.token = null;
            this.userName = null;
        }
    }
})