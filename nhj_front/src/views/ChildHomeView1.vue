<template>
    <div class="home-container">
        <div class="text-position w-50 text-center">
          <div>
            <div class="mb-4">
              <!-- <div>안녕하세요 {{ authStore.user.nickname }}님</div> -->
              <div class="Main-text mb-3">나만의 속도, 나만의 여정</div>
            </div>
          </div>
        </div>
        <video src="@/assets/Main/nhg03-27-50.mp4" autoplay muted
            alt="Main Background"
            class="w-50 main-image"
            style="height: 100vh; object-fit: cover;">
        </video>
    </div>
</template>

<script setup>

</script>

<style scoped>
.home-container {
  position: relative;
  height: 100vh;
  background-color: rgb(0, 0, 0);
  color: white;
}

.main-image {
  position: absolute;
  width: 100%;
}

.Main-text {
  font-size: 250%;
}

.text-position {
  position: absolute;
  top: 42vh;
  bottom: auto;
  right: auto;
  left: 50%;
}
</style>