<template>
  <div class="row g-4">
    <div v-for="travel in travels" :key="travel.id" class="col-12 col-md-6 col-lg-4">
      <div class="card" style="width: 15vw;">
        <img :src="travel.image ? travel.image : 'src/assets/planner/noImage4.jpeg'" 
              class="card-img-top" 
              :alt="travel.title" 
              style="width: 100%; height: 200px; 
              object-fit: cover;">
        <div class="card-body">
          <h5 class="card-title">{{ travel.title }}</h5>
          <p class="card-text text-muted small">{{ travel.date }}</p>
          <div class="d-flex justify-content-between align-items-center">
            <span class="text-primary">{{ travel.location }}</span>
            <div class="dropdown">
              <button class="btn btn-link text-muted p-0" type="button" data-bs-toggle="dropdown">
                <i class="bi bi-three-dots-vertical"></i>
              </button>
              <ul class="dropdown-menu">
                <li><a class="dropdown-item" href="#">수정</a></li>
                <li><a class="dropdown-item" href="#">삭제</a></li>
                <li><a class="dropdown-item" href="#">공유</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const travels = ref([
  {
    id: 1,
    title: '제주도 여행',
    date: '2024.03.15 - 2024.03.18',
    location: '제주도',
    image: ''
  },
  // 더미 데이터 추가 가능
]);
</script>

<style scoped>
.card {
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-5px);
}
</style>