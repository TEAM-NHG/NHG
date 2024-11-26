<template>
  <div class="home-container">
      <div class="text-position w-50 text-center fade-element">
        <div>
          <div class="mb-4">
            <div class="Main-text mb-3">AI에게 여행 계획 수립과 평가를 받아볼 수 있어요</div>
          </div>
        </div>
      </div>
      <video src="@/assets/Main/planner.mp4" 
             autoplay 
             muted
             alt="Main Background"
             class="w-50 main-image fade-element"
      >
      </video>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';

onMounted(() => {
const observer = new IntersectionObserver((entries) => {
  entries.forEach(entry => {
    if (entry.isIntersecting) {
      entry.target.classList.add('fade-in');
    }
  });
}, {
  threshold: 0.3
});

// 페이드 효과를 적용할 요소들 관찰
document.querySelectorAll('.fade-element').forEach(element => {
  observer.observe(element);
});
});
</script>

<style scoped>
.home-container {
position: relative;
height: 100vh;
background-color: rgb(0, 0, 0);
color: white;
}

.main-image {
position: absolute;
width: 100%;
height: 100%;
object-fit: cover;
}

.Main-text {
font-size: 200%;
}

.text-position {
position: absolute;
top: 48vh;
bottom: auto;
right: auto;
left: 50%;
}

/* 페이드 효과 관련 스타일 */
.fade-element {
opacity: 0;
transform: translateY(20px);
transition: all 1s ease-out;
}

.fade-in {
opacity: 1;
transform: translateY(0);
}
</style>