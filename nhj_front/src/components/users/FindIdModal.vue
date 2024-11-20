<template>
  <div class="modal-backdrop">
    <div class="modal-content" @click.stop>
      <div class="modal-header">
        <h5 class="modal-title mb-3">아이디 찾기</h5>
        <button type="button" class="btn-close" @click="$emit('close')"
          style="position: absolute; top: 15px; right: 15px;"></button>
      </div>

      <div class="modal-body">
        <form @submit.prevent="handleSubmit">
          <div class="mb-3">
            <label class="form-label">이메일</label>
            <input
              type="email"
              class="form-control"
              v-model="email"
              placeholder="가입시 등록한 이메일을 입력하세요"
              required>
          </div>

          <div v-if="!notFound" class="alert alert-danger">
            찾으시는 아이디가 존재하지 않습니다.
          </div>

          <div v-if="notFound && foundId" class="alert alert-success">
            찾으신 아이디는 {{ maskId(foundId) }} 입니다.
          </div>


          <button type="submit" class="btn btn-primary w-100">
            아이디 찾기
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { localAxios } from "@/util/http-commons";
const local = localAxios();
const email = ref('');
const foundId = ref('');
const notFound = ref(true)

// 아이디 마스킹 처리
const maskId = (id) => {
  return id.substring(0, 2) + '*'.repeat(id.length - 2);
};

const handleSubmit = async () => {
  try {
    // API 호출
    const response = await local.post(`/member/find-id`, { email: email.value }, {
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (response.data.id) {
      console.log(response)
      foundId.value = response.data.id;
    }else{
      notFound.value = false;
    }
  } catch (error) {
    console.error('아이디 찾기 실패:', error);
  }
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
