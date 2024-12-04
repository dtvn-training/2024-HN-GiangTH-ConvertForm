<template>
    <div class="flex h-screen w-full">
      <!-- Left Side: Image -->
      <div class="w-1/2 h-auto">
        <img
          src="https://loremflickr.com/200/200?random=1"
          alt="Random Plant"
          class="h-full w-full object-cover"
        />
      </div>
  
      <!-- Right Side: Login Form -->
      <div class="w-1/2 flex items-center justify-center bg-gray-50">
        <div class="w-96 bg-white p-8 shadow-lg rounded-lg">
          <!-- Login Heading -->
          <h2 class="text-2xl font-semibold text-gray-800">Login to your account</h2>
  
          <!-- Form -->
          <form @submit.prevent="handleLogin" class="mt-6">
            <!-- Email Input -->
            <div class="mb-4">
              <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
              <input
                type="email"
                id="email"
                v-model="email"
                placeholder="example@gmail.com"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-blue-300 focus:outline-none"
                required
              />
            </div>
  
            <!-- Password Input -->
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
  
            <!-- Submit Button -->
            <button
              type="submit"
              class="w-full bg-blue-500 text-white py-2 px-4 rounded-lg shadow hover:bg-blue-600 transition"
            >
              Login now
            </button>
          </form>
  
          <!-- Sign Up Link -->
          <p class="text-center text-sm text-gray-500 mt-4">
            Don't Have An Account? 
            <a href="#" class="text-blue-500 hover:underline">Sign Up</a>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  
  export default {
    setup() {
      const email = ref("");
      const password = ref("");
  
      const handleLogin = () => {
        console.log("Email:", email.value);
        console.log("Password:", password.value);
        // Perform login logic here
      };
  
      return {
        email,
        password,
        handleLogin,
      };
    },
  };
  </script>
  
  <style>
  /* Add any custom styling here if necessary */
  </style>