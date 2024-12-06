<template>
  <div class="app-container">
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
          </div>
        </div>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="main-content">
      <div v-if="loading" class="loading-state">
        <div class="loading-spinner"></div>
        <p>Loading history...</p>
      </div>

      <div v-else-if="error" class="error-state">
        <span class="error-icon">⚠️</span>
        <p>{{ error }}</p>
      </div>

      <div v-else class="file-grid">
        <FileItem
          v-for="(fileGroup, index) in files"
          :key="index"
          :file="fileGroup"
          @file-action="handleFileAction"
          @delete-file="handleDeleteFile"
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
    async fetchHistory() {
      try {
        const response = await clientFile.get(`/history/${this.userId}`);
        this.files = response.data;
        this.loading = false;
      } catch (err) {
        this.error = err.message || "Failed to fetch history!";
        this.loading = false;
      }
    },

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
        );
        
        const blob = new Blob([response.data], {
          type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        });

        const contentDisposition = response.headers["content-disposition"];
        let filename = action.fileName;

        if (contentDisposition) {
          const filenameMatch = contentDisposition.match(/filename="(.+)"/);
          if (filenameMatch?.length === 2) filename = filenameMatch[1];
        }

        this.downloadFile(blob, filename);
      } catch (err) {
        this.error = err.message || "Failed to download!";
      }
    },

    async handleDeleteFile({ fileId, fileName }) {
      try {
        const isConfirmed = window.confirm(`Are you sure you want to delete ${fileName} id ${fileId}?`);
        
        if (!isConfirmed) return;

        await clientFile.delete(`/delete/${fileId}`);

        this.files = this.files.filter(fileGroup => 
          fileGroup.originalFile.fileId !== fileId
        );

      } catch (err) {
        this.error = err.message || "Failed to delete file!";
      }
    },

    downloadFile(blob, filename) {
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement('a');
      link.href = url;
      link.download = filename;
      document.body.appendChild(link);
      link.click();
      window.URL.revokeObjectURL(url);
      link.remove();
    },
  },
  mounted() {
    this.fetchHistory();
  },
};
</script>

<style scoped>
.app-container {
  display: flex;
  height: 100vh;
  background-color: #f5f7fa;
}

/* Sidebar Styles */
.sidebar {
  width: 250px;
  background-color: #ffffff;
  border-right: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 1.5rem;
}

.sidebar-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1a202c;
  margin-bottom: 2rem;
}

.sidebar-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.nav-link {
  display: flex;
  align-items: center;
  padding: 0.75rem 1rem;
  color: #4a5568;
  text-decoration: none;
  border-radius: 0.5rem;
  margin-bottom: 0.5rem;
  transition: all 0.2s;
}

.nav-link:hover {
  background-color: #edf2f7;
  color: #2d3748;
}

.nav-icon {
  margin-right: 0.75rem;
  font-size: 1.25rem;
}

.user-profile {
  display: flex;
  align-items: center;
  padding: 1rem;
  border-top: 1px solid #e2e8f0;
  margin-top: auto;
}

.avatar {
  width: 2.5rem;
  height: 2.5rem;
  background-color: #e2e8f0;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 0.75rem;
}

.username {
  font-size: 0.875rem;
  color: #4a5568;
}

/* Main Content Styles */
.main-content {
  flex: 1;
  padding: 2rem;
  overflow-y: auto;
}

.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.loading-spinner {
  border: 3px solid #f3f3f3;
  border-top: 3px solid #3498db;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

.error-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #e53e3e;
}

.error-icon {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.file-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
  padding: 1rem;
}

/* Responsive Design */
@media (max-width: 768px) {
  .sidebar {
    width: 80px;
  }

  .nav-text,
  .sidebar-title {
    display: none;
  }

  .nav-link {
    justify-content: center;
    padding: 0.75rem;
  }

  .nav-icon {
    margin-right: 0;
    font-size: 1.5rem;
  }

  .user-profile {
    justify-content: center;
  }

  .username {
    display: none;
  }
}
</style>