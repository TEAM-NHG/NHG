<!-- src/components/Search.vue -->
<template>
  <div class="filter-section mb-5 d-flex gap-3">
    <select class="form-select" v-model="filters.sidoCode">
      <option value="" disabled selected>시도</option>
      <option value="">전체</option>
      <option v-for="sido in sidoList" :key="sido.no" :value="sido.sidoCode">{{ sido.sidoName }}</option>
    </select>
    <select class="form-select" v-model="filters.gugunCode">
      <option value="" disabled selected>구군</option>
      <option value="">전체</option>
      <option v-for="gugun in gugunList" :key="gugun.gugunCode" :value="gugun.gugunCode">{{ gugun.gugunName }}</option>
    </select>
    <select class="form-select" v-model="filters.type">
      <option value="" disabled selected>타입</option>
      <option value="">전체</option>
      <option value="12">관광지</option>
      <option value="32">숙소</option>
      <option value="39">음식점</option>
    </select>

    <!-- Search Button -->
    <button @click="search" class="btn btn-primary">검색</button>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { localAxios } from "@/util/http-commons";
const local = localAxios();

const filters = ref({
  sidoCode: '',
  gugunCode: '',
  type: '',
  // content: '',
})

const emit = defineEmits(['search'])

// 부모에게 검색 이벤트 전달
const search = () => {
  emit('search', filters.value)
}

const gugunList = ref([])

watch(() => filters.value.sidoCode, async (newSido) => {
  const response = await local.get(`/trip-info/filter/gugun?sidoCode=${newSido}`)
  gugunList.value = response.data.gugunList
});


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

</script>

<style scoped>
/* 기존 스타일 유지 */
.form-select {
  width: 100px;
}

.form-select {
  width: 8vw;
}
</style>
