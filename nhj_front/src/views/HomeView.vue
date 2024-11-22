<!-- src/views/HomeView.vue -->
<template>
  <div class="home-container">
    <img src="@/assets/Main/test2.jpg" alt="Main Background" class="w-100 main-image" style="height: 100vh; object-fit: cover;">
    <div class="position-absolute top-50 start-50 translate-middle text-center text-white">
      <h1 v-if="!authStore.isLoggedIn" class="mb-3">나만의 속도, 나만의 여정</h1>
      <template v-else>
        <h1>안녕하세요 {{ authStore.user.nickname }}님</h1>
        <h3>당신만의 특별한 여정을 만들어보세요</h3>
      </template>
    </div>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();
</script>

<style scoped>
.home-container {
  position: relative;
  height: calc(100vh - 70px);
}


h1, h3{
  color: black;
  font-weight: bold;
}
</style>
