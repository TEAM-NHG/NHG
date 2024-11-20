<!-- App.vue -->
<template>
  <div class="app-container">
    <TheNavbar/>
    <router-view v-slot="{ Component }">
      <component :is="Component" />
    </router-view>
  </div>

</template>

<script setup>
import { RouterView } from 'vue-router'
import TheNavbar from '@/components/common/TheNavbar.vue';
</script>

<style>
/* Global styles if needed */
body {
  /* font-family: 'Jua', sans-serif; */
  /* font-size: 120%; */
  font-weight: bold;
}
</style>
