<template>
  <nav class="navbar navbar-expand-lg navbar-transparent">
    <div class="container-fluid d-flex justify-content-between align-items-center">
      <router-link :to="{ name: 'home' }" class="navbar-brand">
        <div>나혼자간다</div>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
        <ul class="navbar-nav align-items-center">
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
            <router-link :to="{ name: 'planner' }" class="nav-link px-4">AI 여행 플래너</router-link>
          </li>
        </ul>
      </div>
      <!-- 로그인 상태에 따라 다른 내용 표시 -->
        <template v-if="authStore.isLoggedIn">
          <div class="nav-item">
            <router-link :to="{ name: 'profile' }" class="nav-link px-4">
              <img class="user-icon" v-if="authStore.user.img" :src=authStore.user.img alt="">
              <img class="user-icon" v-else src="@/assets/userIcon.png" alt="">
            </router-link>
          </div>
          <div @click="logout">로그아웃</div>
        </template>
        <template v-else>
          <div class="nav-item">
            <router-link :to="{ name: 'signup' }" class="nav-link px-3">회원가입</router-link>
          </div>
          <div class="nav-item">
            <router-link :to="{ name: 'login' }" class="btn btn-primary">로그인</router-link>
          </div>
        </template>
    </div>
  </nav>
</template>

<script setup>
import { onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();
import { useRouter } from "vue-router";
const router = useRouter();


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
}

.navbar-nav .nav-link {
  color: #000000;
  font-weight: 300;
}

.navbar {
  height: 70px;
}

.user-icon {
  width: 40px;
  height: 40px;
  object-fit: cover;
}

.router-link-active {
  color: #0056b3 !important;
  font-weight: 600;
}

.btn-primary {
  background-color: #CBDCEB;
  border-color: #CBDCEB;
  color: #000000;
  margin-left: 10px;
}

.btn-primary:hover {
  background-color: #b0c4d6;
  border-color: #b0c4d6;
}

.navbar-transparent {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
}
</style>
