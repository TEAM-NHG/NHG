<template>
  <nav class="navbar navbar-expand-lg" style="background-color: #CBDCEB;">
    <div class="container-fluid">
      <router-link :to="{name: 'home'}" class="navbar-brand">
        <!-- <img src="@/assets/나혼자간다.png" alt="Logo" style="height: 50px;" class="d-inline-block align-top"> -->
         <div>나혼자간다</div>
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
        <ul class="navbar-nav align-items-center">
          <li class="nav-item">
            <router-link :to="{name: 'curation'}" class="nav-link px-4">여행 큐레이션</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{name: 'news'}" class="nav-link px-4">여행 소식</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{name: 'board'}" class="nav-link px-4">같이 떠나요</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{name: 'planner'}" class="nav-link px-4">AI 여행 플래너</router-link>
          </li>
          <!-- 로그인 상태에 따라 다른 내용 표시 -->
          <template v-if="authStore.isLoggedIn">
            <li class="nav-item">
              <router-link :to="{name: 'profile'}" class="nav-link px-4">
                <img class="user-icon" v-if="authStore.user.img" :src=authStore.user.img alt="">
                <img class="user-icon" v-else src="@/assets/userIcon.png" alt="">
              </router-link>
            </li>
            <li @click="logout">로그아웃</li>
          </template>
          <template v-else>
            <li class="nav-item">
              <router-link :to="{name: 'signup'}" class="nav-link px-3">회원가입</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{name: 'login'}" class="btn btn-primary">로그인</router-link>
            </li>
          </template>
        </ul>
      </div>
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
  router.replace({name : 'home'})
}

</script>

<style scoped>
* {
  font-family: 'Gyeonggi_Title_Medium';
}

.navbar-nav .nav-link {
  color: #000000;
  font-weight: 500;
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
</style>
