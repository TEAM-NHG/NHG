<script setup>
import Comment from "./item/comment.vue";
import { detailArticle, deleteArticle } from "@/api/board";
import { ref, onMounted } from "vue";

//pinia
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

//route
import { useRoute, useRouter } from "vue-router";

//axios
import { localAxios } from "@/util/http-commons"
const local = localAxios()

const route = useRoute();
const router = useRouter();

// const articleNo = ref(route.params.articleNo);
const { articleNo } = route.params;
const article = ref({});
const comments = ref([])

onMounted(async() => {
  getArticle();

  //댓글 불러오는 부분
  try {
    const articleResponse = await local.get(`/api/articles/${articleNo.value}`)
    article.value = articleResponse.data

    const commentsResponse = await local.get(`/api/comments/${articleNo.value}`)
    comments.value = commentsResponse.data
  } catch (error) {
    console.error('게시글 또는 댓글 가져오기 실패:', error)
  }
});

const getArticle = () => {
  // const { articleNo } = route.params;
  console.log(articleNo + "번글 얻으러 가자!!!");
  detailArticle(
    articleNo,
    ({ data }) => {
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

function moveList() {
  router.push({ name: "article-list" });
}

function moveModify() {
  router.push({ name: "article-modify", params: { articleNo } });
}

function onDeleteArticle() {
  // const { articleNo } = route.params;
  console.log(articleNo + "번글 삭제하러 가자!!!");
  deleteArticle(
    articleNo,
    (response) => {
      if (response.status == 200) moveList();
    },
    (error) => {
      console.log(error);
    }
  );
}
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">{{ article.articleNo }}. {{ article.subject }}</h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{article.userId}}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }}1 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-outline-primary mb-3" @click="moveList">
              글목록
            </button>
            <button type="button" class="btn btn-outline-success mb-3 ms-1" @click="moveModify" v-if="authStore.user.id === article.userId">
              글수정
            </button>
            <button type="button" class="btn btn-outline-danger mb-3 ms-1" @click="onDeleteArticle" v-if="authStore.user.id === article.userId">
              글삭제
            </button>
          </div>
        </div>
      </div>
      <!-- 댓글 컴포넌트 -->
      <div class="col-lg-10">
        <Comment :articleNo="articleNo" :initialComments="comments"/>
      </div>
    </div>
  </div>

  

</template>

<style scoped></style>
