<template>
  <div class="comments-section">
    <hr class="my-4">
    <div class="mb-4" style="font-size: 150%;">댓글</div>

    <!-- 댓글 리스트 -->
    <div v-for="comment in comments" :key="comment.id" class="comment-item">
      <div class="comment-header d-flex justify-content-between align-items-center">
        <div class="d-flex align-items-center">
          <img :src="comment.img ? comment.img : 'src/assets/userIcon.png'"
            style="width: 25px; border-radius: 50%; margin-right: 15px" alt="">
          <strong class="pt-2">{{ comment.userId }}</strong>
          <template v-if="comment.userId === authStore.user.id">
            <div class="btn text-muted btn-sm ms-2" @click="comment.isEditing = true">수정</div>
            <div class="btn text-muted btn-sm" @click="deleteComment(comment.id)">삭제</div>
          </template>
        </div>
        <small class="text-muted ms-2">{{ comment.updatedAt.replace(/T/, " ") }}</small>
      </div>

      <div class="d-flex justify-content-between align-items-center">
        <!-- 댓글 내용: 수정 상태에 따라 표시 -->
        <div v-if="!comment.isEditing" style="margin-left: 40px;">
          {{ comment.content }}
        </div>
        <input v-else v-model="comment.content" class="form-control" style="margin-left: 40px; width: 80%;"
          @keyup.enter="modifyComment(comment.id, comment.content)" />

        <!-- 아코디언 토글 버튼 -->
        <button @click="toggleReplies(comment.id)">
          {{ expandedComments[comment.id] ? '댓글 숨기기' : '댓글 달기' }}
        </button>
      </div>

      <!-- 대댓글 컴포넌트 -->
      <BoardReplyComment 
        v-if="expandedComments[comment.id]"
        :replies="comment.replies"
        :parent-comment-id="comment.id"
        :article-no="articleNo"
        @update-replies="getComments"
      />
    </div>

    <!-- 댓글 입력 -->
    <div class="comment-input mb-3">
      <textarea v-model="newComment" placeholder="댓글을 입력하세요..." rows="3" class="form-control"
        @keyup.enter="submitComment"></textarea>
      <button class="btn btn-primary mt-2" @click="submitComment">작성</button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { detailComments } from "@/api/board";
import { localAxios } from "@/util/http-commons";
import { useAuthStore } from '@/stores/auth';
import BoardReplyComment from './BoardReplyComment.vue';

const local = localAxios();
const authStore = useAuthStore();

const props = defineProps({
  articleNo: Number,
  userId: String
});

const comments = ref([]);
const newComment = ref('');
const expandedComments = ref({});

onMounted(() => {
  getComments();
});

const getComments = () => {
  detailComments(
    props.articleNo,
    ({ data }) => {
      comments.value = data.comments;
      comments.value.forEach(async (comment) => {
        const response = await local.get(`/member/profile/image?userId=${comment.userId}`);
        comment.img = "http://localhost" + response.data.image;
        comment.isEditing = false;
      });
    },
    (error) => {
      console.log(error);
    }
  );
};

// 댓글 작성
const submitComment = async () => {
  if (!newComment.value.trim()) return alert('댓글을 입력하세요.');

  const commentData = {
    articleNo: props.articleNo,
    userId: authStore.user.id,
    content: newComment.value
  };

  try {
    await local.post('/companion-board/comment', commentData);
    getComments();
    newComment.value = '';
  } catch (error) {
    console.error('댓글 저장 실패:', error);
    alert('댓글 저장 중 문제가 발생했습니다.');
  }
};

// 댓글 수정
const modifyComment = async (commentId, content) => {
  const modifiedComment = comments.value.find(c => c.id === commentId);
  if (!modifiedComment.content.trim()) {
    alert('내용을 입력해주세요.');
    return;
  }

  try {
    await local.put(`/companion-board/comment/${commentId}`, {
      commentId: commentId,
      content: content
    });
    alert('댓글이 수정되었습니다.');
    modifiedComment.isEditing = false;
  } catch (error) {
    console.error('댓글 수정 실패:', error);
    alert('댓글 수정 중 문제가 발생했습니다.');
  }
};

// 댓글 삭제
const deleteComment = async (commentId) => {
  if (!confirm('정말 삭제하시겠습니까?')) {
    return;
  }
  try {
    await local.delete(`/companion-board/comment/${commentId}`);
    getComments();
  } catch (error) {
    console.log('댓글 삭제 실패: ', error);
    alert('댓글 삭제 중 문제가 발생했습니다.');
  }
};

// 대댓글 토글
const toggleReplies = (commentId) => {
  expandedComments.value[commentId] = !expandedComments.value[commentId];
};
</script>

<style scoped>
.comment-input textarea {
  width: 100%;
  resize: none;
}

.comment-list .comment-item {
  margin-bottom: 20px;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.comment-input {
  margin-top: 10px;
  display: flex;
  align-items: end;
  flex-direction: column;
}
</style>