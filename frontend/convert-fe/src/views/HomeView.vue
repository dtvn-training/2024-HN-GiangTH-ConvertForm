<template>
  <div class="flex h-screen bg-gray-50">
    <!-- Sidebar -->
    <aside class="w-64 bg-gray-300 shadow-lg">
      <div class="h-full flex flex-col">
        <!-- Logo/Title -->
        <div class="p-6">
          <h2 class="text-2xl font-bold text-gray-800">Convert XLSX</h2>
        </div>

        <!-- Navigation -->
        <nav class="flex-1 px-4 py-6">
          <ul class="space-y-2">
            <li>
              <a href="/home" class="flex items-center px-4 py-3 text-gray-700 hover:bg-purple-50 hover:text-purple-700 rounded-lg transition-all duration-200">
                <span class="text-xl mr-3">🏠</span>
                <span class="font-medium">Home</span>
              </a>
            </li>
            <li>
              <a href="/history" class="flex items-center px-4 py-3 text-gray-700 hover:bg-purple-50 hover:text-purple-700 rounded-lg transition-all duration-200">
                <span class="text-xl mr-3">🕒</span>
                <span class="font-medium">History</span>
              </a>
            </li>
          </ul>
        </nav>

        <!-- User Profile -->
        <div class="border-t border-gray-200 p-4">
          <div class="flex items-center justify-between">
            <div class="flex items-center">
              <span class="w-10 h-10 rounded-full bg-purple-100 flex justify-center items-center text-purple-600 font-semibold">
                🧑
              </span>
              <span class="ml-3 font-medium text-gray-700">{{ userName }}</span>
            </div>
            <button 
              @click="logout"
              class="px-4 py-2 text-sm font-medium text-red-600 hover:text-red-700 hover:bg-red-50 rounded-lg transition-all duration-200"
            >
              Logout
            </button>
          </div>
        </div>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 overflow-auto bg-gray-200">
      <div class="max-w-4xl mx-auto p-8">
        <div class="mb-8">
          <h1 class="text-2xl font-bold text-gray-900 mb-2">
            Upload an XLSX file to convert
          </h1>
          <p class="text-gray-600">We'll convert your XLSX file to the right format.</p>
        </div>

        <!-- File Info Cards -->
        <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
          <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
            <div class="text-purple-600 mb-1">📁</div>
            <div class="text-sm font-medium text-gray-800">File size limit</div>
            <div class="text-sm text-gray-600">100MB</div>
          </div>
          <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
            <div class="text-purple-600 mb-1">📊</div>
            <div class="text-sm font-medium text-gray-800">File type</div>
            <div class="text-sm text-gray-600">XLSX only</div>
          </div>
          <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
            <div class="text-purple-600 mb-1">📋</div>
            <div class="text-sm font-medium text-gray-800">Requirements</div>
            <div class="text-sm text-gray-600">Must contain data</div>
          </div>
          <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100">
            <div class="text-purple-600 mb-1">🔒</div>
            <div class="text-sm font-medium text-gray-800">Security</div>
            <div class="text-sm text-gray-600">Data privacy guaranteed</div>
          </div>
        </div>

        <!-- Upload Section -->
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
          <div class="text-center">
            <input 
              type="file" 
              ref="fileInput"
              @change="handleFileSelect"
              accept=".xlsx,.xls"
              class="hidden"
              id="file-upload"
            >
            <label 
              for="file-upload"
              class="inline-flex items-center px-6 py-3 bg-purple-600 text-white font-medium rounded-lg hover:bg-purple-700 transition-colors cursor-pointer"
            >
              <span class="mr-2">📎</span>
              Choose File
            </label>
            <div class="selected-file" v-if="selectedFile">
              <span class="file-name">{{ selectedFile.name }}</span>
              <button class="clear-file" @click="clearFileSelection">×</button>
            </div>
            
            <button 
              @click="uploadFile" 
              :disabled="!selectedFile || loading"
              class="ml-4 px-6 py-3 bg-green-600 text-white font-medium rounded-lg hover:bg-green-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {{ loading ? 'Processing...' : 'Upload Excel' }}
            </button>
          </div>

          <div v-if="error" class="mt-4 p-4 bg-red-50 text-red-600 rounded-lg">
            {{ error }}
          </div>

          <!-- Results Section -->
          <div v-if="responseStatus" class="mt-6 border-t border-gray-100 pt-6">
            <h3 class="text-lg font-medium text-gray-800 mb-4">{{ responseStatus }}</h3>
            <div class="space-y-3">
              <div v-for="(file, index) in resultFiles" :key="index">
                <button 
                  @click="handleFileAction(file.fileId, file.fileName)"
                  class="w-full text-left px-4 py-3 bg-gray-50 hover:bg-gray-100 rounded-lg flex items-center justify-between group transition-colors"
                >
                  <span class="flex items-center">
                    <span class="text-gray-400 mr-3">📄</span>
                    <span class="text-gray-700">{{ file.fileName }}</span>
                  </span>
                  <span class="text-purple-600 group-hover:text-purple-700">Download</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  </div>
