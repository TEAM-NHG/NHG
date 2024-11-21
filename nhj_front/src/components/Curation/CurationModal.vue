<!-- src/components/CurationModal.vue -->
<template>
  <div class="modal " tabindex="-1" @click="$emit('showModal')">
    <div class="modal-dialog modal-xl modal-dialog-centered">
      <div class="modal-content p-4" style="border-radius: 15px;" v-if="!isLoading">
        <!-- 닫기 버튼 -->
        <button type="button" class="btn-close" @click="$emit('closeModal')"
          style="position: absolute; top: 15px; right: 15px;"></button>

        <div class="row ">
          <!-- 왼쪽: 정보 섹션 -->
          <div class="col-lg-6 d-flex flex-column justify-content-center">
            <div class="fw-bold my-3 mx-4" style="font-size: 150%;">[ {{ card.title }} ]</div>
            <div class="mb-2 mx-4 scroll"
                 style="overflow:auto; height: 300px;" 
                 v-html="card.overview.replace(/\([^()]*\)/g, '').replace(/\([^)]*\)/g, '')">
            </div>

            <!-- 아이콘 리스트 -->
            <div class="d-flex justify-content-center align-items-center m-3">
              <img v-for="(image, index) in images" :key="index" :src="image.src" alt="info-icon" class="img-fluid mx-4"
                style="width: 30px; height: 30px; cursor: pointer;"
                @click="selectedInfo = image.text" />
            </div>

            <!-- 선택된 정보 -->
            <p v-show="!selectedInfo" style="color: white;">text</p>
            <p class="mb-4" style="text-align: center;" v-html="selectedInfo" ></p>

            <!-- 버튼 -->
            <div class="d-flex justify-content-center m-2">
              <RouterLink :to="{ name: 'news', params: { word: card.title } }"> <button class="btn">블로그</button> </RouterLink>
              <RouterLink :to="{ name: 'board', params: { word: card.title } }"> <button class="btn">동행글</button>
              </RouterLink>
            </div>
          </div>

          <!-- 오른쪽: 지도 섹션 -->
          <div class="col-lg-6 d-flex justify-content-center align-items-center">
            <KakaoMap :lat="card.latitude" :lng="card.longitude" :draggable="true" width="95%">
              <KakaoMapMarker :lat="card.latitude" :lng="card.longitude"></KakaoMapMarker>
            </KakaoMap>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

//kakao map
import { KakaoMap, KakaoMapMarker } from 'vue3-kakao-maps';

// Props & emit
const props = defineProps(['no'])
defineEmits(['closeModal', 'showModal'])

//axios
import { localAxios } from '@/util/http-commons';
const local = localAxios();

const card = ref({
  title: '',
  overview: '',
  latitude: 0,
  longitude: 0,
  detail: {
    addr1: '',
    homepage: '',
    tel: '',
    time: '',
    parking: '',
  },
});
const isLoading = ref(false)

onMounted( () => {
  makeModalInfo();
})

const makeModalInfo = async () => {
  const response = await local.get(`/trip-info/search/attraction/${props.no}`, {
    onStart: () => (isLoading.value = true),    // 로딩 시작
    onFinish: () => (isLoading.value = false),  // 로딩 종료
  });
  card.value = response.data
}

// Data
const selectedInfo = ref('')
const images = computed(() => [
  { src: "src/assets/modal-icon/adress.svg", text: card.value.addr1 || "주소 정보가 없습니다." },
  { src: 'src/assets/modal-icon/homepage.svg', text: card.value.homepage || "홈페이지 주소가 없습니다." },
  { src: 'src/assets/modal-icon/phone.svg', text: card.value.tel || "번호 정보가 없습니다." },
  { src: 'src/assets/modal-icon/time.svg', text: card.value.time || "운영 시간 정보가 없습니다." },
  { src: 'src/assets/modal-icon/parking.svg', text: card.value.parking || "주차 정보가 없습니다." },
]);

</script>

<style scoped>
.modal-content {
  background-color: #FFFFFF;
}

.btn {
  margin-right: 30px;
  background-color: #CBDCEB;
  color: #133E87;
}

/* 스크롤바 스타일링 */
.scroll::-webkit-scrollbar {
  width: 8px;
}

.scroll::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.scroll::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.scroll::-webkit-scrollbar-thumb:hover {
  background: #555;
}
</style>
