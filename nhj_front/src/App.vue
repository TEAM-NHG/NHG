<!-- App.vue -->
<template>
  <div class="app-container">
    <TheNavbar />
    <router-view></router-view>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import TheNavbar from '@/components/common/TheNavbar.vue';
</script>

<style>
body {
  font-family: 'NanumSquare';
  font-weight: bold;
  font-size: 105%;
  background-color: pink;
}

.app-container {
  position: relative;
}

.innerBox {
  margin-top: 10%;
  margin-left: 15%;
  margin-right: 15%
}
</style>
