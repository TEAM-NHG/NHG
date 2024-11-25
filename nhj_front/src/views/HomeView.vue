<template>
  <div v-if="!authStore.user" class="home-container scroll-container" style="height: 100vh;">
    <div class="scroll-area"><ChildHomeView1/></div>
    <div class="scroll-area"><ChildHomeView2/></div>
    <div class="scroll-area"><ChildHomeView3/></div>
    <div class="scroll-area"><ChildHomeView4/></div>
    <div class="scroll-area"><ChildHomeView5/></div>
    <div class="scroll-area"><ChildHomeView6/></div>
    <font-awesome-icon :icon="['fas', 'angles-down']" beat-fade 
                        style="color: #ffffff; position: fixed; bottom: 10vh; width: 100%;"/>
  </div>

  <div v-else>
    <div class="scroll-area"><ChildHomeView6/></div>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import ChildHomeView1 from './HomeChild/ChildHomeView1.vue';
import ChildHomeView2 from './HomeChild/ChildHomeView2.vue';
import ChildHomeView3 from './HomeChild/ChildHomeView3.vue';
import ChildHomeView4 from './HomeChild/ChildHomeView4.vue';
import ChildHomeView5 from './HomeChild/ChildHomeView5.vue';
import ChildHomeView6 from './HomeChild/ChildHomeView6.vue';

const authStore = useAuthStore()

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
