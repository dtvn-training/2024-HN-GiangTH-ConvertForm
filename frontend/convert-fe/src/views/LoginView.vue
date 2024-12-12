<template>
    <div class="flex h-screen w-full">
      <div class="w-1/2 h-auto">
        <img
          src="https://wallpapers.com/images/hd/plant-phone-3kyxtd2uvib3kl2g.jpg"
          alt="Random Plant"
          class="h-full w-full object-cover"
        />
      </div>
  
      <div class="w-1/2 flex items-center justify-center bg-gray-50">
        <div class="w-96 bg-white p-8 shadow-lg rounded-lg">
          <h2 class="text-2xl font-semibold text-gray-800">Login to your account</h2>
  
          <form @submit.prevent="handleLogin" class="mt-6">
            <div class="mb-4">
              <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
              <input
                type="text"
                id="email"
                v-model="email"
                placeholder="example@gmail.com"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-blue-300 focus:outline-none"
                required
              />
            </div>
  
            <div class="mb-4">
              <label for="password" class="block text-sm font-medium text-gray-600">Password</label>
              <div class="flex items-center">
                <input
                  type="password"
                  id="password"
                  v-model="password"
                  placeholder="Enter your password"
                  class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-blue-300 focus:outline-none"
                  required
                />
                <a href="#" class="ml-2 text-sm text-blue-500 hover:underline">Forgot?</a>
              </div>
            </div>
  
            <button
              type="submit"
              class="w-full bg-blue-500 text-white py-2 px-4 rounded-lg shadow hover:bg-blue-600 transition"
            >
              Login now
            </button>
          </form>
  
          <p class="text-center text-sm text-gray-500 mt-4">
            Don't Have An Account? 
            <a href="/register" class="text-blue-500 hover:underline">Sign Up</a>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  import client from "@/api/apiSetup";
  import router from "@/router";
  import { useUserStore } from "@/store/userStorage";

  const userStore = useUserStore()
  
  export default {
    setup() {
      const email = ref("");
      const password = ref("");
  
      return {
        email,
        password
      };
    },
    methods: {
      async handleLogin () {
        try {
          const response = await client.post('/sign-in', {
            username: email.value,
            password: password.value
          })

          console.log(response.status + response.data)

          if (response.status == 202) {
            console.log(response.data)
            userStore.saveToLocal(response.data.uid, response.data.userName, response.data.token, response.data.refreshToken)
            console.log(userStore.getUserName + userStore.getUId + userStore.getToken)
            router.push({path: '/home'})
          }
        } catch (error) {
          console.error("Signin failed:", error.response?.data || error.message);
        }
      }
    }
  };
  </script>
  
  <style>

  </style>