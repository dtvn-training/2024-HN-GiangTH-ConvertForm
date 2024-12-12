import { useUserStore } from "@/store/userStorage";
import axios from "axios";
import client from "./apiSetup";

const clientFile = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: {
        "Content-Type": "multipart/form-data"
    }
})

clientFile.interceptors.request.use(
    config => {
        const userStore = useUserStore()
        const token = userStore.getToken

        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }

        return config
    },
    error => {
        return Promise.reject(error)
    }
)

clientFile.interceptors.response.use(
    (response) => response,
    async (error) => {
        const originalRequest = error.config;
        const userStore = useUserStore()

        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const oldRefreshToken = userStore.refresh;
                const response = await client.post('/refresh', {
                    refreshToken: oldRefreshToken
                });

                const { accessToken, refreshToken } = response.data;

                // Update tokens in store  
                userStore.setToken(accessToken)
                userStore.setRefresh(refreshToken)
                userStore.updateStorageToken(accessToken, refreshToken)

                // Retry the original request with new token
                originalRequest.headers.Authorization = `Bearer ${accessToken}`;
                return clientFile(originalRequest);
            } catch(refreshError) {
                router.push('/login');
                return Promise.reject(refreshError);
            }
        }
    }
)

export default clientFile;