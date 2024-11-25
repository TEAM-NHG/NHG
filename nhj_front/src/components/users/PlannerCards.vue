<template>
  <div class="row g-4 px-4 pt-1">
    <div v-for="travel in travels" :key="travel.id" class="col-12 col-md-6 col-lg-4">
      <div class="card w-100" @click="openModal(travel)" style="width: 15vw;">
        <img :src="travel.image ? 'http://localhost'+travel.image : 'src/assets/planner/noImage4.jpeg'" class="card-img-top" :alt="travel.title" style="width: 100%; height: 200px; object-fit: cover;">
        <div class="card-body">
          <h5 class="card-title">{{ travel.title }}</h5>
          <div class="card-text text-muted small" style="font-size: 70%;">{{ travel.startDate.replace(/T.*/, "") }} ~ {{travel.endDate.replace(/T.*/, "")}}</div>
          <span class="text-primary small">{{ travel.sido }} {{travel.gugun}}</span>
        </div>
      </div>
    </div>
  </div>

  <div class="btn" @click="openCreateModal"> 생성 </div>

  <!-- 모달 -->
  <PlannerModal
      v-if="isModalVisible"
      :travelData="selectedTravel"
      @close="closeModal"
      @save="handleSave"
      @delete="handleDelete"
      @update="handleUpdate"
    />
</template>

<script setup>
import { ref, onMounted } from "vue";
import { localAxios } from "@/util/http-commons";
import PlannerModal from "./PlannerModal.vue";

const local = localAxios();
const emit = defineEmits(['open-modal'])


const travels = ref([])
const isModalVisible = ref(false);
const selectedTravel = ref(null);

onMounted(async () => {
  getTravleList()
});


const getTravleList = async () => {
  try{
    const response = await local.get('/plan')
    // console.log(...response.data.plans)
    travels.value = response.data.plans;
  }catch(error){
    console.log("여행 계획 생성 실패 :",error)
  }
}

const openCreateModal = () => {
  selectedTravel.value = {
    title: "",
    sido: "",
    gugun: "",
    image: "",
    startDate: "",
    endDate: "",
    notes: "",
    isCreated: true,
  };
  isModalVisible.value = true;
};

const openModal = (travel) => {
  selectedTravel.value = travel
  isModalVisible.value = true;
};

const closeModal = () => {
  isModalVisible.value = false;
  selectedTravel.value = null;
};

const handleSave = async (saveTravel) => {
  console.log(saveTravel)
  try{
    await local.post('/plan', saveTravel, {
          headers: { "Content-Type": "multipart/form-data" },
        });
    getTravleList()
    alert('저장 되었습니다.')
    closeModal();
  }catch(error){
    console.log("여행 계획 생성 실패: ", error)
  }

};

const handleUpdate = async (updatedTravel) => {
  try{
    await local.put('/plan', updatedTravel, {
          headers: { "Content-Type": "multipart/form-data" },
        });
    alert('수정 되었습니다.')
    getTravleList()
    closeModal();
  }catch(error){
    console.log("여행 계획 수정 실패: ", error)
  }
}

const handleDelete = async (travelId) => {
  //API요청
  try{
    await local.delete(`/plan/${travelId}`)
    alert('삭제가 완료되었습니다.')
    getTravleList()
    closeModal()
  }catch(error){
    console.log("여행 계획 삭제 실패: ", error)
  }

};

</script>

<style scoped>
.card {
  transition: transform 0.2s;
}
.card:hover {
  transform: translateY(-5px);
}
.btn{
  position: absolute;
  top: 8px;
  right: 8px;
}
</style>
