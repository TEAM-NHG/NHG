<template>
  <div class="outerBox">
    <div class="innerBox">
      <!-- Profile Section -->
      <div class="card mb-4">
        <div class="user-profile">
          <div class="row align-items-center">
            <div class="col-auto">
              <img :src="authStore.user.img ? authStore.user.img : '@/assets/userIcon.png'" class="user-img rounded-circle">
            </div>
            <div class="col">
              <div class="d-flex align-items-center mb-2">
                <div class="me-3" style="font-size: 125%;">{{ authStore.user.nickname }}</div>
                <button type="button" class="btn btn-outline-success btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                  수정
                </button>
                <ProfileEditModal />
                <button type="button" class="btn btn-outline-danger btn-sm me-2" @click="MemberDelete">탈퇴</button>
              </div>
              <p class="text-muted mb-1">{{ authStore.user.phone.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3") }}</p>
              <p class="text-muted mb-0">{{ authStore.user.email }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Navigation Tabs -->
      <div class="card">
        <div class="card-header">
          <ul class="nav nav-tabs card-header-tabs position-relative">
            <li class="nav-item">
              <router-link :to="{ name: 'my-travels' }"
                            class="nav-link"
                            :class="{ active: $route.name === 'my-travels' }"
                            @click="btnvisible = !btnvisible">
                나의 여행
              </router-link>
              <div v-show="btnvisible"
                   class="btn position-absolute top-0 end-0"
                   @click="openCreateModal"> 생성 </div>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'notifications' }"
                           class="nav-link"
                           :class="{ active: $route.name === 'notifications' }"
                           @click="btnvisible = !btnvisible">
                댓글 알림
              </router-link>
            </li>
          </ul>
        </div>
        <div class="card-body">
          <router-view @open-modal="openEditModal" :test="computedTravels" />
        </div>
      </div>
    </div>

    <!-- 모달 -->
    <PlannerModal
      v-if="isModalVisible"
      :travelData="selectedTravel"
      @close="closeModal"
      @save="handleSave"
      @delete="handleDelete"
      @update="handleUpdate"
    />
  </div>
</template>

<script setup>
import ProfileEditModal from "@/components/users/ProfileEditModal.vue";
import PlannerModal from "@/components/users/PlannerModal.vue";

import { ref, onMounted, computed } from "vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import { localAxios } from "@/util/http-commons";

const authStore = useAuthStore();
const router = useRouter();
const local = localAxios();

const isModalVisible = ref(false);
const selectedTravel = ref(null);

onMounted( () => {
  getTravleList()
})

const travels = ref([])
const computedTravels = computed(() => travels.value);
const getTravleList = async () => {
  try{
    const reponse = await local.get('/plan')
    travels.value = reponse.data.plans;
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

const btnvisible = ref(true)

const openEditModal = (travel) => {
  selectedTravel.value = travel;
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
    alert('저장 되었습니다.')
    closeModal();
  }catch(error){
    console.log("여행 계획 생성 실패: ", error)
  }

};

const handleUpdate = async (updatedTravel) => {
  try{
    await local.put('/plan', updatedTravel)
    alert('수정 되었습니다.')
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
    // travelList.value = travelList.value.filter((t) => t.id !== travelId);
    closeModal()
  }catch(error){
    console.log("여행 계획 삭제 실패: ", error)
  }

};

const MemberDelete = async () => {
  const confirmDelete = confirm("정말 회원 정보를 삭제하시겠습니까?");
  if (!confirmDelete) return;
  try {
    const userId = authStore.user.id;
    const response = await local.delete(`/member/${userId}`);
    if (response.status === 200) {
      alert("회원 정보가 성공적으로 삭제되었습니다.");
      authStore.logout();
      router.replace({ name: "home" });
    } else {
      alert("회원 정보 삭제에 실패했습니다. 다시 시도해주세요.");
    }
  } catch (error) {
    console.error("Error deleting member:", error);
    alert("오류가 발생했습니다. 관리자에게 문의하세요.");
  }
};
</script>

<style scoped>
.outerBox{
  background-color: #191A1C;
  width: 100vw;
  height: 100%;
  min-height: 92vh;
  margin-top: 8vh;
}

.innerBox {
  background-color: aliceblue;
  border-radius: 20px;
  height: 100%;
  padding: 2%;
  width: 60vw;
}
.nav-link {
  color: #000000;
}
.nav-link.active {
  color: #0056b3 !important;
  font-weight: 600;
}

.user-img {
  width: 120px;
  height: 120px;
  object-fit: cover;
  border-radius: 50%;
}

.text-muted{
  font-size: 90%;
}
</style>
