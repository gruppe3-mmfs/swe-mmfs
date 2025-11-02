<script setup>
import { ref } from "vue";

const message = ref("Ping fra frontend");

const loopPingPong = async () => {
  while (true) {
    // Show "Ping"
    message.value = "Ping fra frontend";
    await new Promise((resolve) => setTimeout(resolve, 2000));

    // Fetch "Pong" from backend
    try {
      const response = await fetch("/api/ping");
      const text = await response.text();
      message.value = text;
    } catch (error) {
      console.error("Failed to fetch from API:", error);
      message.value = "Error";
    }

    await new Promise((resolve) => setTimeout(resolve, 2000));
  }
};

loopPingPong();
</script>

<template>
  <transition name="bounce" mode="out-in">
    <h1 :key="message">{{ message }}</h1>
  </transition>
</template>

<style scoped>
.bounce-enter-active,
.bounce-leave-active {
  transition: all 0.5s ease;
}
.bounce-enter-from {
  transform: scale(0.8);
  opacity: 0;
}
.bounce-enter-to {
  transform: scale(1);
  opacity: 1;
}
.bounce-leave-from {
  transform: scale(1);
  opacity: 1;
}
.bounce-leave-to {
  transform: scale(1.2);
  opacity: 0;
}
</style>
