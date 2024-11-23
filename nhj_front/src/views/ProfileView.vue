<template>
  <div>
    <div class="innerBox">
      <!-- Profile Section -->
      <div class="card mb-4">
        <div class="user-profile">
          <div class="row align-items-center">
            <div class="col-auto">
              <img v-if="authStore.user.img" :src="authStore.user.img" :alt="authStore.user.nickname"
                class="user-img rounded-circle" />
              <img v-else src="@/assets/userIcon.png" alt="noImg" class="user-img rounded-circle">
            </div>
            <div class="col">
              <div class="d-flex align-items-center mb-2">
                <h2 class="mb-0 me-3">{{ authStore.user.nickname }}</h2>
                <button type="button" class="btn btn-outline-success btn-sm me-2" data-bs-toggle="modal"
                  data-bs-target="#editProfileModal">
                  수정
                </button>
                <!-- 모달 컴포넌트 추가 -->
                <ProfileEditModal @update="handleProfileUpdate" />
                <button type="button" class="btn btn-outline-danger btn-sm me-2" @click="MemberDelete">탈퇴</button>
              </div>
              <p class="text-muted mb-1">{{ dashPhone }}</p>
              <p class="text-muted mb-0">{{ authStore.user.email }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- Navigation Tabs -->
      <div class="card">
        <div class="card-header">
          <ul class="nav nav-tabs card-header-tabs">
            <li class="nav-item">
              <router-link :to="{ name: 'my-travels' }" class="nav-link"
                :class="{ active: $route.name === 'my-travels' }">
                나의 여행
              </router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{ name: 'notifications' }" class="nav-link"
                :class="{ active: $route.name === 'notifications' }">
                댓글 알림
              </router-link>
            </li>
          </ul>
        </div>
        <div class="card-body">
          <router-view></router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import ProfileEditModal from '@/components/users/ProfileEditModal.vue';

//vue
import { computed } from 'vue'

//pinia
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

//route
import { useRouter } from "vue-router";
const router = useRouter();

//axios
import { localAxios } from "@/util/http-commons";
const local = localAxios();

// 프로필 업데이트 후 처리
const handleProfileUpdate = () => {
  // 필요한 경우 프로필 데이터 새로고침
  // authStore.fetchUserProfile();
};

const dashPhone = computed(() => {
  return authStore.user.phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
});


const MemberDelete = async () => {
  const confirmDelete = confirm("정말 회원 정보를 삭제하시겠습니까?");
  if (!confirmDelete) return; // 사용자가 취소를 누르면 함수 종료
  try {
    const userId = authStore.user.id; // authStore에서 사용자 ID 가져오기

    // 백엔드 API 호출
    const response = await local.delete(`/member/${userId}`);

    if (response.status === 200) {
      alert("회원 정보가 성공적으로 삭제되었습니다.");
      authStore.logout();
      // 로그아웃 처리 또는 화면 이동 (예: 홈으로 리다이렉트)
      router.replace({name:'home'})
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
* {
  font-family: 'goorm-sans-bold';
}

.innerBox {
  margin-top: 5%;
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

.user-profile{
  background-color: aquamarine;
}
</style>
