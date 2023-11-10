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
            <!-- <h1 class="text-white pt-3 mt-n5 me-2" :style="{ display: 'inline-block ' }">Material Kit 2</h1>
            <p class="lead text-white px-5 mt-3" :style="{ fontWeight: '500' }">Start the Development With Bootstrap 5 Design System inspired by Material Design.</p> -->
          </div>
        </div>
      </div>
    </div>
  </ImgHeader>
</template>

<style scoped></style>
