<template>
  <div class="home-container scroll-container" style="height: 100vh;">
    <div class="scroll-area"><ChildHomeView1/></div>
    <div class="scroll-area"><ChildHomeView2/></div>
    <div class="scroll-area"><ChildHomeView3/></div>
    <div class="scroll-area"><ChildHomeView4/></div>
    <div class="scroll-area"><ChildHomeView5/></div>
  </div>
</template>

<script setup>
import ChildHomeView1 from './ChildHomeView1.vue';
import ChildHomeView2 from './ChildHomeView2.vue';
import ChildHomeView3 from './ChildHomeView3.vue';
import ChildHomeView4 from './ChildHomeView4.vue';
import ChildHomeView5 from './ChildHomeView5.vue';
</script>
<style scoped>
.home-container {
  position: relative;
  height: 100vh;
}

.Main-text {
  font-size: 300%;
}

.text-position {
  position: absolute;
  top: 42vh;
  bottom: auto;
  right: auto;
  left: 50%;
}

/* 부모 스크롤 스냅 컨테이너 */
.scroll-container {
  overflow: auto;
  scroll-snap-type: y mandatory; /* y 축 방향으로만 scroll snap 적용 */
}

/* 자식 스크롤 스냅 영역 */
.scroll-area {
  scroll-snap-align: start; /* 스크롤 위치 맞춤 */
  height:100vh;
}
</style>
