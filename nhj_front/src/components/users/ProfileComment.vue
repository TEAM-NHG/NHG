<template>
  <div class="list-group">
    <div v-for="notification in notifications" :key="notification.id" 
      class="list-group-item list-group-item-action">
      <div class="d-flex w-100 align-items-center">
        <img 
          :src="notification.userImage" 
          :alt="notification.userName"
          class="rounded-circle me-3"
          style="width: 48px; height: 48px; object-fit: cover;"
        />
        <div class="flex-grow-1">
          <div class="d-flex justify-content-between align-items-center">
            <p class="mb-1">
              <strong>{{ notification.userName }}</strong>
              님이 회원님의 게시글에 댓글을 남겼습니다
            </p>
            <button type="button" class="btn-close" aria-label="Close"></button>
          </div>
          <p class="mb-1 text-muted">{{ notification.comment }}</p>
          <small class="text-muted">{{ notification.time }}</small>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const notifications = ref([
  {
    id: 1,
    userName: '여행러버',
    userImage: '/placeholder-user-1.jpg',
    comment: '정말 좋은 여행 코스네요!',
    time: '10분 전'
  },
  // 더미 데이터 추가 가능
]);
</script>

<style scoped>
.list-group-item {
  transition: background-color 0.2s;
}

.list-group-item:hover {
  background-color: #f8f9fa;
}
</style>