<template>
  <div class="app-container">
    <!-- Sidebar -->
    <aside class="w-1/4 bg-gray-400 p-4">
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
    <main class="main-content">
      <div v-if="loading" class="loading">Loading history...</div>
      <div v-else-if="error" class="error">Error: {{ error }}</div>
      <div v-else class="file-grid">
        <FileItem
          v-for="(fileGroup, index) in files"
          :key="index"
          :file="fileGroup"
          @file-action="handleFileAction"
        />
      </div>
    </main>
  </div>
</template>

<script>
import FileItem from "../component/FileItem.vue";
import { useUserStore } from "@/store/userStorage";
import clientFile from "@/api/apiFileSetup";

export default {
  components: {
    FileItem,
  },
  data() {
    const userStore = useUserStore()
    userStore.loadFromStorage()
    return {
      files: null,
      loading: true, 
      error: null, 
      userName: userStore.getUserName,
      userId: userStore.getUId ? userStore.getUId : 1
    };
  },
  methods: {
    // Fetch history data from the backend
    async fetchHistory() {
      try {
        const response = await clientFile.get(`/history/${this.userId}`);
        console.log(response.data)
        this.files = response.data; // Assign response data to `files`
        this.loading = false; // Stop loading
        console.log(this.loading)
        console.log(this.files)
      } catch (err) {
        this.error = err.message || "Failed to fetch history!";
        this.loading = false;
      }
    },

    // Handle actions from FileItem
    async handleFileAction(action) {
      try {
        const response = await clientFile.get(
          `/download/${action.fileId}?fileName=${action.fileName}`,
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
        let filename = action.fileName;

        if (contentDisposition) {
          const filenameMatch = contentDisposition.match(/filename="(.+)"/);
          if (filenameMatch?.length === 2) filename = filenameMatch[1];
        }

        this.downloadFile(blob, filename)

      } catch (err) {
        this.error = err.message || "Failed to download!";
      }
    },
    downloadFile: (blob, filename) => {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      window.URL.revokeObjectURL(url);
      link.remove();
    }
  },
  mounted() {
    this.fetchHistory();
  },
};
</script>

<style scoped>
/* App Layout */
.app-container {
  display: flex;
  height: 100vh;
  font-family: Arial, sans-serif;
  color: #333;
}

/* Sidebar */
.sidebar {
  width: 250px;
  background-color: #f4f4f4;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.sidebar h1 {
  font-size: 20px;
  margin-bottom: 20px;
}

.sidebar nav ul {
  list-style: none;
  padding: 0;
}

.sidebar nav ul li {
  margin-bottom: 10px;
}

.sidebar nav ul li a {
  text-decoration: none;
  color: #333;
  padding: 10px 15px;
  display: block;
  border-radius: 8px;
}

.sidebar nav ul li a.active {
  background-color: #ddd;
}

.sidebar-footer {
  text-align: center;
  margin-top: auto;
}

/* Main Content */
.main-content {
  flex-grow: 1;
  background-color: #fff;
  padding: 20px;
  overflow-y: auto;
}

.loading {
  text-align: center;
  font-size: 18px;
  color: #555;
}

.error {
  text-align: center;
  font-size: 18px;
  color: red;
}

.file-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}
</style>