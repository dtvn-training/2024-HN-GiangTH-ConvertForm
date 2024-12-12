import { defineStore } from "pinia";

export const useUserStore = defineStore('user', {
    state: () => {
        const userData = localStorage.getItem("userStore");

        const initState = {
            uid: null,
            token: null,
            refresh: null,
            userName: null
        }

        if (userData) {
            const parsed = JSON.parse(userData)
            return {
                uid: parsed.uid,
                token: parsed.token,
                refresh: parsed.refresh,
                userName: parsed.userName
            }
        }

        return initState
    },
        
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
        setRefresh(refresh) {
            this.refresh = refresh;
        },
        saveToLocal(uid, userName, jwt, refresh) {
            this.uid = uid;
            this.userName = userName;
            this.token = jwt;
            this.refresh = refresh;
            const userData = {
                uid: uid,
                token: jwt,
                refresh: refresh,
                userName: userName
            }
            localStorage.setItem("userStore", JSON.stringify(userData))
        },
        loadFromStorage() {
            const userData = localStorage.getItem("userStore");
            if (userData) {
                const { uid, token, userName, refresh } = JSON.parse(userData);
                this.uid = uid;
                this.token = token;
                this.refresh = refresh;
                this.userName = userName;
            }
        },
        updateStorageToken(jwt, refresh) {
            const userData = localStorage.getItem("userStore");
            if (userData) {
                userData = {
                    token: jwt,
                    refresh: refresh,
                    ...userData
                }
                localStorage.setItem("userStore", JSON.stringify(userData))
            }
        },

        clearStorage() {
            localStorage.removeItem("userStore");
            this.uid = null;
            this.token = null;
            this.refresh = null;
            this.userName = null;
        }
    }
})