<script setup>
import { onMounted, onUnmounted } from 'vue';
import ImgHeader from '@/components/headers/ImgHeader.vue';
import TripImg from '@/assets/trip.avif';
const body = document.getElementsByTagName('body')[0];
onMounted(() => {
  body.classList.add('presentation-page');
  body.classList.add('bg-gray-200');
});
onUnmounted(() => {
  body.classList.remove('presentation-page');
  body.classList.remove('bg-gray-200');
});
</script>

<template>
  <ImgHeader
    ><div class="page-header min-vh-75" :style="`background-image: url(${TripImg})`" loading="lazy">
      <div class="container">
        <div class="row">
          <div class="col-lg-7 text-center mx-auto position-relative">
            <h1 class="text-white pt-3 mt-n5 me-2" :style="{ display: 'inline-block ' }">ENJOY TRIP WITH SIMPLE TRIP</h1>
            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">SIMPLE TRIP과 함께 즐거운 여행을 도전해보세요!</p>
          </div>
        </div>
      </div>
    </div>
  </ImgHeader>
</template>

<style scoped></style>
