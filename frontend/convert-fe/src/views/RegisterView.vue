<template>
    <div class="flex h-screen">
      <!-- Left Side: Image -->
      <div class="w-1/2 h-full">
        <img
          src="https://loremflickr.com/200/200?random=3"
          alt="Random Nature"
          class="h-full w-full object-cover"
        />
      </div>
  
      <!-- Right Side: Signup Form -->
      <div class="w-1/2 flex items-center justify-center bg-gray-50">
        <div class="w-96 bg-white p-8 shadow-lg rounded-lg">
          <!-- Signup Heading -->
          <h2 class="text-2xl font-semibold text-gray-800">Create your account</h2>
  
          <!-- Form -->
          <form @submit.prevent="handleSignup" class="mt-6">
            <!-- Name Input -->
            <div class="mb-4">
              <label for="name" class="block text-sm font-medium text-gray-600">Name</label>
              <input
                type="text"
                id="name"
                v-model="name"
                placeholder="Your Full Name"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-green-300 focus:outline-none"
                required
              />
            </div>
  
            <!-- Email Input -->
            <div class="mb-4">
              <label for="email" class="block text-sm font-medium text-gray-600">Email</label>
              <input
                type="email"
                id="email"
                v-model="email"
                placeholder="example@gmail.com"
                class="w-full mt-1 px-4 py-2 border border-gray-300 rounded-lg focus:ring focus:ring-green-300 focus:outline-none"
                required
              />
            </div>
  
            <!-- Password Input -->
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
  
            <!-- Submit Button -->
            <button
              type="submit"
              class="w-full bg-green-500 text-white py-2 px-4 rounded-lg shadow hover:bg-green-600 transition"
            >
              Sign Up
            </button>
          </form>
  
          <!-- Login Link -->
          <p class="text-center text-sm text-gray-500 mt-4">
            Already Have An Account? 
            <a href="#" class="text-green-500 hover:underline">Login</a>
          </p>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import { ref } from "vue";
  
  export default {
    setup() {
      const name = ref("");
      const email = ref("");
      const password = ref("");
  
      const handleSignup = () => {
        console.log("Name:", name.value);
        console.log("Email:", email.value);
        console.log("Password:", password.value);
        // Perform signup logic here
      };
  
      return {
        name,
        email,
        password,
        handleSignup,
      };
    },
  };
  </script>
  
  <style>
  /* Add any custom styling here */
  </style>