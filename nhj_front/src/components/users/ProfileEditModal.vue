<template>
  <div class="modal fade" id="editProfileModal" tabindex="-1" aria-labelledby="editProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="editProfileModalLabel">프로필 수정</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="handleSubmit">
            <div class="mb-3">
              <label for="nickname" class="form-label">닉네임</label>
              <input
                type="text"
                class="form-control"
                id="nickname"
                v-model="formData.nickname"
                :class="{ 'is-invalid': errors.nickname }"
              >
              <div class="invalid-feedback">{{ errors.nickname }}</div>
            </div>

            <div class="mb-3">
              <label for="phone" class="form-label">핸드폰 번호</label>
              <input
                type="tel"
                class="form-control"
                id="phone"
                v-model="formData.phone"
                :class="{ 'is-invalid': errors.phone }"
              >
              <div class="invalid-feedback">{{ errors.phone }}</div>
            </div>

            <div class="mb-3">
              <label for="email" class="form-label">이메일</label>
              <input
                type="email"
                class="form-control"
                id="email"
                v-model="formData.email"
                :class="{ 'is-invalid': errors.email }"
              >
              <div class="invalid-feedback">{{ errors.email }}</div>
            </div>

            <div class="mb-3">
              <label for="profileImage" class="form-label">프로필 이미지</label>
              <input
                type="file"
                class="form-control"
                id="profileImage"
                @change="handleImageChange"
                accept="image/*"
              >
            </div>

            <div class="modal-footer px-0 pb-0">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
              <button type="submit" class="btn btn-primary" :disabled="isSubmitting">
                <span v-if="isSubmitting" class="spinner-border spinner-border-sm me-1"></span>
                저장
              </button>
            </div>
          </form>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore();
const emit = defineEmits(['update']);

const formData = ref({
  id: authStore.user.id,
  nickname: '',
  phone: '',
  email: '',
  // img: null
});

const errors = ref({
  nickname: '',
  phone: '',
  email: ''
});

const isSubmitting = ref(false);

// 초기 데이터 설정
onMounted(() => {
  formData.value = {
    id: authStore.user.id,
    nickname: authStore.user.nickname,
    phone: authStore.user.phone,
    email: authStore.user.email,
    // img: null
  };
});

const userFormData = new FormData();
const handleImageChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    userFormData.append("images", file);
  }
};

const handleSubmit = async () => {
  if (!validateForm()) return;
  isSubmitting.value = true;
  try {
    // 여기에 실제 API 호출 로직 추가
    userFormData.append("member", JSON.stringify(formData.value));
    await authStore.updateProfile(userFormData);
    emit('update');
    alert('수정이 완료되었습니다')
  } catch (error) {
    console.error('프로필 업데이트 실패:', error);
  } finally {
    isSubmitting.value = false;
  }
};

const validateForm = () => {
  let isValid = true;
  errors.value = {
    nickname: '',
    phone: '',
    email: ''
  };

  if (!formData.value.nickname) {
    errors.value.nickname = '닉네임을 입력해주세요.';
    isValid = false;
  }

  if (!formData.value.phone) {
    errors.value.phone = '핸드폰 번호를 입력해주세요.';
    isValid = false;
  } else if (!/^01[0-9]{8,9}$/.test(formData.value.phone)) {
    errors.value.phone = '올바른 핸드폰 번호 형식이 아닙니다. (예: 010-1234-5678)';
    isValid = false;
  }

  if (!formData.value.email) {
    errors.value.email = '이메일을 입력해주세요.';
    isValid = false;
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.value.email)) {
    errors.value.email = '올바른 이메일 형식이 아닙니다.';
    isValid = false;
  }

  return isValid;
};
</script>