</template>

<script>
import { useUserStore } from '@/store/userStorage';
import { ref } from 'vue';
import clientFile from '@/api/apiFileSetup';
import router from '@/router';

export default {
  setup() {
    const userName = ref("Anonymous");
    const fileInput = ref(null);
    const selectedFile = ref(null);
    const loading = ref(false);
    const error = ref(null);
    const responseStatus = ref("")
    const resultFiles = ref([]);

    const userStore = useUserStore();
    userStore.loadFromStorage();

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
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        );

        console.log(response)
        resultFiles.value = [];

        if (response.status === 202) responseStatus.value = "Your input file have validation error, please modify and upload again!"

        else responseStatus.value = "Result File:"

        // Extract the main file metadata
        if (response.data.main_output) {
          resultFiles.value.push({
            fileName: response.data.main_output.fileName,
            fileId: response.data.main_output.fileId,
          });
        }

        // Extract the sharedLibFile metadata if it exists
        if (response.data.shared_lib_output) {
          resultFiles.value.push({
            fileName: response.data.shared_lib_output.fileName,
            fileId: response.data.shared_lib_output.fileId,
          });
        }
      
      } catch (err) {
        error.value = "Upload failed: " + err.message;
      } finally {
        loading.value = false;
        if (fileInput.value) fileInput.value.value = "";
        selectedFile.value = null;
      }
    };

    const handleFileAction = async (id, name) => {
      try {
        const response = await clientFile.get(
          `/download/${id}?fileName=${name}`,
          {
            responseType: 'blob',
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }
        )
        
        const blob = new Blob([response.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        const contentDisposition = response.headers["content-disposition"];
        let filename = name;

        if (contentDisposition) {
          const filenameMatch = contentDisposition.match(/filename="(.+)"/);
          if (filenameMatch?.length === 2) filename = filenameMatch[1];
        }

        downloadFile(blob, filename)

      } catch (err) {
        this.error = err.message || "Failed to download!";
      }
    };

    const clearFileSelection = () => {
      selectedFile.value = null;
      this.$refs.fileInput.value = '';
    };

    const downloadFile = async (blob, filename) => {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      window.URL.revokeObjectURL(url);
      link.remove();
    };

    const logout = () => {
      userStore.clearStorage();
      router.push({path: '/login'})
    }

    return {
      userName,
      fileInput,
      selectedFile,
      loading,
      error,
      handleFileSelect,
      handleFileAction,
      uploadFile,
      downloadFile,
      responseStatus,
      resultFiles,
      logout,
      clearFileSelection
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

  .fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* Optional: Add smooth scrolling to the main content */
main {
  scroll-behavior: smooth;
}

/* Optional: Custom scrollbar */
main::-webkit-scrollbar {
  width: 8px;
}

main::-webkit-scrollbar-track {
  background: #f1f1f1;
}

main::-webkit-scrollbar-thumb {
  background: #ddd;
  border-radius: 4px;
}

main::-webkit-scrollbar-thumb:hover {
  background: #ccc;
}
.selected-file {
  margin-top: 1rem;
  padding: 0.75rem;
  background-color: #d1dff0;
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.file-name {
  font-size: 0.875rem;
  color: #4a5568;
  word-break: break-all;
  margin-right: 0.5rem;
}
</style>