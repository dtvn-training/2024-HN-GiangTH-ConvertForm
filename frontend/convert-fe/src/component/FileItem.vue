<template>
    <div class="file-card">
      <h2>{{ file.originalFile.fileName }}</h2>
      <p>Create at: {{ formatDate(file.originalFile.createAt) }}</p>
      <p>
        Status:
        <span
          :class="{
            success: hasSuccess(file.relatedFiles),
            error: hasError(file.relatedFiles),
          }"
        >
          {{ hasSuccess(file.relatedFiles) ? "Success" : "Error" }}
        </span>
      </p>
      <div class="button-group">
        <button class="btn original" @click="handleOriginalClick">
          Original
        </button>
        <button
          v-for="relatedFile in file.relatedFiles"
          :key="relatedFile.fileId"
          class="btn"
          :class="{
            output: relatedFile.type === 'CONVERTED',
            error: relatedFile.type === 'ERROR',
          }"
          @click="handleRelatedClick(relatedFile)"
        >
          <!-- {{ relatedFile.type === 'CONVERTED' ? "Output File" : "Error" }} -->
            {{ relatedFile.fileName }}
        </button>
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
        const options = { year: "numeric", month: "short", day: "numeric" };
        return new Date(dateString).toLocaleDateString(undefined, options);
      },
      hasSuccess(relatedFiles) {
        return relatedFiles.some((file) => file.type === "CONVERTED");
      },
      hasError(relatedFiles) {
        return !this.hasSuccess(relatedFiles);
      },
      handleOriginalClick() {
        // Emit the original file's name and ID to the parent
        this.$emit("file-action", {
          type: "original",
          fileName: this.file.originalFile.fileName,
          fileId: this.file.originalFile.fileId,
        });
      },
      handleRelatedClick(relatedFile) {
        // Emit the related file's name and ID to the parent
        this.$emit("file-action", {
          type: relatedFile.type === "CONVERTED" ? "output" : "error",
          fileName: relatedFile.fileName,
          fileId: relatedFile.fileId,
        });
      },
    },
  };
  </script>
  
  <style scoped>
  /* File Card Styles */
  .file-card {
    background-color: #fbe4e4;
    border-radius: 10px;
    padding: 20px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .file-card h2 {
    font-size: 18px;
    margin-bottom: 10px;
  }
  
  .file-card p {
    margin: 5px 0;
    font-size: 14px;
  }
  
  .file-card p span.success {
    color: green;
    font-weight: bold;
  }
  
  .file-card p span.error {
    color: red;
    font-weight: bold;
  }
  
  /* Buttons */
  .button-group {
    margin-top: 10px;
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
  }
  
  .btn {
    padding: 5px 10px;
    font-size: 14px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
  
  .btn.original {
    background-color: #ccc;
    color: #333;
  }
  
  .btn.output {
    background-color: #4caf50;
    color: white;
  }
  
  .btn.error {
    background-color: #f44336;
    color: white;
  }
  </style>