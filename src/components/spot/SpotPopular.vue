<script setup>
import { ref, onMounted } from 'vue';
import { getPopularSpotList } from '@/api/spot.js';

const user = ref({
  userSex: 'F',
  userAge: '24',
});
const popularSpotList = ref([]);

function searchSpot(spotName) {
  console.log(`https://search.daum.net/search?q=${spotName}`);
  window.open(`https://search.daum.net/search?q=${spotName}`);
}

onMounted(() => {
  let userInfo = {};
  userInfo.userSex = user.value.userSex;
  userInfo.userAge = user.value.userAge;

  getPopularSpotList(userInfo, ({ data }) => {
    for (const popularSpot of data) {
      popularSpotList.value.push(popularSpot);
    }
  });
});
</script>

<template>
  <div class="container">
    <h2 class="text-center mb-5">추천 여행지</h2>
    <div class="row">
      <div v-for="popularSpot in popularSpotList" :key="popularSpot.spotId" class="col-md-6 mx-auto mt-4">
        <div class="card mt-5">
          <div class="card-header p-0 mx-3 mt-n4 position-relative z-index-2">
            <a class="d-block blur-shadow-image">
              <img :src="popularSpot.spotImgUrl" alt="img-blur-shadow" class="img-fluid border-radius-lg" loading="lazy" />
            </a>
          </div>
          <div class="card-body pt-3">
            <h5>{{ popularSpot.spotName }}</h5>
            <p>{{ popularSpot.spotAddr }}</p>
            <button class="btn btn-sm mb-0 btn-success"><a class="text-white" :href="'https://search.daum.net/search?q=' + popularSpot.spotName" target="_blank">검색</a></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
