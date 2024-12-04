import { useUserStore } from "@/store/userStorage";
import axios from "axios";

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

export default clientFile;