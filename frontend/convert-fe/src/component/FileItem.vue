<template>
  <div class="bg-gray-300 rounded-xl shadow-sm border border-gray-100 p-6 hover:shadow-md transition-shadow relative">
    <button 
      @click="handleDelete"
      class="absolute top-4 right-4 p-2 text-gray-500 hover:text-red-600 transition-colors rounded-full hover:bg-red-100"
    >
      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
      </svg>
    </button>
    <div class="flex items-start justify-between">
      <div class="flex-1">
        <!-- File Name and Date -->
        <div class="mb-4">
          <h2 class="text-lg font-semibold text-gray-900 mb-1 truncate">
            {{ file.originalFile.fileName }}
          </h2>
          <p class="text-sm text-gray-500 flex items-center">
            <span class="mr-2">📅</span>
            {{ formatDate(file.originalFile.createAt) }}
          </p>
        </div>

        <!-- Status Badge -->
        <div class="mb-4">
          <span
            class="inline-flex items-center px-3 py-1 rounded-full text-sm font-medium"
            :class="{
              'bg-green-100 text-green-700': hasSuccess(file.relatedFiles),
              'bg-red-100 text-red-700': hasError(file.relatedFiles)
            }"
          >
            {{ hasSuccess(file.relatedFiles) ? 'Success' : 'Error' }}
          </span>
        </div>

        <!-- Action Buttons -->
        <div class="flex flex-wrap gap-3">
          <!-- Original File Button -->
          <button 
            @click="handleOriginalClick"
            class="inline-flex items-center px-4 py-2 rounded-lg text-sm font-medium text-gray-700 bg-gray-100 hover:bg-gray-200 transition-colors"
          >
            <span class="mr-2">📄</span>
            Original
          </button>

          <!-- Related File Buttons -->
          <button
            v-for="relatedFile in file.relatedFiles"
            :key="relatedFile.fileId"
            @click="handleRelatedClick(relatedFile)"
            class="inline-flex items-center px-4 py-2 rounded-lg text-sm font-medium transition-colors"
            :class="{
              'bg-green-100 text-green-700 hover:bg-green-200': relatedFile.type === 'CONVERTED',
              'bg-red-100 text-red-700 hover:bg-red-200': relatedFile.type === 'ERROR'
            }"
          >
            <span class="mr-2">
              {{ relatedFile.type === 'CONVERTED' ? '📊' : '⚠️' }}
            </span>
            {{ relatedFile.type === 'CONVERTED' ? 'Output File' : 'Error' }}
            <span 
              v-if="relatedFile.fileName.toLowerCase().includes('shared')"
              class="ml-1 text-xs opacity-75"
            >
              (shared_lib)
            </span>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "FileItem",
  props: {
    file: {
      type: Object,
      required: true,
    },
  },
  methods: {
    formatDate(dateString) {
      const options = { 
        year: "numeric", 
        month: "short", 
        day: "numeric",
        hour: "2-digit",
        minute: "2-digit"
      };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
    hasSuccess(relatedFiles) {
      return relatedFiles.some((file) => file.type === "CONVERTED");
    },
    hasError(relatedFiles) {
      return !this.hasSuccess(relatedFiles);
    },
    handleOriginalClick() {
      this.$emit("file-action", {
        type: "original",
        fileName: this.file.originalFile.fileName,
        fileId: this.file.originalFile.fileId,
      });
    },
    handleRelatedClick(relatedFile) {
      this.$emit("file-action", {
        type: relatedFile.type === "CONVERTED" ? "output" : "error",
        fileName: relatedFile.fileName,
        fileId: relatedFile.fileId,
      });
    },
    handleDelete() {
      this.$emit('delete-file', {
        fileId: this.file.originalFile.fileId,
        fileName: this.file.originalFile.fileName
      });
    }
  },
};
</script>

<style scoped>
/* Optional: Add animation for hover state */
.file-card {
  transition: all 0.2s ease-in-out;
}

/* Optional: Add animation for button hover */
button {
  transition: all 0.2s ease-in-out;
}

/* Optional: Add animation for status badge */
.status-badge {
  transition: all 0.2s ease-in-out;
}
</style>