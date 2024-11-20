<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="text-center mb-4">로그인</h2>

      <form @submit.prevent="handleLogin">
        <!-- 아이디 입력 -->
        <div class="mb-3">
          <input
            type="text"
            class="form-control"
            v-model="loginForm.id"
            placeholder="아이디"
            required>
        </div>

        <!-- 비밀번호 입력 -->
        <div class="mb-3 position-relative">
          <input
            :type="showPassword ? 'text' : 'password'"
            class="form-control"
            v-model="loginForm.password"
            placeholder="비밀번호"
            required>
          <button
            type="button"
            class="btn btn-link position-absolute end-0 top-50 translate-middle-y "
            @click="togglePassword">
            <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
          </button>
        </div>

        <!-- 로그인 유지 체크박스 -->
        <div class="mb-3 form-check">
          <input
            type="checkbox"
            class="form-check-input"
            id="keepLogin"
            v-model="keepLogin">
          <label class="form-check-label" for="keepLogin">로그인 유지</label>
        </div>

        <!-- 로그인 버튼 -->
        <button type="submit" class="btn btn-primary w-100 mb-3">로그인</button>

        <!-- 소셜 로그인 -->
        <div class="social-login mb-3">
          <button type="button" class="btn btn-success w-100 mb-2" @click="naverLogin">
            <i class="bi bi-n"></i> 네이버로 로그인
          </button>
          <button type="button" class="btn btn-warning w-100" @click="kakaoLogin">
            <i class="bi bi-chat-fill"></i> 카카오로 로그인
          </button>
        </div>

        <!-- 아이디/비밀번호 찾기 -->
        <div class="text-center">
          <button type="button" class="btn btn-link" @click="showUserFindIdModal">아이디 찾기</button>
          <span class="mx-2">|</span>
          <button type="button" class="btn btn-link" @click="showFindPasswordModal">비밀번호 찾기</button>
        </div>
      </form>
    </div>

    <!-- 아이디 찾기 모달 -->
    <UserFindIdModal
      v-if="showIdModal"
      @close="showIdModal = false"
      />

    <!-- 비밀번호 찾기 모달 -->
    <FindPasswordModal
      v-if="showPasswordModal"
      @close="showPasswordModal = false"
      />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import UserFindIdModal from '@/components/users/FindIdModal.vue';
import FindPasswordModal from '@/components/users/FindPasswordModal.vue';
import { localAxios } from "@/util/http-commons";
const local = localAxios();
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

const router = useRouter();
const showPassword = ref(false);
const keepLogin = ref(false);
const showIdModal = ref(false);
const showPasswordModal = ref(false);

const loginForm = ref({
  id: '',
  password: ''
});

// 비밀번호 표시/숨김 토글
const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

// 로그인 처리
const handleLogin = async () => {
  try {
    const response = await local.post(`/member/login`, loginForm.value, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    authStore.login(JSON.stringify(response.data))
    router.replace({ name: 'home' });
  }catch(error){
    alert('아이디와 비밀번호를 다시 확인해주세요')
    console.error('Failed to login:', error)
  }
};

// 소셜 로그인 처리
const naverLogin = () => {
  // 네이버 로그인 구현
};

const kakaoLogin = () => {
  // 카카오 로그인 구현
};

// 아이디/비밀번호 찾기 모달 표시
const showUserFindIdModal = () => {
  showIdModal.value = true;
};

const showFindPasswordModal = () => {
  showPasswordModal.value = true;
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f8f9fa;
}

.login-box {
  width: 100%;
  max-width: 400px;
  padding: 2rem;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.btn-primary {
  background-color: #CBDCEB;
  border-color: #CBDCEB;
  color: #000000;
}

.btn-primary:hover {
  background-color: #b0c4d6;
  border-color: #b0c4d6;
}

.btn-link {
  color: #6c757d;
  text-decoration: none;
}

.btn-link:hover {
  color: #000000;
}
</style>
