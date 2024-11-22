<template>
  <div class="comments-section">
    <hr class="my-4">
    <div style="font-size: 150%;">댓글</div>

    <!-- 댓글 리스트 -->
    <div class="comment-list">
      <div v-for="comment in comments" :key="comment.id" class="comment-item">
        <div class="comment-header d-flex justify-content-between align-items-center">
          <div>
            <p></p><strong>{{comment.id}} {{ comment.userId }}</strong>
            <small class="text-muted ms-2">{{ comment.updatedAt.replace(/T.*/, "") }}</small>
          </div>
        </div>
        <p>{{ comment.content }}</p>


        <!-- 대댓글 리스트 -->
        <div v-if="comment.replies.length" class="replies ms-4 mt-2">
          <div v-for="reply in comment.replies" :key="reply.replyId" class="reply-item">
            <div class="d-flex justify-content-between">
              <strong>{{ reply.userId }}</strong>
              <small class="text-muted">{{ reply.createdAt.replace(/T.*/, "") }}</small>
            </div>
            <p>{{ reply.content }}</p>
          </div>
        </div>

        <!-- 대댓글 작성 -->
        <div class="reply-section ms-4">
          <textarea v-model="replyContent[comment.id]" placeholder="같이 가볼까요?" rows="2"
            class="form-control" @keyup.enter="submitReply(comment.id)"></textarea>
          <button class="btn btn-secondary mt-1" @click="submitReply(comment.id)">댓글 작성</button>
        </div>
      </div>
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
import { onMounted, ref } from 'vue'
import { detailComments } from "@/api/board";

//axios
import { localAxios } from "@/util/http-commons"
const local = localAxios()

// props로 댓글 데이터 받기
const props = defineProps({
  articleNo: Number,       // 게시글 번호
  userId: String
})

const comments = ref([]) // 댓글 데이터
const newComment = ref('') // 새로운 댓글 입력 상태
const replyContent = ref({}) // 대댓글 입력 상태

onMounted(() => {
  getComments();
})

const getComments = async () => {
  detailComments(
    props.articleNo,
    ({ data }) => {
      comments.value = data.comments;
    },
    (error) => {
      console.log(error);
    }
  );
}

// 댓글 작성
const submitComment = async () => {
  if (!newComment.value.trim()) return alert('댓글을 입력하세요.')

  const commentData = {
    articleNo: props.articleNo,
    userId: props.userId, // 실제 유저 ID는 백엔드 세션으로 관리
    content: newComment.value
  }

  try {
    await local.post('/companion-board/comment', commentData)
    getComments(); // DB 저장 후 새로운 댓글 추가
    newComment.value = ''
  } catch (error) {
    console.error('댓글 저장 실패:', error)
    alert('댓글 저장 중 문제가 발생했습니다.')
  }
}

// 대댓글 작성
const submitReply = async (commentId) => {
  if (!replyContent.value[commentId]?.trim()) return alert('대댓글을 입력하세요.')

  const replyData = {
    userId: props.userId, // 실제 유저 ID는 백엔드 세션으로 관리
    articleNo: props.articleNo,
    content: replyContent.value[commentId],
    parentCommentId: commentId,
  }

  try {
    await local.post('/companion-board/comment/child', replyData)
    // const parentComment = comments.value.find(comment => comment.commentId === commentId)
    // parentComment.replies.push(response.data) // DB 저장 후 대댓글 추가
    getComments();
    replyContent.value[commentId] = ''
  } catch (error) {
    console.error('대댓글 저장 실패:', error)
    alert('대댓글 저장 중 문제가 발생했습니다.')
  }
}
</script>

<style scoped>
.comment-input textarea,
.reply-section textarea {
  width: 100%;
  resize: none;
}

.comment-list .comment-item {
  margin-bottom: 20px;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.reply-section, .comment-input {
  margin-top: 10px;
  display: flex;
  align-items: end;
  flex-direction: column;
}

.replies .reply-item {
  padding-left: 15px;
  margin-top: 10px;
  border-left: 2px solid #ddd;
}
</style>
