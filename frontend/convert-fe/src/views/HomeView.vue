<template>
  <div class="flex h-screen bg-gray-100">
    <!-- Sidebar -->
    <aside class="w-1/4 bg-gray-400 p-4 flex-col justify-center items-center">
      <h2 class="text-lg font-semibold text-gray-800 mb-4">Convert XLSX</h2>
      <nav>
        <ul>
          <li class="mb-2">
            <a
              href="/home"
              class="flex items-center text-gray-600 hover:bg-gray-300 px-3 py-2 rounded-lg"
            >
              <span class="mr-2">🏠</span> Home
            </a>
          </li>
          <li>
            <a
              href="/history"
              class="flex items-center text-gray-600 hover:bg-gray-300 px-3 py-2 rounded-lg"
            >
              <span class="mr-2">🕒</span> History
            </a>
          </li>
        </ul>
      </nav>
      <div class="absolute bottom-4 left-4 flex items-center">
        <span class="w-10 h-10 rounded-full bg-purple-300 flex justify-center items-center">
          🧑
        </span>
        <p class="ml-2 text-gray-700">{{ userName }}</p>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-8 bg-gray-300">
      <h1 class="text-xl font-semibold text-gray-800">
        Upload an XLSX file to convert to right format
      </h1>
      <p class="text-gray-600 mt-1">We'll convert your XLSX file.</p>

      <!-- File Info -->
      <div class="flex flex-wrap gap-4 mt-6 text-sm text-gray-700">
        <div class="px-4 py-2 bg-gray-200 rounded-lg">File size limit: 100MB</div>
        <div class="px-4 py-2 bg-gray-200 rounded-lg">File type: xlsx</div>
        <div class="px-4 py-2 bg-gray-200 rounded-lg">File must contain data</div>
        <div class="px-4 py-2 bg-gray-200 rounded-lg">
          Please wait for the conversion to finish
        </div>
      </div>
      <p class="text-sm mt-2 text-gray-500">Data privacy guaranteed</p>

      <!-- Upload Section -->
      <div class="mt-8">
        <div class="file-upload">
          <div class="upload-container">
            <input 
              type="file" 
              ref="fileInput"
              @change="handleFileSelect"
              accept=".xlsx,.xls"
              class="file-input"
            >
            <button 
              @click="uploadFile" 
              :disabled="!this.selectedFile || loading"
              class="upload-button"
            >
              {{ loading ? 'Uploading...' : 'Upload Excel' }}
            </button>
          </div>
      
          <div v-if="error" class="error-message">
            {{ error }}
          </div>
      
          <div v-if="loading" class="loading">
            Processing file...
          </div>
        </div>

        <!-- Drag and Drop Area -->
        <div
          class="response-area mt-4 border-2 border-dashed border-gray-300 rounded-lg p-8 text-center bg-white"
        >
          <span class="text-gray-600">Result file:</span>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { useUserStore } from '@/store/userStorage';
import { ref } from 'vue';
import axios from 'axios';
import clientFile from '@/api/apiFileSetup';

export default {
  setup() {
    const userName = ref("Anonymous");
    const fileInput = ref(null);
    const selectedFile = ref(null);
    const loading = ref(false);
    const error = ref(null);

    const userStore = useUserStore()
    userName.value = userStore.getUserName

    const handleFileSelect = (event) => {
      selectedFile.value = event.target.files[0];
      error.value = null;
    };

    const uploadFile = async () => {
      if (!selectedFile.value) {
        error.value = "Please select a file first";
        return;
      }

      loading.value = true;
      error.value = null;

      const formData = new FormData();
      formData.append("file", selectedFile.value);

      try {
        const response = await clientFile.post('/upload', 
          formData,
          {
            responseType: 'blob',
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        );

        const blob = new Blob([response.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        const contentDisposition = response.headers["content-disposition"];
        let filename = "convert_main_output.xlsx";
        if (contentDisposition) {
          const filenameMatch = contentDisposition.match(/filename="(.+)"/);
          if (filenameMatch?.length === 2) filename = filenameMatch[1];
        }

        downloadFile(blob, filename);
      } catch (err) {
        error.value = "Upload failed: " + err.message;
      } finally {
        loading.value = false;
        if (fileInput.value) fileInput.value.value = "";
        selectedFile.value = null;
      }
    };

    const downloadFile = (blob, filename) => {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      window.URL.revokeObjectURL(url);
      link.remove();
    };

    return {
      userName,
      fileInput,
      selectedFile,
      loading,
      error,
      handleFileSelect,
      uploadFile,
      downloadFile,
    };
  },
};
</script>

<style>
.file-upload {
    margin: 20px;
    padding: 20px;
    border: 2px dashed #ccc;
    border-radius: 8px;
  }

  .down_button {
    margin: 20px;
    padding: 20px;
    border: 2px dashed #ccc;
    border-radius: 8px;
  }
  
  .upload-container {
    display: flex;
    flex-direction: column;
    gap: 10px;
    align-items: center;
  }
  
  .file-input {
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
    width: 100%;
    max-width: 300px;
  }
  
  .upload-button {
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s;
  }
  
  .upload-button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  
  .upload-button:hover:not(:disabled) {
    background-color: #45a049;
  }
  
  .error-message {
    color: red;
    margin-top: 10px;
    padding: 10px;
    background-color: #ffebee;
    border-radius: 4px;
  }
  
  .loading {
    margin-top: 10px;
    color: #666;
  }
</style>