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
    notes: "여행계획, 1일차 ~~~ 2일차~~~~"
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
