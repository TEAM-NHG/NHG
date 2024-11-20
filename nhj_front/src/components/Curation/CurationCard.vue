<!-- src/components/Card.vue -->
<template>
  <div class="col-lg-4 col-md-6 col-xl-3">
    <div class="card h-100 shadow-sm" @click="openModal">
      <div class="image-slider position-relative">
        <img
          v-if="card.images && card.images.length"
          :src="card.images[currentImageIndex]"
          class="card-img-top"
          alt="card image"
          style="height: 200px; object-fit: cover;"
        >
        <img
          v-else
          src="@/assets/noImage.png"
          class="card-img-top"
          alt="no image"
          style="height: 200px; object-fit: cover;"
        >

        <!-- Navigation arrows -->
        <button
          v-if="card.images && card.images.length > 1"
          class="slider-btn prev"
          @click.stop="prevImage"
        >
          &#10094;
        </button>
        <button
          v-if="card.images && card.images.length > 1"
          class="slider-btn next"
          @click.stop="nextImage"
        >
          &#10095;
        </button>

        <!-- Image counter -->
        <div
          v-if="card.images && card.images.length > 1"
          class="image-counter"
        >
          {{ currentImageIndex + 1 }} / {{ card.images.length }}
        </div>
      </div>

      <div class="card-body">
        <h5 class="card-title">{{ card.title }}</h5>
        <p class="card-text">{{ card.addr1 }}</p>
        <div class="d-flex justify-content-between align-items-center">
          <small class="text-muted">{{ card.no }}</small>
        </div>
      </div>
    </div>

    <!-- Modal for detailed view -->
    <CurationModal :no="card.no" v-if="showModal" @closeModal="closeModal"/>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import CurationModal from './CurationModal.vue';

const props = defineProps(['card'])
const currentImageIndex = ref(0)
const showModal = ref(false)

const nextImage = () => {
  if (props.card.images && props.card.images.length > 1) {
    currentImageIndex.value = (currentImageIndex.value + 1) % props.card.images.length
  }
}

const prevImage = () => {
  if (props.card.images && props.card.images.length > 1) {
    currentImageIndex.value = currentImageIndex.value === 0
      ? props.card.images.length - 1
      : currentImageIndex.value - 1
  }
}

const openModal = () => (showModal.value = true)
const closeModal = () => (showModal.value = false)
</script>

<style scoped>
.card {
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-5px);
}

.modal {
  display: block;
  background: rgba(0, 0, 0, 0.5);
}

.image-slider {
  position: relative;
  overflow: hidden;
}

.slider-btn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  padding: 10px 15px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-radius:50%;
  font-size: 0.7rem;
}

.slider-btn:hover {
  background: rgba(0, 0, 0, 0.8);
}

.prev {
  left: 10px;
}

.next {
  right: 10px;
}

.image-counter {
  position: absolute;
  bottom: 10px;
  right: 10px;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 5px 10px;
  border-radius: 12px;
  font-size: 0.7rem;
}
</style>
