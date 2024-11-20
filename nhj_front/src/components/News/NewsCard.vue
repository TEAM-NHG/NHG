<template>
  <div class="col-md-12 mb-3">
    <a :href="card.link" target="_blank">
      <div
        class="card p-3 shadow-sm"
        :style="cardStyle"
        @mouseenter="hover = true"
        @mouseleave="hover = false"
      >
        <h6 class="mb-3 text-primary">{{ card.bloggername }}</h6>
        <h4 class="mb-2">{{ stripHtml(card.title) }}</h4>
        <p>{{ stripHtml(card.description) }}</p>
          <small class="text-muted d-flex justify-content-end">{{ new Date(card.createdDate).toISOString().split('T')[0] }}</small>
      </div>
    </a>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";

defineProps(["card"]);

const opacity = ref(0);
const hover = ref(false);

const cardStyle = computed(() => ({
  opacity: hover.value ? 1 : opacity.value,
  transform: hover.value ? "scale(1.02)" : "scale(1)",
  transition: "all 0.3s ease",
}));

onMounted(() => {
  setTimeout(() => {
    opacity.value = 1; // 나타나는 효과
  }, 100); // 100ms 지연 후 나타남
});

//정규식으로 html 지우기
const stripHtml = (htmlString) => {
  return htmlString.replace(/<\/?[^>]+(>|$)/g, "");
};
</script>

<style scoped>
.card {
  background-color: #fffbe6;
  border: 1px solid #f5f5dc;
  transition: transform 0.2s;
}

a{
  text-decoration-line: none;
}

*{
  font-size: 98%;
}
</style>
