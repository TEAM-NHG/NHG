<template>
  <div class="row g-4">
    <div v-for="travel in travels" :key="travel.id" class="col-12 col-md-6 col-lg-4">
      <div class="card" @click="openModal(travel)" style="width: 15vw;">
        <img :src="travel.image ? travel.image : 'src/assets/planner/noImage4.jpeg'" class="card-img-top" :alt="travel.title" style="width: 100%; height: 200px; object-fit: cover;">
        <div class="card-body">
          <h5 class="card-title">{{ travel.title }}</h5>
          <div class="card-text text-muted small" style="font-size: 70%;">{{ travel.startDate.replace(/T.*/, "") }} ~ {{travel.endDate.replace(/T.*/, "")}}</div>
          <span class="text-primary small">{{ travel.sido }} {{travel.gugun}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { localAxios } from "@/util/http-commons";

const local = localAxios();
const emit = defineEmits(['open-modal'])


const props = defineProps(['test'])
console.log(props.test)



const travels = ref([])

onMounted( () => {
  getTravleList()
})

const getTravleList = async () => {
  try{
    const reponse = await local.get('/plan')
    travels.value = reponse.data.plans;
  }catch(error){
    console.log("여행 계획 생성 실패 :",error)
  }
}

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
