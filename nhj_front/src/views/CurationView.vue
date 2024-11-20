<!-- src/views/CurationView.vue -->
<template>
  <div class="curation-container p-4">
    <h2 class="mb-4" style="cursor: pointer; width: 12rem;" @click="filtersClear(filters)">여행 큐레이션</h2>

    <!-- Search Component -->
    <CurationSearch @search="handleSearch" />

    <div v-if="isLoading" style="text-align: center;">
      <font-awesome-icon :icon="['fas', 'spinner']" spin-pulse/>
    </div>

    <!-- Cards Grid -->
    <div class="row g-4" v-else>
      <CurationCard
        v-for="card in CardInfos"
        :key="card.no"
        :card="card"
      />
    </div>

    <VPageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
        style="margin: 30px;"
      ></VPageNavigation>
  </div>
</template>

<script setup>
import CurationSearch from '@/components/Curation/CurationSearch.vue'
import CurationCard from '@/components/Curation/CurationCard.vue'
import VPageNavigation from '@/components/common/VPageNavigation.vue';
import { ref, onMounted } from 'vue'
import { localAxios } from "@/util/http-commons";
const local = localAxios();

const CardInfos = ref([])
const isLoading = ref(false)

const currentPage = ref(1);
const totalPage = ref(0);
const filters = ref({
  sidoCode: '',
  gugunCode: '',
  type: '',
  content: '',
  pgno: currentPage.value,
  // spp: 12,
});

//필터 초기화
const filtersClear = (obj) => {
  Object.keys(obj).forEach(key => {
    obj[key] = '';
  });
  obj.pgno = 1
  makeCards();
}

// API로 여행 카드 데이터를 가져오는 함수
const makeCards = async (filterInfo) => {
  try {
    const response = await local.get('/trip-info/search', {
      params: filterInfo,
      onStart: () => (isLoading.value = true),    // 로딩 시작
      onFinish: () => (isLoading.value = false),  // 로딩 종료
       })

    CardInfos.value = response.data.attractionList
    currentPage.value = response.data.currentPage;
    totalPage.value = response.data.lastPage;

  } catch (error) {
    console.error('Failed to fetch travel cards:', error)
  }
}

// 검색 버튼 클릭 시 필터된 데이터 로드
const handleSearch = async (SelectedFilters) => {
  filters.value = {
    ...SelectedFilters,
    pgno: 1
  }
  console.log(filters.value)
  await makeCards(filters.value)
}

onMounted(() => {
  makeCards()
})

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  filters.value.pgno = val;
  makeCards(filters.value)
}

</script>

<style scoped>
.curation-container {
  margin-left: 15%;
  margin-right: 15%;
  margin-top: 5%;
}

</style>
