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

//axios
import { localAxios } from "@/util/http-commons";
const local = localAxios();

onMounted(() => {
  getArticle();
});

const getArticle = async() => {

  try {
    const response = await local.get(`/companion-board/${articleNo}`)
    article.value = response.data;
    article.value.image = "http://localhost" + response.data.image
  }catch(error){
    console.log('글 상세 확인중 에러 발생', error)
  }
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
        <div class="my-2 d-flex justify-content-between align-items-end">
          <div style="font-size: 150%;">| 제목: {{ article.subject }}</div>

          <div class="text-end text-secondary fw-light">
            {{ article.registerTime?.replace(/T/, " ") || "등록 시간 없음" }} | 조회 : {{ article.hit }}    
          </div>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="d-flex align-items-center mb-2">
              <img class="user-icon me-3" :src="article.image ? article.image : '@/assets/userIcon.png'">
              <div>{{article.userId}}</div>
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
