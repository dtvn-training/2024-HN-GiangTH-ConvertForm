<template>
    <div class="flex h-screen">
      <div class="w-1/2 h-full flex items-center justify-center">
        <img
          src="https://wallpapers.com/images/hd/plant-phone-3kyxtd2uvib3kl2g.jpg"
          alt="Random Nature"
          class="h-auto w-full object-cover"
        />
      </div>
  
      <div class="w-1/2 flex items-center justify-center bg-gray-50">
        <div class="w-96 bg-white p-8 shadow-lg rounded-lg">
          <h2 class="text-2xl font-semibold text-gray-800">Create your account</h2>
  
          <form @submit.prevent="handleSignup" class="mt-6">
            <div class="mb-4">
              <label for="name" class="block text-sm font-medium text-gray-600">UserName</label>
              <input
                type="text"
                id="name"
                v-model="name"
                placeholder="Your User Name"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-green-300 focus:outline-none"
                required
              />
            </div>

            <div class="mb-4">
              <label for="password" class="block text-sm font-medium text-gray-600">Password</label>
              <input
                type="password"
                id="password"
                v-model="password"
                placeholder="Create a password"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-green-300 focus:outline-none"
                required
              />
            </div>

            <button
              type="submit"
              class="w-full bg-green-500 text-white py-2 px-4 rounded-lg shadow hover:bg-green-600 transition"
              @click="handleSignup"
            >
              Sign Up
            </button>
          </form>
  
          <p class="text-center text-sm text-gray-500 mt-4">
            Already Have An Account? 
            <a href="/login" class="text-green-500 hover:underline">Login</a>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  import client from '../api/apiSetup'
  
  export default {
    setup() {
      const name = ref("");
      const password = ref("");
      const rePassword = ref("");
      const loading = ref(false)
  
      const handleSignup = async () => {
        if (loading.value) return;

        loading.value = true;
        try {
          const response = await client.post("/registry", {
            username: name.value,
            password: password.value,
          });
          console.log(response.status, response.data);
        } catch (error) {
          console.error("Signup failed:", error.response?.data || error.message);
        } finally {
          loading.value = false;
        }
      };
  
      return {
        name,
        password,
        rePassword,
        handleSignup,
      };
    },
  };
  </script>
  
  <style>

  </style>