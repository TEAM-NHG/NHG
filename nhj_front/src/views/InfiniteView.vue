<template>
  <div class="infinite-scroll">
    <ul>
      <li v-for="item in items" :key="item.id" class="item">
        {{ item.name }}
      </li>
    </ul>
    <div ref="loadMoreTrigger" class="loading-trigger" v-if="!allLoaded">
      <p v-if="isLoading">Loading...</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const items = ref([]); // 현재 로드된 아이템 목록
const page = ref(1); // 현재 페이지
const isLoading = ref(false); // 로딩 상태
const allLoaded = ref(false); // 모든 데이터를 로드했는지 여부
const loadMoreTrigger = ref(null); // 관찰 대상 요소

const fetchData = async () => {
  if (isLoading.value || allLoaded.value) return;

  isLoading.value = true;

  // 데이터를 가져오는 API 호출 (예제 API 사용)
  try {
    const response = await fetch(
      `https://jsonplaceholder.typicode.com/comments?_page=${page.value}&_limit=10`
    );
    const data = await response.json();

    if (data.length > 0) {
      items.value.push(...data); // 기존 데이터에 추가
      page.value += 1; // 다음 페이지로 업데이트
    } else {
      allLoaded.value = true; // 데이터가 더 이상 없을 경우
    }
  } catch (error) {
    console.error('Error fetching data:', error);
  } finally {
    isLoading.value = false;
  }
};

const setupObserver = () => {
  const observer = new IntersectionObserver(async (entries) => {
    if (entries[0].isIntersecting && !isLoading.value) {
      await fetchData(); // 트리거가 화면에 나타나면 데이터 로드
    }
  });

  if (loadMoreTrigger.value) {
    observer.observe(loadMoreTrigger.value);
  }
};

onMounted(() => {
  fetchData(); // 초기 데이터 로드
  setupObserver(); // Intersection Observer 설정
});
</script>

<style scoped>
.infinite-scroll {
  max-width: 600px;
  margin: auto;
  padding: 1rem;
}

.item {
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.loading-trigger {
  text-align: center;
  margin-top: 10px;
}

.loading-trigger p {
  color: gray;
}
</style>
