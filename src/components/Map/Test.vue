<script setup>
import { ref, onMounted } from 'vue';
import { getSido } from '@/api/Map.js';

const sido = ref({});
onMounted(() => {
  getSidoObj();
});

const getSidoObj = () => {
  getSido(({ data }) => {
    sido.value = data;
    console.log(sido.value);
  });
};
</script>

<template>
  <div></div>
</template>

<style scoped></style>
