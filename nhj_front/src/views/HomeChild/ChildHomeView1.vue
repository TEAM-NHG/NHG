<template>
  <div class="home-container">
      <div class="text-position w-100 text-center">
        <div>
          <div class="mb-4">
            <div class="Main-text mb-3 fade-text">나만의 속도, 나만의 여정</div>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

onMounted(() => {
const textElement = document.querySelector('.fade-text');
// 약간의 지연 후 페이드 인 효과 적용
setTimeout(() => {
  textElement.classList.add('fade-in');
}, 100);
});
</script>

<style scoped>
.home-container {
position: relative;
height: 100vh;
background-color: black;
color: white;
}

.main-image {
position: absolute;
width: 100%;
}

.Main-text {
font-size: 250%;
}

.text-position {
position: absolute;
top: 45vh;
}

/* 페이드 효과 관련 스타일 */
.fade-text {
opacity: 0;
transform: translateY(20px);
transition: all 1.2s ease-out;
}

.fade-in {
opacity: 1;
transform: translateY(0);
}
</style>