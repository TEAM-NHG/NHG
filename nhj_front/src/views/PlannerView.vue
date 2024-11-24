<template>
  <!-- 전체 화면 배경에 이미지 고정 -->
  <div class="background-container">
    <!-- 어두운 오버레이 -->
    <div class="overlay"></div>
    <img src="@/assets/insideout/장기기억저장소.jpg" alt="Main Background"
      class="planner-image position-fixed top-0 start-0"
      style="height: 100%; width: 100%; object-fit: cover; z-index: -2;">

  </div>

  <div class="innerBox">

    <h1 class="mb-4">AI 여행 플래너</h1>
    <div class="mb-4">
      <p class="mb-2">● 나만의 여행코치는 생성형 AI를 활용한 서비스로 계획을 생성 혹은 평가 받을 수 있는 공간입니다.</p>
      <p>● 여행 일정에 대해 구체적으로 적을 수록 상세한 평가를 받을 수 있으며, 제시된 캐릭터 별로 같은 평가를 다르게 받아 보실 수 있습니다.</p>
    </div>
    <div class="emotion-buttons d-flex justify-content-center mb-4">
      <template v-for="emotion in emotions" :key="emotion.id">
        <div class="emotion-button">
          <div class="d-flex row justify-content-center mx-4" @click="selectEmotion(emotion)" style="cursor: pointer;">
            <img :src="emotion.selected ? emotion.src2 : emotion.src" alt="" class=""
              style="width: 100px; height: 150px;">
            <p class="" style="text-align: center;">
              {{ emotion.label }}
            </p>
          </div>
        </div>
      </template>
    </div>

    <div class="row">
      <div class="col-md-6 mb-4">
        <div class="card">
          <div class="card-body inputText">
            <div class="d-flex justify-content-between align-items-center mb-1">
              <div>prompt 입력창</div>
              <button class="btn btn-light mb-1" @click="handleEmotionClick(selectedEmotion)">생성</button>
            </div>
            <textarea v-model="prompt" class="form-control" rows="8" placeholder="여행 계획을 입력해주세요..."
              style="height: 20vh;" />
          </div>
        </div>
      </div>

      <div class="col-md-6 mb-4">
        <div class="card">
          <div class="card-body outputText" style="height: 280px;"
            :style="{ 'background-color': selectedEmotion.borderColor }">
            <div class="d-flex justify-content-between align-items-center" style="height: 42px;">
              <div>출력창</div>
            </div>
            <div class="output-area p-3 bg-light rounded mt-2 position-relative">
              <img class="position-absolute top-0 end-0" :src="selectedEmotion.src ?
                selectedEmotion.src : 'src/assets/insideout/characters.png'" style="height: 50px;">
              <div v-if="loading" class="loading-spinner">
                <font-awesome-icon :icon="['fas', 'spinner']" spin-pulse />
              </div>
              <div v-else class="response-text">
                {{ displayText }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <button
      class="tw-fixed tw-bottom-10 tw-right-20 tw-bg-blue-600 tw-text-white tw-rounded-full tw-p-3 tw-shadow-lg hover:tw-bg-blue-700 tw-transition tw-flex tw-items-center tw-justify-center"
      style="width: 80px; height: 80px;"
      @click="savePromt"></button>

    <!-- 모달 -->
    <PlannerModal
      v-if="isModalVisible"
      :travelData="selectedTravel"
      @close="closeModal"
      @save="handleSave"
      @delete="handleDelete"
    />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { localAxios } from "@/util/http-commons"
import PlannerModal from '@/components/users/PlannerModal.vue';
const local = localAxios()

// 반응형 상태 정의
const selectedEmotion = ref({
  id: '',
  label: '',
  src: '',
  selected: '',
  borderColor: ''
})
const prompt = ref('')
const response = ref('')
const loading = ref(false)
const displayText = ref('')
const interval = ref(null)

// 감정 데이터
const emotions = ref([
  { id: 'happy', label: '기쁨', src: "src/assets/insideout/기쁨.png", src2: "src/assets/insideout/기쁨2.png", selected: false, borderColor: "#FDFD71" },
  { id: 'strict', label: '까칠', src: "src/assets/insideout/까칠.png", src2: "src/assets/insideout/까칠2.png", selected: false, borderColor: "#85CA6E" },
  { id: 'worry', label: '걱정', src: "src/assets/insideout/걱정.png", src2: "src/assets/insideout/걱정2.png", selected: false, borderColor: "#D3B3D4" },
  { id: 'angry', label: '분노', src: "src/assets/insideout/분노.png", src2: "src/assets/insideout/분노2.png", selected: false, borderColor: "#E62C2B" },
  { id: 'boring', label: '따분', src: "src/assets/insideout/따분.png", src2: "src/assets/insideout/따분2.png", selected: false, borderColor: "#5756A6" },
])

// 감정 선택 처리
const handleEmotionClick = async (emotion) => {
  if (emotion.id === '') {
    response.value = '감정을 선택해주세요.'
    return;
  } else if (emotion.id === 'boring') {
    response.value = '귀찮으니까 니가 알아서 해, 난 다 좋아...'
    return;
  }

  selectedEmotion.value = emotion
  loading.value = true

  try {
    const res = await local.post(`/plan/ai`, { "userPlan": prompt.value, "aiType": emotion.id })
    console.log(response)
    response.value = res.data.answer
  } catch (error) {
    console.error('감정 데이터 가져오기 실패:', error)
    response.value = '데이터를 가져오는 중 오류가 발생했습니다.'
  } finally {
    loading.value = false
  }
}

const selectEmotion = (emotion) => {
  //선택값 부여
  emotions.value.forEach((e) => (e.selected = false));
  selectedEmotion.value = emotion;
  emotion.selected = true;

  //출력창 초기화
  displayText.value = '';
  clearInterval(interval)
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

//저장 구슬
const isModalVisible = ref(false)
const selectedTravel = ref(null);
const savePromt = () => {
  selectedTravel.value = {
    title: "",
    sido: "",
    gugun: "",
    image: "",
    startDate: "",  
    endDate: "",
    notes: `[입력창] \n${prompt.value}\n\n[출력창]\n${response.value}`,
    isCreated: true,
  };
  isModalVisible.value = true;
}

const closeModal = () => {
  isModalVisible.value = false;
  selectedTravel.value = null;
};

const handleSave = (updatedTravel) => {
  if (updatedTravel.id) {
    const index = travelList.value.findIndex((t) => t.id === updatedTravel.id);
    if (index !== -1) {
      travelList.value[index] = updatedTravel;
    }
  } else {
    updatedTravel.id = Date.now(); // 임시 ID
    travelList.value.push(updatedTravel);
  }
  closeModal();
};

const handleDelete = (travelId) => {
  travelList.value = travelList.value.filter((t) => t.id !== travelId);
  closeModal();
};
</script>
<style scoped>
.innerBox {
  background-color: rgba(0, 0, 0, 0.2);
  margin-top: 6%;
  padding: 4%;
  width: 70vw;
}

* {
  font-family: 'Bazzi', sans-serif;
  border-radius: 10px;
}

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

.inputText {
  background: linear-gradient(45deg, #ffff17, rgba(255, 0, 0, 0) 70.71%), linear-gradient(0deg, #72dc4f, rgba(0, 255, 0, 0) 70.71%), linear-gradient(245deg, #5756A6, rgba(0, 0, 255, 0) 70.71%), linear-gradient(180deg, #E62C2B, rgba(0, 0, 255, 0) 70.71%);
  color: black;
  /* 텍스트 색상 */
  border-radius: 10px;
}

.outputText {
  background-color: antiquewhite;
}

.card {
  border-radius: 10px;
  height: 280px;
}

.planner {
  background-color: #72dc4f;
  z-index: 3;
}

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.1); /* 검은색 반투명 오버레이 */
  z-index: -1; /* 이미지를 가리지 않게 뒤로 배치 */
}
</style>
