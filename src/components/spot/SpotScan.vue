<script setup>
import { ref, onMounted } from 'vue';
import SpotSearch from './SpotSearch.vue';
import SpotFavorite from './SpotFavorite.vue';

const isSearch = ref(true);

function clickSearch() {
  isSearch.value = true;
}

function clickfavorite() {
  isSearch.value = false;
}
</script>

<template>
  <div>
    <section class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-lg-4 mx-auto">
            <div class="nav-wrapper position-relative end-0">
              <ul class="nav nav-pills nav-fill p-1" role="tablist">
                <li class="nav-item">
                  <a @click="clickSearch" class="nav-link mb-0 px-0 py-1 fw-bold active" data-bs-toggle="tab" role="tab" aria-selected="true" href="#search">여행지 검색</a>
                </li>
                <li class="nav-item">
                  <a @click="clickfavorite" class="nav-link mb-0 px-0 py-1 fw-bold" data-bs-toggle="tab" role="tab" aria-selected="false" href="#favorite"> 즐겨찾기 </a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </section>
    <div v-show="isSearch">
      <SpotSearch id="search"></SpotSearch>
    </div>
    <div v-show="!isSearch">
      <SpotFavorite id="favorite"></SpotFavorite>
    </div>
  </div>
</template>

<style scoped></style>
