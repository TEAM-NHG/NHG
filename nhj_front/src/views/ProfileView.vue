<template>
  <div class="outerBox">
    <div class="innerBox">
      <!-- Profile Section -->
      <div class="card mb-4">
        <div class="user-profile">
          <div class="row align-items-center">
            <div class="col-auto">
              <img :src="authStore.user.img ? authStore.user.img : defaultUserIcon" class="user-img rounded-circle">
            </div>
            <div class="col">
              <div class="mb-2 d-flex align-items-center justify-content-between">
                <div class="me-3" style="font-size: 125%;">{{ authStore.user.nickname }}</div>
                <div class="me-3">
                  <button type="button" class="btn btn-outline-success btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                    수정
                  </button>
                  <ProfileEditModal />
                  <button type="button" class="btn btn-outline-danger btn-sm me-2" @click="MemberDelete">탈퇴</button>
                </div>
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
              <!-- <div v-show="btnvisible"
                   class="btn position-absolute top-0 end-0"
                   @click="openCreateModal"> 생성 </div> -->
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
          <router-view />
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import ProfileEditModal from "@/components/users/ProfileEditModal.vue";
import defaultUserIcon from '@/assets/userIcon.png';
import { ref } from "vue";
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";
import { localAxios } from "@/util/http-commons";

const authStore = useAuthStore();
const router = useRouter();
const local = localAxios();

const btnvisible = ref(true)

const MemberDelete = async () => {
  const confirmDelete = confirm("정말 회원 정보를 삭제하시겠습니까?");
  if (!confirmDelete) return;
  try {
    const userId = authStore.user.id;
    const response = await local.delete(`/member/${userId}`);
    if (response.status === 200) {
      alert("회원 정보가 성공적으로 삭제되었습니다.\n그동안 함께해주셔서 감사합니다.");
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
