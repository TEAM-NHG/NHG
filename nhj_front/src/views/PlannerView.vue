<template>
  <div class="container py-5" style="margin-top: 3%; padding: 0%;">
    <h1 class="mb-4">AI 여행 플래너</h1>

    <div class="mb-4">
      <p class="mb-2">● 나만의 여행코치는 생성형 AI를 활용한 서비스로 계획을 생성 혹은 평가 받을 수 있는 공간입니다.</p>
      <p>● 여행 일정에 대해 구체적으로 적을 수록 상세한 평가를 받을 수 있으며, 제시된 캐릭터 별로 같은 평가를 다르게 받아 보실 수 있습니다.</p>
    </div>

    <div class="emotion-buttons d-flex mb-4">
      <template  v-for="emotion in emotions" :key="emotion.id">

        <div class="emotion-button">
          <div class="d-flex align-items-center"
               @click="selectedEmotion = emotion; displayText = ''"
               style="width: 200px; cursor: pointer;">
            <img :src="emotion.src" alt="" class="me-3">

            <div class="me-3">
              {{ emotion.label }}
            </div>
          </div>
        </div>

      </template>
    </div>

    <div class="row">
      <div class="col-md-6 mb-4">
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-1">
              <h5 class="card-title">prompt 입력창</h5>
              <button class="btn btn-light mb-1" @click="handleEmotionClick(selectedEmotion)">생성</button>
            </div>
            <textarea
              v-model="prompt"
              class="form-control"
              rows="8"
              placeholder="여행 계획을 입력해주세요..."
              />
          </div>
        </div>
      </div>

      <div class="col-md-6 mb-4">
        <div class="card">
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
              <div>출력창</div>
              <img :src="selectedEmotion.src ? selectedEmotion.src : 'src/assets/insideout/characters.png'" alt="" style="width: 5%;">
            </div>
            <div class="output-area p-3 bg-light rounded">
              <div v-if="loading" class="loading-spinner">
                <font-awesome-icon :icon="['fas', 'spinner']" spin-pulse/>
              </div>
              <div v-else class="response-text">
                {{ displayText }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { localAxios } from "@/util/http-commons"

const local = localAxios()

// 반응형 상태 정의
const selectedEmotion = ref({id: '', label: '', src: ''})
const prompt = ref('')
const response = ref('')
const loading = ref(false)
const displayText = ref('')

// 감정 데이터
const emotions = [
  { id: 'happy', label: '기쁨', src: "src/assets/insideout/기쁨.png" },
  { id: 'strict', label: '까칠', src: "src/assets/insideout/까칠.png" },
  { id: 'worry', label: '걱정', src: "src/assets/insideout/걱정.png" },
  { id: 'angry', label: '분노', src: "src/assets/insideout/분노.png" },
  { id: 'boring', label: '따분', src: "src/assets/insideout/따분.png" },
]

// 감정 선택 처리
const handleEmotionClick = async (emotion) => {
  if(emotion.id === '') {
    response.value = '감정을 선택해주세요.'
    return;
  }else if(emotion.id === 'boring') {
    response.value = '귀찮으니까 니가 알아서 해, 난 다 좋아...'
    return;
  }

  selectedEmotion.value = emotion
  loading.value = true

  try {
    const res = await local.post(`/plan/ai`, {"userPlan" : prompt.value, "aiType" : emotion.id})
    console.log(response)
    response.value = res.data.answer
  } catch (error) {
    console.error('감정 데이터 가져오기 실패:', error)
    response.value = '데이터를 가져오는 중 오류가 발생했습니다.'
  } finally {
    loading.value = false
  }
}

// 텍스트 애니메이션 효과
watch([response, loading], ([newResponse, isLoading]) => {
  if (!isLoading && newResponse) {
    let index = 0
    displayText.value = ''

    const interval = setInterval(() => {
      if (index < newResponse.length) {
        displayText.value += newResponse[index]
        index++
      } else {
        clearInterval(interval)
      }
    }, 30)

    // 컴포넌트 언마운트 시 인터벌 정리
    return () => clearInterval(interval)
  }
})
</script>
<style scoped>

.emotion-button img {
  width: 60px;
  height: 100px;
  transition: all 0.3s ease;
}

.emotion-button:hover {
  transform: scale(1.05);
}


.output-area {
  height: 205px;
  overflow-y: auto;
}

.loading-spinner {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.response-text {
  white-space: pre-wrap;
  word-break: break-word;
}

/* 스크롤바 스타일링 */
.output-area::-webkit-scrollbar {
  width: 8px;
}

.output-area::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.output-area::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.output-area::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
