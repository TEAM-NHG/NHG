<template>
  <div class="list-group">

    <div v-if="notifications.length == 0">알림이 없습니다.</div>


    <div v-if="notifications.length > 0"
      v-for="notification in notifications" :key="notification.id"
      class="list-group-item list-group-item-action">
      <div class="d-flex w-100 align-items-center">
        <img
          :src="notification.userImage ? notification.userImage : 'src/assets/userIcon.png'"
          :alt="notification.userName"
          class="rounded-circle me-3"
          style="width: 48px; height: 48px; object-fit: cover;"
        />
        <div class="flex-grow-1">
          <div class="d-flex justify-content-between align-items-center">
            <p class="mb-1">
              <strong>{{ notification.userId }}</strong>
              님이 회원님의 게시글에 댓글을 남겼습니다
            </p>
            <button type="button" class="btn-close" @click="deleteNotification(notification.id, authStore.user.id)"></button>
          </div>
          <p class="mb-1 text-muted">{{ notification.content }}</p>
          <small class="text-muted">{{ notification.createdAt.replace(/\T/, ' ') }}</small>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';

//axios
import { localAxios } from '@/util/http-commons';
const local = localAxios()

//pinia
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

const notifications = ref([]);

onMounted(async() => {
  try {
    const response = await local.get('/companion-board/comment/notice', {params: {'userId': authStore.user.id}})
    notifications.value = (response.data.comments)
  } catch(error) {
    console.log('댓글알림 가져오는 중에 오류 발생', error)
  }

})


const deleteNotification = async (commentId, userId) => {
  try{
    const response = await local.delete('', {'commentId': commentId, 'userId' : userId})
    notifications.value = (response.data.comments)
  }catch(error) {
    console.log('댓글 알림 삭제 중 오류 발생', error)
  }
}

</script>

<style scoped>
.list-group-item {
  transition: background-color 0.2s;
}

.list-group-item:hover {
  background-color: #f8f9fa;
}
</style>
