<template>
  <div class="innerBox p-4">

    <div class="news-header">

      <div>
        <div class="mb-5" style="font-size: 200%;">블로그 여행 소식 모음집</div>
      </div>

      <!-- 검색창 -->
      <div class="d-flex search">
        <select class="form-select w-auto me-2" v-model="searchOption">
          <option value="title">제목</option>
        </select>
        <input
          type="text"
          class="form-control me-2"
          placeholder="검색어..."
          v-model="searchText"
        />
        <button class="btn btn-primary" style="width: 80px;" @click="search" @keyup.enter="search">검색</button>
      </div>

    </div>


    <!-- 카드 목록 -->
    <div class="row">
      <NewsCard
        v-for="card in cards"
        :key="card.blogger"
        :card="card"
      />
    </div>

    <!-- 무한스크롤 -->
    <div ref="loadMoreTrigger" class="loading-trigger" v-if="!allLoaded">
      <p v-if="isLoading">Loading...</p>
    </div>
  </div>
</template>

<script setup>
import NewsCard from "@/components/News/NewsCard.vue";
import { ref, onMounted } from "vue";

//route
import { useRoute } from "vue-router";
const route = useRoute();

//axios
import { localAxios } from "@/util/http-commons";
const local = localAxios()

const searchOption = ref("title");
const searchText = ref(route.params.word ? route.params.word : "")

// 데이터 불러오기
onMounted(async () => {
  fetchData(); // 초기 데이터 로드
  setupObserver(); // Intersection Observer 설정
});

// 검색 기능
const search = async () => {
  const response = await local.get('/trip-info/search/blog', {params : {keyword : searchText.value, pgno: 1}})
  cards.value = response.data.blogList
};

//무한 스크롤
const cards = ref([]); // 현재 로드된 아이템 목록
const page = ref(1); // 현재 페이지
const isLoading = ref(false); // 로딩 상태
const allLoaded = ref(false); // 모든 데이터를 로드했는지 여부
const loadMoreTrigger = ref(null); // 관찰 대상 요소

const fetchData = async () => {
  if (isLoading.value || allLoaded.value) return;

  isLoading.value = true;

  // 데이터를 가져오는 API 호출 (예제 API 사용)
  try {
    const response = await local.get('/trip-info/search/blog', {params : {keyword : searchText.value, pgno: page.value * 5}})
    if (response.data) {
      cards.value.push(...response.data.blogList); // 기존 데이터에 추가
      console.log(cards.value)
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

</script>

<style scoped>
.news-header{
  display: flex;
  justify-content: space-between;
  align-items: end;
  margin-bottom: 10px;
}

.search {
  height: 40px;
}


/* 무한스크롤 */
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
