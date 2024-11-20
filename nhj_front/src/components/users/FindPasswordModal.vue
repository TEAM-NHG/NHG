<template>
  <div class="modal-backdrop">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h5 class="modal-title mb-3">비밀번호 찾기</h5>
        <button type="button" class="btn-close" @click="$emit('close')"
          style="position: absolute; top: 15px; right: 15px;"></button>
        <!-- <button type="button" class="btn-close" @click="$emit('close')"></button> -->
      </div>

      <div class="modal-body">
        <form @submit.prevent="handleSubmit" v-if="!correct">
          <div class="mb-3">
            <div class="mb-3">
              <label class="form-label">아이디</label>
              <input type="id" class="form-control" v-model="data.id" placeholder="가입시 등록한 아이디를 입력하세요" required>
            </div>

            <label class="form-label">이메일</label>
            <input type="email" class="form-control" v-model="data.email" placeholder="가입시 등록한 이메일을 입력하세요" required>
          </div>

          <div v-if="errorStatus" class="alert alert-danger">
            입력하신 정보를 찾을 수 없습니다.
          </div>

          <button type="submit" class="btn btn-primary w-100">
            비밀번호 찾기
          </button>
        </form>

        <form @submit.prevent="resetPwd" v-if="correct">
          <div class="mb-3">
            <!-- 비밀번호 입력 -->
            <div class="mb-3">
              <label class="form-label">비밀번호</label>
              <div class="input-group">
                <input :type="showPassword ? 'text' : 'password'" class="form-control" v-model="data.password"
                  :class="{ 'is-invalid': !isPasswordValid && isDirty.password }" @blur="validatePassword"
                  placeholder="영문, 숫자, 특수문자 포함 8-16자" required>
                  <button type="button" class="btn btn-outline-secondary" @click="togglePassword">
                  <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                </button>
              </div>
              <div class="invalid-feedback" v-if="!isPasswordValid && isDirty.password">
                비밀번호는 영문, 숫자, 특수문자를 포함한 8-16자여야 합니다.
              </div>
            </div>

            <!-- 비밀번호 확인 -->
            <div class="mb-3">
              <label class="form-label">비밀번호 확인</label>
              <input type="password" class="form-control" v-model="passwordConfirm"
                :class="{ 'is-invalid': !isPasswordMatch && isDirty.passwordConfirm }" @blur="validatePasswordMatch"
                placeholder="비밀번호를 다시 입력해주세요" required>
              <div class="invalid-feedback" v-if="!isPasswordMatch && isDirty.passwordConfirm">
                비밀번호가 일치하지 않습니다.
              </div>
            </div>
          </div>

          <button class="btn btn-primary w-100">
            비밀번호 재설정
          </button>
        </form>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

//axios
import { localAxios } from "@/util/http-commons";
const local = localAxios();

//emit
const emit = defineEmits(['close'])

const data = ref({
  email: '',
  id: '',
  password: '',
})
const errorStatus = ref(false);
const correct = ref(false)

const handleSubmit = async () => {
  try {
    // API 호출
    const response = await local.post('/member/find-password', data.value)

    if (response.status === 200) {
      correct.value = true
    } else {
      errorStatus.value = true;
    }
  } catch (error) {
    console.error('정보 불일치?:', error);
  }
};

const resetPwd = async () => {
  try {
    //API호출
    const response = await local.put('/member/password', data.value)
    if(response.status === 200) {
      alert('비밀번호가 재설정되었습니다.')
      emit('close')
    }
  }catch(error){
    console.log('에러 발생', error)
  }
}

const passwordConfirm = ref('');
const isPasswordMatch = ref(true);
const showPassword = ref(false);

// 비밀번호 표시/숨김 토글
const togglePassword = () => {
  showPassword.value = !showPassword.value;
};

//비밀번호 유효성 검사
const isDirty = ref({
  password: false,
  passwordConfirm: false,
});
const isPasswordValid = ref(true);

const validatePassword = () => {
  isDirty.value.password = true;
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,16}$/;
  isPasswordValid.value = passwordRegex.test(data.value.password);
};

const validatePasswordMatch = () => {
  isDirty.value.passwordConfirm = true;
  isPasswordMatch.value = data.value.password === passwordConfirm.value;
};

</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 400px;
  padding: 1rem;
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
</style>
