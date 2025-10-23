<script setup>
import { ref, onMounted } from "vue";

const message = ref("Loading...");

onMounted(async () => {
  try {
    const response = await fetch("/api/message");
    const data = await response.json();
    message.value = data.message;
  } catch (error) {
    console.error("Failed to fetch from API:", error);
    message.value = "Failed to load message from backend.";
  }
});
</script>

<template>
  <h1>{{ message }}</h1>
</template>
