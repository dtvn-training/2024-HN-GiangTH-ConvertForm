<template>
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
          :disabled="!selectedFile || loading"
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
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import axios from 'axios';
  
  const fileInput = ref(null);
  const selectedFile = ref(null);
  const loading = ref(false);
  const error = ref(null);
  
  const handleFileSelect = (event) => {
    selectedFile.value = event.target.files[0];
    error.value = null;
  };
  
  const downloadFile = (blob, filename) => {
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    const content = document.createTextNode(filename)

    link.appendChild(content)
    
    link.href = url;
    link.download = filename;
    document.body.appendChild(link);
    // link.click();
    // window.URL.revokeObjectURL(url);
    // link.remove();
  };
  
  const uploadFile = async () => {
    if (!selectedFile.value) {
      error.value = 'Please select a file first';
      return;
    }
  
    loading.value = true;
    error.value = null;
  
    const formData = new FormData();
    formData.append('file', selectedFile.value);
  
    try {
      const response = await axios.post('http://localhost:8080/api/upload', 
        formData,
        {
          responseType: 'blob',
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }
      );
  
      const blob = new Blob([response.data], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
      });
  
      const contentDisposition = response.headers['content-disposition'];
      let filename = 'convert_main_output.xlsx';
      if (contentDisposition) {
        const filenameMatch = contentDisposition.match(/filename="(.+)"/);
        if (filenameMatch?.length === 2) filename = filenameMatch[1];
      }
  
      downloadFile(blob, filename);
    } catch (err) {
      if (err.response) {
        const reader = new FileReader();
        reader.onload = () => {
          try {
            const errorData = JSON.parse(reader.result);
            error.value = errorData.message || 'Upload failed';
          } catch {
            error.value = 'Upload failed';
          }
        };
        reader.readAsText(err.response.data);
      } else {
        error.value = 'Upload failed: ' + err.message;
      }
    } finally {
      loading.value = false;
      if (fileInput.value) fileInput.value.value = '';
      selectedFile.value = null;
    }
  };
  </script>
  
  <style scoped>
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