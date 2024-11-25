<template>
  <nav v-if="route.name !== 'home'"
       :class="route.name === 'planner' ? 'transparent navbar navbar-expand-lg' : 'navbar navbar-expand-lg'">
    <div class="container-fluid d-flex justify-content-between align-items-center">
      <router-link :to="{ name: 'home' }" class="ms-4">
        <div style="font-size: 150%;">나혼자간다</div>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
        <ul class="d-flex align-items-center">
          <li class="nav-item">
            <router-link :to="{ name: 'curation' }" class="nav-link px-4">여행 큐레이션</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'news' }" class="nav-link px-4">여행 소식</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'board' }" class="nav-link px-4">같이 떠나요</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'planner' }" class="nav-link px-4"
            :class="{ 'planner-active': route.name === 'planner' }" >AI 여행 플래너</router-link>
          </li>
        </ul>
      </div>
      <!-- 로그인 상태에 따라 다른 내용 표시 -->
        <template v-if="authStore.isLoggedIn">
          <div class="nav-item">
            <router-link :to="{ name: 'profile' }" class="nav-link mx-3">
              <div class="btn position-relative">
                <img class="user-icon" :src="authStore.user.img ? authStore.user.img : defaultUserIcon"></img>
                <span v-show="authStore.user.notification > 0"
                      class="nofication p-2 rounded-circle">
                </span>
              </div>
            </router-link>
          </div>
          <div @click="logout">로그아웃</div>
        </template>
        <template v-else>
          <div class="nav-item">
            <router-link :to="{ name: 'signup' }" class="nav-link px-3">회원가입</router-link>
          </div>
          <div class="nav-item">
            <router-link :to="{ name: 'login' }" class="nav-link px-3">로그인</router-link>
          </div>
        </template>
    </div>
  </nav>
</template>

<script setup>
import { onMounted } from 'vue';
import defaultUserIcon from '@/assets/userIcon.png';
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

import { useRouter, useRoute } from "vue-router";
const router = useRouter();
const route = useRoute();

onMounted(() => {
  //mount 될 때마다 세션에서 확인
  authStore.checkLoginStatus()
});

const logout = () => {
  authStore.logout()
  router.replace({ name: 'home' })
}

</script>

<style scoped>
* {
  font-family: 'Gyeonggi_Title_Medium';
  font-size: 102%;
}

.navbar-nav .nav-link {
  font-weight: 300;
}

.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 500;
  height: 8vh;
  background-color: #191A1C;
  color: #faf7f0;
  font-weight: 300;
}

.transparent{
  background-color: transparent;
  color: #191A1C;
  font-weight: bolder;
}

.nav-item:hover {
  transform: scale(1.1);
  transition: all 0.3s ease;
}

.user-icon {
  width: 40px;
  height: 40px;
  object-fit: cover;
  border-radius: 50%;
}

.router-link-active {
  border-bottom: 2px solid white;
}

.router-link-active.planner-active {
  border-bottom: 2px solid black;
}

.nofication {
  position: absolute;
  top: 0;
  right: 5px;
  background-color: red;
}
</style>
