<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky" style="font-size: 125%;">글쓰기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <BoardFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.container{
  background-color: white;
  margin-top: 8vh;
  padding: 3vh 12vh;
  height: 92vh;
  border-radius: 25px;
  width: 60vw;
  font-size: 110%;
}
</style>
