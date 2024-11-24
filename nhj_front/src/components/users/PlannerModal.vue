<template>
  <div class="modal fade show d-block" tabindex="-1">
    <div class="modal-dialog modal-lg">
      <div class="modal-content position-relative">
        <!-- <img v-if="!isEditing"
             class="position-absolute top-0 start-0" 
             :src="'src/assets/planner/noImage4.jpeg'"
             style="z-index: -1; object-fit: fill"> -->
        <!-- Header -->
        <div class="modal-header">
          <h5 class="modal-title">
            {{ localData.isCreated ? "Create a New Travel Plan" : 
               localData.isEditing ? "Edit Your Travel Plan" : (travelData.title || "제목이 없습니다.") }}
          </h5>
          <button type="button" class="btn-close" @click="closeModal"></button>
        </div>

        <!-- Body -->
        <div class="modal-body">
          <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input
              v-if="localData.isEditing"
              id="title"
              class="form-control"
              v-model="localData.title"
              placeholder="제목을 입력해주세요"
            />
            <p v-else>{{ travelData.title || "제목이 없습니다." }}</p>
          </div>

          <!-- 지역 selectbox -->
          <div class="mb-3">
            <div v-if="localData.isEditing">
              <label class="form-label">여행지</label>
              <div class="d-flex gap-2">
                <select class="form-select" v-model="localData.sido" style="width: 15vw">
                  <option value="" disabled selected>시도</option>
                  <option v-for="sido in sidoList" 
                          :key="sido.no" 
                          :value="sido.sidoName"
                          >{{ sido.sidoName }}</option>
                </select>
                <select class="form-select" v-model="localData.gugun" style="width: 15vw">
                  <option value="" disabled selected>구군</option>
                  <option v-for="gugun in gugunList" 
                          :key="gugun.gugunCode" 
                          :value="gugun.gugunName"
                          >{{ gugun.gugunName }}</option>
                </select>
              </div>
            </div>
            <div v-else>
              <div>여행지</div>
              <div>{{ `${travelData.sido || "N/A"}, ${travelData.gugun || "N/A"}` }}</div>
            </div>
          </div>

          <!-- 이미지 -->
          <div class="mb-3" v-if="localData.isEditing">
            <label for="image" class="form-label">카드 이미지</label>
            <input
              id="image"
              class="form-control"
              type="file"
              @change="handleImageUpload"
            />
            <!-- <img
              v-else
              :src="travelData.image || 'https://via.placeholder.com/150'"
              alt="Travel Image"
              class="img-fluid rounded"
            /> -->
          </div>

          <template v-if="localData.isEditing">
            <div class="d-flex">
              <div class="mb-3" style="width: 30%;">
                <label class="form-label">여행 시작일</label> <br>
                <input
                  class="form-control"
                  type="date"
                  v-model="localData.startDate"
                />
              </div>
              
              <div class="mb-3 mx-4" style="width: 30%;">
                <label class="form-label">여행 종료일</label>
                <input
                class="form-control"
                type="date"
                v-model="localData.endDate"
                />
              </div>
            </div>

          </template>

          <div v-else class="mb-3">
            <div class="mb-2">여행 일자</div>
            <span> {{ travelData.startDate || '' }} ~ {{ travelData.endDate || '' }}</span>
          </div>

          <div class="mb-3">
            <label for="notes" class="form-label">여행 계획</label>
            <textarea
              v-if="localData.isEditing"
              id="notes"
              class="form-control"
              rows="10"
              v-model="localData.notes"
              placeholder="여행 계획을 작성해주세요."
            ></textarea>
            <p v-else>{{ travelData.notes || "작성된 여행 계획이 없습니다." }}</p>
          </div>
        </div>

        <!-- Footer -->
        <div class="modal-footer">
          <button
            v-if="localData.isEditing"
            class="btn btn-primary"
            @click="saveChanges"
          >
            Save
          </button>
          <button
            v-if="!localData.isCreated && localData.isEditing"
            class="btn btn-secondary"
            @click="cancelEdit"
          >
            Cancel
          </button>
          <button v-if="!localData.isEditing" class="btn btn-outline-primary" @click="toggleEdit">
            Edit
          </button>
          <button v-if="!localData.isCreated" class="btn btn-danger" @click="confirmDelete">Delete</button>
          <button class="btn btn-secondary" @click="closeModal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, watch } from "vue";

import { localAxios } from "@/util/http-commons";
const local = localAxios();

// Props
const props = defineProps({
  travelData: Object, // 기존 데이터 (or 빈 객체)
});

// 로컬 상태 관리
const localData = ref(props.travelData);
localData.value.isEditing = localData.value.isCreated;

// 시도 및 구군 데이터
const sidoList = ref([
  { "no": 1, "sidoCode": 1, "sidoName": "서울" },
  { "no": 2, "sidoCode": 2, "sidoName": "인천" },
  { "no": 3, "sidoCode": 3, "sidoName": "대전" },
  { "no": 4, "sidoCode": 4, "sidoName": "대구" },
  { "no": 5, "sidoCode": 5, "sidoName": "광주" },
  { "no": 6, "sidoCode": 6, "sidoName": "부산" },
  { "no": 7, "sidoCode": 7, "sidoName": "울산" },
  { "no": 8, "sidoCode": 8, "sidoName": "세종특별자치시" },
  { "no": 9, "sidoCode": 31, "sidoName": "경기도" },
  { "no": 10, "sidoCode": 32, "sidoName": "강원도" },
  { "no": 11, "sidoCode": 33, "sidoName": "충청북도" },
  { "no": 12, "sidoCode": 34, "sidoName": "충청남도" },
  { "no": 13, "sidoCode": 35, "sidoName": "경상북도" },
  { "no": 14, "sidoCode": 36, "sidoName": "경상남도" },
  { "no": 15, "sidoCode": 37, "sidoName": "전라북도" },
  { "no": 16, "sidoCode": 38, "sidoName": "전라남도" },
  { "no": 17, "sidoCode": 39, "sidoName": "제주도" }])

const gugunList = ref([])

const sidoCode = ref(null)

watch(() => localData.value.sido, async (sidoName) => {
  const newSido = sidoList.value.filter((s) => s.sidoName === sidoName)
  const response = await local.get(`/trip-info/filter/gugun?sidoCode=${newSido[0].sidoCode}`)
  gugunList.value = response.data.gugunList
});

// Emit 정의
const emit = defineEmits(["save", "delete", "close"]);

// Methods
const toggleEdit = () => {
  localData.value.isEditing = true;
};

const saveChanges = () => {
  // 저장 로직 (백엔드로 데이터 전송)
  emit("save", localData);
  localData.value.isEditing = false;
};

const cancelEdit = () => {
  Object.assign(localData, props.travelData); // 원본 데이터 복구
  localData.value.isEditing = false;
};

const confirmDelete = () => {
  if (confirm("정말 삭제하시겠습니까?")) {
    emit("delete", props.travelData.id);
  }
};

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    localData.image = URL.createObjectURL(file);
  }
};

const closeModal = () => {
  emit("close");
};
</script>


<style scoped>
.modal-body label {
  font-weight: bold;
}
.img-fluid {
  max-width: 100%;
  max-height: 200px;
  object-fit: cover;
}
</style>
