<script setup>
import BoardComment from "./item/BoardComment.vue";
import { detailArticle, deleteArticle } from "@/api/board";
import { ref, onMounted } from "vue";

//pinia
import { useAuthStore } from '@/stores/auth';
const authStore = useAuthStore();

//route
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

// const articleNo = ref(route.params.articleNo);
const { articleNo } = route.params;
const article = ref({});

onMounted(() => {
  getArticle();
});

const getArticle = async() => {
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
  router.push({ name: "article-modify", params: { "articleno": article.value.articleNo } });
}

function onDeleteArticle() {
  console.log(articleNo + "번글 삭제하러 가자!!!");

  if(confirm('정말 삭제하시겠습니까?\n등록된 댓글도 모두 삭제됩니다.')) {
    deleteArticle(
      articleNo,
      (response) => {
        if (response.status == 200) {
          alert('삭제가 완료되었습니다. 목록으로 이동합니다.')
          moveList();
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
</script>

<template>
  <div class="container" style="margin-top: 10%;">
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
              <img class="user-icon" v-if="authStore.user.img && authStore.user.id === article.userId" :src=authStore.user.img alt="">
              <img class="user-icon" v-else src="@/assets/userIcon.png" alt="">
              <p>
                <span class="fw-bold">{{article.userId}}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime?.replace(/T/, " ") || "등록 시간 없음" }} | 조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div class="col-md-4 align-self-center text-end">댓글 : 17</div>
          <div class="divider mb-3"></div>
          <div class="">
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
        <BoardComment :articleNo="articleNo" :userId="article.userId"/>
      </div>

    </div>
  </div>



</template>

<style scoped>
.user-icon{
  width: 40px;
  border-radius: 50%;
}
</style>
