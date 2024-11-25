<template>
  <div class="signup-container">
    <div class="signup-box">
      <h2 class="text-center mb-4">회원가입</h2>

      <form @submit.prevent="handleSignup" class="needs-validation">
        <!-- 아이디 입력 -->
        <div class="mb-3">
          <label class="form-label">아이디 *</label>
          <div class="input-group">
            <input
              type="text"
              class="form-control"
              v-model="signupForm.id"
              :class="{ 'is-invalid': !isIdValid && isDirty.id }"
              @blur="validateId"
              placeholder="영문, 숫자 5-20자"
              required>
            <button
              type="button"
              class="btn btn-outline-secondary"
              @click="checkIdDuplicate">
              중복확인
            </button>
          </div>
          <div class="invalid-feedback" v-if="!isIdValid && isDirty.id">
            아이디는 영문, 숫자를 포함한 5-20자여야 합니다.
          </div>
        </div>

        <!-- 비밀번호 입력 -->
        <div class="mb-3">
          <label class="form-label">비밀번호 *</label>
          <div class="input-group">
            <input
              :type="showPassword ? 'text' : 'password'"
              class="form-control"
              v-model="signupForm.password"
              :class="{ 'is-invalid': !isPasswordValid && isDirty.password }"
              @blur="validatePassword"
              placeholder="영문, 숫자, 특수문자 포함 8-16자"
              required>
            <button
              type="button"
              class="btn btn-outline-secondary"
              @click="togglePassword">
              <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
            </button>
          </div>
          <div class="invalid-feedback" v-if="!isPasswordValid && isDirty.password">
            비밀번호는 영문, 숫자, 특수문자를 포함한 8-16자여야 합니다.
          </div>
        </div>

        <!-- 비밀번호 확인 -->
        <div class="mb-3">
          <label class="form-label">비밀번호 확인 *</label>
          <input
            type="password"
            class="form-control"
            v-model="passwordConfirm"
            :class="{ 'is-invalid': !isPasswordMatch && isDirty.passwordConfirm }"
            @blur="validatePasswordMatch"
            placeholder="비밀번호를 다시 입력해주세요"
            required>
          <div class="invalid-feedback" v-if="!isPasswordMatch && isDirty.passwordConfirm">
            비밀번호가 일치하지 않습니다.
          </div>
        </div>

        <!-- 닉네임 입력 -->
        <div class="mb-3">
          <label class="form-label">닉네임 *</label>
          <input
            type="text"
            class="form-control"
            v-model="signupForm.nickname"
            :class="{ 'is-invalid': !isNicknameValid && isDirty.nickname }"
            @blur="validateNickname"
            placeholder="2-10자 이내"
            required>
          <div class="invalid-feedback" v-if="!isNicknameValid && isDirty.nickname">
            닉네임은 2-10자 이내여야 합니다.
          </div>
        </div>

        <!-- 이메일 입력 -->
        <div class="mb-3">
          <label class="form-label">이메일 *</label>
          <input
            type="email"
            class="form-control"
            v-model="signupForm.email"
            :class="{ 'is-invalid': !isEmailValid && isDirty.email }"
            @blur="validateEmail"
            placeholder="example@email.com"
            required>
          <div class="invalid-feedback" v-if="!isEmailValid && isDirty.email">
            올바른 이메일 형식이 아닙니다.
          </div>
        </div>

        <!-- 전화번호 입력 -->
        <div class="mb-3">
          <label class="form-label">전화번호 *</label>
          <input
            type="tel"
            class="form-control"
            v-model="signupForm.phone"
            :class="{ 'is-invalid': !isPhoneValid && isDirty.phone }"
            @blur="validatePhone"
            placeholder="01012345678"
            required>
          <div class="invalid-feedback" v-if="!isPhoneValid && isDirty.phone">
            올바른 전화번호 형식이 아닙니다.
          </div>
        </div>

        <!-- 프로필 이미지 업로드 -->
        <div class="mb-4">
          <label class="form-label">프로필 이미지</label>
          <input
            type="file"
            class="form-control"
            @change="handleImageUpload"
            accept="image/*">
        </div>

        <!-- 회원가입 버튼 -->
        <button
          type="submit"
          class="btn btn-primary w-100"
          :disabled="!isFormValid">
          회원가입
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { localAxios } from "@/util/http-commons";
const local = localAxios();

const router = useRouter();

const signupForm = ref({
  id: '',
  password: '',
  nickname: '',
  email: '',
  phone: '',
  img: ''
});

const passwordConfirm = ref('');
const showPassword = ref(false);

// 유효성 검사 상태
const isDirty = ref({
  id: false,
  password: false,
  passwordConfirm: false,
  nickname: false,
  email: false,
  phone: false
});

// 유효성 검사 함수들
const validateId = () => {
  isDirty.value.id = true;
  const idRegex = /^[a-zA-Z0-9]{5,20}$/;
  isIdValid.value = idRegex.test(signupForm.value.id);
};

const validatePassword = () => {
  isDirty.value.password = true;
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
  isPasswordValid.value = passwordRegex.test(signupForm.value.password);
};

const validatePasswordMatch = () => {
  isDirty.value.passwordConfirm = true;
  isPasswordMatch.value = signupForm.value.password === passwordConfirm.value;
};

const validateNickname = () => {
  isDirty.value.nickname = true;
  isNicknameValid.value = signupForm.value.nickname.length >= 2 &&
                          signupForm.value.nickname.length <= 10;
};

const validateEmail = () => {
  isDirty.value.email = true;
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  isEmailValid.value = emailRegex.test(signupForm.value.email);
};

const validatePhone = () => {
  isDirty.value.phone = true;
  const phoneRegex = /^01[0-9]{8,9}$/;
  isPhoneValid.value = phoneRegex.test(signupForm.value.phone);
};

// 유효성 검사 결과
const isIdValid = ref(true);
const isPasswordValid = ref(true);
const isPasswordMatch = ref(true);
const isNicknameValid = ref(true);
const isEmailValid = ref(true);
const isPhoneValid = ref(true);

// 폼 전체 유효성 검사
const isFormValid = computed(() => {
  return isIdValid.value &&
         isPasswordValid.value &&
         isPasswordMatch.value &&
         isNicknameValid.value &&
         isEmailValid.value &&
         isPhoneValid.value;
});

// 비밀번호 표시/숨김 토글
const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

// 아이디 중복 확인
const checkIdDuplicate = async () => {
  if (!isIdValid.value) return;

  try {
    const response = await local.post(`/member/join/id-check`, {"id" : signupForm.value.id})

    if (response.data) {
      alert('이미 사용중인 아이디입니다.');
      isIdValid.value = false;
    } else {
      alert('사용 가능한 아이디입니다.');
    }
  } catch (error) {
    console.error('아이디 중복 확인 실패:', error);
  }
};

// 이미지 업로드 처리
const signupFormData = new FormData();
const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    signupFormData.append("images", file);
  }
};

// 회원가입 처리
const handleSignup = async () => {
  if (!isFormValid.value) return;

  try {
    signupFormData.append("member", JSON.stringify(signupForm.value));
    const response = await local.post('/member/join', signupFormData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
    if (response.status == 201) {
      alert('회원가입이 완료되었습니다.');
      router.push({ name: 'login' });
    }
  } catch (error) {
    console.error('회원가입 실패:', error);
  }
};
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f8f9fa;
  padding: 2rem 0;
}

.signup-box {
  width: 100%;
  max-width: 500px;
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

.btn-primary:disabled {
  background-color: #e9ecef;
  border-color: #e9ecef;
}
</style>
