<script setup></script>

<template>
  <div class="outerBox">
    <router-view></router-view>
  </div>
</template>

<style>
mark.sky {
  background: linear-gradient(to top, #d0d0ce 20%, transparent 30%);
}

.outerBox{
  background-color: #191a1c;
  width: 100vw;
  height: 100vh;
}
</style>
