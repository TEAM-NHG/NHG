<template>
  <div class="row g-4">
    <div v-for="travel in travels" :key="travel.id" class="col-12 col-md-6 col-lg-4">
      <div class="card" @click="openModal(travel)" style="width: 15vw;">
        <img :src="travel.image ? travel.image : 'src/assets/planner/noImage4.jpeg'" class="card-img-top" :alt="travel.title" style="width: 100%; height: 200px; object-fit: cover;">
        <div class="card-body">
          <h5 class="card-title">{{ travel.title }}</h5>
          <div class="card-text text-muted small" style="font-size: 70%;">{{ travel.startDate }} ~ {{travel.endDate}}</div>
          <span class="text-primary small">{{ travel.sido }} {{travel.gugun}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
const emit = defineEmits(['open-modal'])

const travels = ref([
  {
    id: 1,
    title: "제주도 여행",
    startDate: "2024-03-15",
    endDate: "2024-03-18",
    sido: "제주도",
    gugun: "제주시",
    image: "",
    notes: `Day 1: 서귀포 카페 투어\n` +
`오전\n` +
`항상 핫한 카페: 도착 후 앤트러사이트 서귀포점에서 여유로운 아침 시작. 독특한 로스팅 향과 함께 빈티지한 분위기를 느껴보세요.\n` +
`점심\n` +
`현지 음식 체험: 서귀포 올레시장 인근의 자매국수에서 제주도의 대표적인 고기국수를 맛보세요.\n` +
`오후\n` +
`더클리프 제주: 전망이 뛰어난 해안가 카페로, 탁 트인 바다를 보며 커피와 디저트를 즐기기에 좋습니다.\n` +
`월정리 카페 거리: 다양한 감성 카페들이 밀집해 있어 산책하며 취향에 맞는 곳에서 커피를 즐겨보세요.\n` +
`저녁\n` +
`흑돼지 BBQ: 저녁은 제주 흑돼지로 든든히! 서귀포의 돈사돈을 추천해요.\n` +
`밤\n` +
`제주 아일랜드 펍: 제주 특산 주류를 즐기며 분위기 좋은 펍에서 하루를 마무리해보세요.\n` +
`\n` +
`Day 2: 한경면과 협재 해변 카페 투어\n` +
`오전\n` +
`도토리 카페: 한경면의 아기자기한 카페에서 아침을 시작해 보세요. 바다 전망이 훌륭한 힐링 스팟입니다.\n` +
`점심\n` +
`카페 공작소: 제주 특유의 감성을 느낄 수 있는 카페로, 맛있는 파니니와 샐러드를 맛볼 수 있어요.\n` +
`오후\n` +
`협재 해변과 카페 오라: 협재 해변에서 산책 후, 인근의 카페 오라에서 바다를 바라보며 여유롭게 오후를 보내세요.\n` +
`아르떼 뮤지엄: 독특한 미디어 아트 전시를 관람할 수 있는 공간으로, 데이트 코스로 강력 추천합니다.\n` +
`저녁\n` +
`해산물 저녁 식사: 협재 인근의 해산물 전문점에서 신선한 해산물을 즐겨보세요.\n` +
`\n` +
`Day 3: 중문과 성산 카페 투어\n` +
`오전\n` +
`아침 카페 데이트: 중문에 위치한 더 문에서 탁 트인 바다 뷰를 감상하며 아침을 시작하세요.\n` +
`점심\n` +
`성산일출봉 근처: 성산에서 인기 있는 브런치 카페인 모찌모찌 카페에서 브런치를 즐겨 보세요.\n` +
`오후\n` +
`성산 카페거리 산책: 성산 일대의 카페거리에서 각자의 취향에 맞는 카페를 둘러보고, 제주 특유의 분위기를 만끽하세요.\n` +
`제주 아라리오 뮤지엄: 예술 작품을 관람하며 제주에서의 마지막 일정을 마무리하기에 좋습니다.`
  },
]);

const openModal = (travel) => {
  // 부모 컴포넌트에 이벤트 전달
  emit("open-modal", travel);
};
</script>

<style scoped>
.card {
  transition: transform 0.2s;
}
.card:hover {
  transform: translateY(-5px);
}
</style>
