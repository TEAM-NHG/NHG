<template>
  <div class="replies ms-4 mt-2">
    <div v-for="reply in replies" :key="reply.replyId" class="reply-item">
      <div class="d-flex justify-content-between mb-2">
        <div class="d-flex align-items-center">
          <img :src="reply.image ? 'http://localhost' + reply.image : defaultUserIcon"
            style="width: 25px; border-radius: 50%; margin-right: 15px" alt="">
          <strong class="pt-2">{{ reply.nickname }}</strong>
          <template v-if="reply.userId === authStore.user.id">
            <div class="btn text-muted btn-sm ms-2" @click="reply.isEditing = true">수정</div>
            <div class="btn text-muted btn-sm" @click="handleDelete(reply.id)">삭제</div>
          </template>
        </div>
      </div>
      <div class="d-flex justify-content-between align-items-end">
        <div v-if="!reply.isEditing" style="margin-left: 40px;">
          {{ reply.content }}
        </div>
        <input v-else v-model="reply.content" class="form-control" style="margin-left: 40px; width: 80%;"
          @keyup.enter="handleModify(reply.id, reply.content)" />

        <small class="text-muted">{{ reply.createdAt.replace(/T/, " ") }}</small>
      </div>
    </div>

    <!-- 대댓글 작성 -->
    <div class="reply-section ms-4 d-flex flex-column align-items-end">
      <textarea v-model="newReplyContent" placeholder="같이 가볼까요?" rows="2" class="form-control mt-2"
        @keyup.enter="submitReply">
      </textarea>
      <button class="btn btn-secondary mt-1" @click="submitReply">댓글 작성</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { localAxios } from "@/util/http-commons";
import { useAuthStore } from '@/stores/auth';
import defaultUserIcon from '@/assets/userIcon.png';

const local = localAxios();
const authStore = useAuthStore();

const props = defineProps({
  replies: {
    type: Array,
    required: true
  },
  parentCommentId: {
    type: Number,
    required: true
  },
  articleNo: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['update-replies']);

const newReplyContent = ref('');

// 대댓글 작성
const submitReply = async () => {
  if (!newReplyContent.value?.trim()) return alert('댓글을 입력해주세요.');

  const replyData = {
    userId: authStore.user.id,
    articleNo: props.articleNo,
    content: newReplyContent.value,
    parentCommentId: props.parentCommentId,
  }

  try {
    await local.post('/companion-board/comment/child', replyData);
    newReplyContent.value = '';
    emit('update-replies'); // 부모 컴포넌트에 업데이트 요청
  } catch (error) {
    console.error('대댓글 저장 실패:', error);
    alert('대댓글 저장 중 문제가 발생했습니다.');
  }
}

// 대댓글 수정
const handleModify = async (commentId, content) => {
  if (!content.trim()) {
    alert('내용을 입력해주세요.');
    return;
  }

  try {
    await local.put(`/companion-board/comment/${commentId}`, {
      commentId: commentId,
      content: content
    });
    alert('댓글이 수정되었습니다.');
    emit('update-replies'); // 부모 컴포넌트에 업데이트 요청
  } catch (error) {
    console.error('댓글 수정 실패:', error);
    alert('댓글 수정 중 문제가 발생했습니다.');
  }
};

// 대댓글 삭제
const handleDelete = async (commentId) => {
  if (!confirm('정말 삭제하시겠습니까?')) {
    return;
  }

  try {
    await local.delete(`/companion-board/comment/${commentId}`);
    emit('update-replies'); // 부모 컴포넌트에 업데이트 요청
  } catch (error) {
    console.log('댓글 삭제 실패: ', error);
    alert('댓글 삭제 중 문제가 발생했습니다.');
  }
}
</script>

<style scoped>
.reply-section textarea {
  width: 100%;
  resize: none;
}

.replies .reply-item {
  padding-left: 15px;
  margin-top: 10px;
  border-left: 2px solid #ddd;
}

.replies {
  transition: max-height 0.3s ease-in-out, opacity 0.3s ease-in-out;
  overflow: hidden;
}
</style>
