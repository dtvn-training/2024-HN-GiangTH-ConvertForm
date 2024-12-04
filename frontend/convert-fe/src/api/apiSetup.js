import { useUserStore } from "@/store/userStorage";
import axios from "axios";

const client = axios.create({
    baseURL: 'http://localhost:8080/auth',
    headers: {
        'Content-Type': 'application/json'
    }
})

export default client;