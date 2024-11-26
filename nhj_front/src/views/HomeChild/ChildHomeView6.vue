<template>
  <div class="home-container" style="height: 100vh;">
    <!-- 배경 이미지 -->
    <transition name="fade">
      <img
        :src="currentImage"
        :key="currentImage"
        alt="Main Background"
        class="w-50 main-image fade-element"
        style="height: 90vh; object-fit: cover;">
    </transition>
    <div class="position-absolute top-0 start-0 w-100 black">''</div>

    <!-- 텍스트 오버레이 -->
    <div class="text-position w-50 text-center">
      <div v-if="!authStore.isLoggedIn">
        <div class="Main-text mb-3">지금, 당신만의 특별한 여정을 만들어보세요</div>
        <div class="btn" @click="routeLogin">로그인하고 떠나요
          <font-awesome-icon :icon="['fas', 'suitcase']" bounce style="color: #000000;" />
        </div>

      </div>
      <div v-else class="logined fade-element">
        <div class="mb-4">
          <div>지금, 당신만의 특별한 여정을 만들어보세요</div>
        </div>
        <div class="d-flex justify-content-center router-link">
          <div class="router-hover">
            <router-link :to="{ name: 'curation' }" class="nav-link px-4">여행 큐레이션</router-link>
          </div>
          <div class="router-hover">
            <router-link :to="{ name: 'news' }" class="nav-link px-4">여행 소식</router-link>
          </div>
          <div class="router-hover">
            <router-link :to="{ name: 'board' }" class="nav-link px-4">같이 떠나요</router-link>
          </div>
          <div class="router-hover">
            <router-link :to="{ name: 'planner' }" class="nav-link px-4">AI 여행 플래너</router-link>
          </div>
          <div class="router-hover">
            <router-link :to="{ name: 'profile' }" class="nav-link px-4">My Page</router-link>
          </div>
        </div>
      </div>
    </div>
    <div class="position-absolute bottom-0 start-0 w-100 black">''</div>
    <div class="btn easterEgg" @click="easterEgg"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { useRouter } from "vue-router";

const authStore = useAuthStore();
const router = useRouter();

// 이미지 배열 및 현재 이미지 상태
const images = [
  "src/assets/Main/main1.jpg",
  "src/assets/Main/main2.jpg",
  "src/assets/Main/main5.jpg",
  "src/assets/Main/main6.jpg",
  "src/assets/Main/main8.jpg",
  "src/assets/Main/main10.jpg",
  "src/assets/Main/main12.jpg",
  "src/assets/Main/main14.jpg",
  "src/assets/Main/main15.jpg",
];
const currentImage = ref(images[0]); // 현재 보여줄 이미지
let imageIndex = 0;

// 로그인 라우터 이동
const routeLogin = () => {
  router.push({ name: 'login' });
};

const easterEgg = () => {
  router.push({name:'admin'})
}

// 이미지 변경 함수
const changeImage = () => {
  imageIndex = (imageIndex + 1) % images.length; // 다음 이미지로 순환
  currentImage.value = images[imageIndex];
};

// 이미지 변경 주기 설정
onMounted(() => {
  setInterval(changeImage, 5000); // 5초마다 이미지 변경
});
</script>

<style scoped>
.home-container {
  position: relative;
  height: calc(100vh - 70px);
  background-color: rgb(255, 255, 255);
  color: rgb(0, 0, 0);
}

.main-image {
  position: absolute;
  top: 5vh;
  width: 100%;
}

/* 페이드 효과 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1.5s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.Main-text {
  font-size: 225%;
}

.logined{
  font-size: 160%;
  border-bottom: 2.8px solid black;
  border-top: 2.8px solid black;
  padding-top: 5vh;
  padding-bottom: 5vh;
}

.black {
  background-color: black;
  color: black;
  height: 5vh;
}

.router-link{
  font-size: 60%;
}

.router-hover:hover{
  transform: scale(1.1);
  transition: all 0.3s ease;
}

.text-position {
  position: absolute;
  top: 41vh;
  bottom: auto;
  right: auto;
  left: 50%;
}

.easterEgg{
  position: absolute;
  bottom: 0;
  right: 0;
  height: 5vh;
  width: 1vh;
}

.btn{
  font-size: 140%;
  font-weight: bold;
}

.btn:hover{
  transform: scale(1.15);
  transition: all 0.3s ease;
}



</style>
