<script setup>
import { RouterView } from 'vue-router';
import DefaultNav from '@/components/headers/DefaultNav.vue';
import DefaultFooter from '@/components/footers/DefaultFooter.vue';
</script>

<template>
  <div class="container position-sticky z-index-sticky top-0">
    <div class="row">
      <div class="col-12">
        <DefaultNav :sticky="true" />
      </div>
    </div>
  </div>
  <RouterView />
  <DefaultFooter />
</template>

<style scoped></style>
