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
          <div class="modal-title me-2  ">
            {{ localData.isCreated ? "Create a New Travel Plan" :
               localData.isEditing ? "Edit Your Travel Plan" : ("Your Plan" || "제목이 없습니다.") }}
          </div>
          <font-awesome-icon :icon="['fas', 'plane-departure']" style="color: #000000;" />
          <button type="button" class="btn-close" @click="closeModal"></button>
        </div>

        <!-- Body -->
        <div class="modal-body px-4">
          <div :class="{'info-header' : !localData.isEditing}">
            <div class="mb-2" style="height: 70px;">
              <mark class="sky" v-if="!localData.isEditing">
                <label for="title" class="form-label">제목</label>
              </mark>
              <label v-else for="title" class="form-label">제목</label>
              <input
                v-if="localData.isEditing"
                id="title"
                class="form-control"
                v-model="localData.title"
                placeholder="제목을 입력해주세요"
              />
              <div v-else class="ms-2">{{ travelData.title || "제목이 없습니다." }}</div>
            </div>

            <!-- 지역 selectbox -->
            <div class="mb-3">
              <mark class="sky" v-if="!localData.isEditing">
                  <label class="form-label">여행지</label>
                </mark>
              <label v-else class="form-label">여행지</label>
              <div v-if="localData.isEditing">
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
                <div class="ms-2">{{ `${travelData.sido || "N/A"}, ${travelData.gugun || "N/A"}` }}</div>
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
            </div>

            <!-- 여행 일자 -->
            <mark class="sky" v-if="!localData.isEditing">
              <label class="form-label">여행 일자</label>
            </mark>
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

            <div v-else class="mb-3 ms-2">
              <span> {{ travelData.startDate || '' }} ~ {{ travelData.endDate || '' }}</span>
            </div>
          </div>
            <mark class="sky" v-if="!localData.isEditing">
              <label class="form-label">여행 계획</label>
            </mark>
            <label v-else class="form-label">여행 계획</label>
            <textarea
              v-if="localData.isEditing"
              id="notes"
              class="form-control"
              rows="10"
              v-model="localData.notes"
              placeholder="여행 계획을 작성해주세요."
              style="resize: none;"
            ></textarea>
            <div v-else
                 class="plan-text px-2"
                 v-html="travelData.notes.replace(/\n/g, '<br>') || '작성된 여행 계획이 없습니다.'">
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
localData.value.startDate = localData.value.startDate.replace(/T.*/, "")
localData.value.endDate = localData.value.endDate.replace(/T.*/, "")

//유효성 검사
const validateForm = () => {

  if (!localData.value.title.trim()) {
    alert('제목을 입력해주세요.');
    return false;
  }
  if(!localData.value.sido) {
    alert('여행 시도를 선택해주세요.');
    return false;
  }
  if(!localData.value.gugun) {
    alert('여행 구군을 선택해주세요.');
    return false;
  }
  if (!localData.value.startDate) {
    alert('시작 날짜를 선택해주세요.');
    return false;
  }
  if (!localData.value.endDate) {
    alert('종료 날짜를 선택해주세요.');
    return false;
  }
  if (new Date(localData.value.endDate) < new Date(localData.value.startDate)) {
    alert(`여행 날짜를 확인해주세요.\n종료 날짜는 시작 날짜보다 앞설 수 없습니다.`);
    return false;
  }
  if (!localData.value.notes.trim()) {
    alert('여행 계획을 입력해주세요.');
    return false;
  }

  return true;
};

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

watch(() => localData.value.sido, async (sidoName) => {
  const newSido = sidoList.value.filter((s) => s.sidoName === sidoName)
  const response = await local.get(`/trip-info/filter/gugun?sidoCode=${newSido[0].sidoCode}`)
  gugunList.value = response.data.gugunList
},{immediate: true});

// Emit 정의
const emit = defineEmits(["save", "delete", "close", "update"]);

// Methods
const toggleEdit = () => {
  localData.value.isEditing = true;
};

const PlannerFormData = new FormData();
const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    PlannerFormData.append("images", file);
  }
};

const saveChanges = () => {
  if (!validateForm()) {
    return
  }
  PlannerFormData.append("plan", JSON.stringify(localData.value));
  if(localData.value.isCreated) {
    emit("save", PlannerFormData)
    closeModal()
  }else{
    emit("update", PlannerFormData)
  }

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

const closeModal = () => {
  emit("close");
};
</script>


<style scoped>
.modal-body {
  font-weight: bold;
  height: 100%;
}
.img-fluid {
  max-width: 100%;
  max-height: 200px;
  object-fit: cover;
}
.form-label{
  font-size: 120%;
}
.plan-text{
  overflow: auto;
  height: 350px;
  border-top: 1.5px solid black;
  border-bottom: 1.5px solid black;
  border-radius: 5px;
  padding-top: 1rem;
}
</style>
