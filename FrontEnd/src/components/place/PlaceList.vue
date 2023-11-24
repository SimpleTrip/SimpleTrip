<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'

import Pagination from '@/components/common/VPagination.vue'
import PlaceItem from '@/components/place/PlaceItem.vue'
import PlaceSelect from '@/components/place/PlaceSelect.vue'

import { getPlaceList } from '@/api/place.js'

const router = useRouter()

const placeList = ref([]);
const placeListPageCount = ref(1);
// const totalPgno = ref(1);

const inputWord = ref('');
const listInfo = ref({
  pgno: 1,
  key: 'all',
  word: '',
});

const routePlace = function (placeId) {
  router.push({ name: 'placeDetail', params: { placeId } })
}

const setPlaceList = function () {
  listInfo.value.word = inputWord.value;
  getPlaceList(listInfo.value, ({ data }) => {
    placeList.value = data.placeList
    // totalPgno.value = data.totalPgno;
    placeListPageCount.value = data.placeListPageCount
  });
};

onMounted(() => {
  setPlaceList();
});

// const clickPage = (curLabel) => {
//   params.value.pgno = curLabel;
//   params.value.word = inputWord.value;
//   getArticleList(params.value, ({ data }) => {
//     articleList.value = data.articleList;
//     totalPgno.value = data.totalPgno;
//   });
// };

const clickPage = (currentPage) => {
  listInfo.value.pgno = currentPage
  listInfo.value.word = inputWord.value
  getPlaceList(listInfo.value, ({ data }) => {
    placeList.value = data.placeList
    placeListPageCount.value = data.placeListPageCount
  })
}

const clickSearch = () => {
  // params.value.pgno = 1;
  listInfo.value.pgno = 1
  listInfo.value.word = inputWord.value;
  getPlaceList(listInfo.value, ({ data }) => {
    placeList.value = data.placeList;
    // totalPgno.value = data.totalPgno;
    placeListPageCount.value = data.placeListPageCount
  });
};

const selectedChange = (selectedValue) => {
  listInfo.value.key = selectedValue;
  listInfo.value.word = inputWord.value;
};
</script>

<template>
  <div class="container">
    <h2 class="text-center">장소 목록</h2>
    <div class="d-flex justify-content-between">
      <div class="ms-2">
        <router-link :to="{ name: 'placeRegister' }" class="btn btn-outline-success btn-md">장소 등록</router-link>
      </div>
      <div class="d-flex align-items-center justify-content-between" style="width: 50%">
        <place-select style="width: 30%" @selected-change="selectedChange"></place-select>
        <div class="input-group input-group-outline">
          <input type="text" id="text" class="form-control form-control-md" style="width: 60%" v-model="inputWord" />
          <i class="searchBtn fas fa-search" style="" @click="clickSearch"></i>
        </div>
      </div>
    </div>

    <div class="table-responsive">
      <table class="table align-items-center mb-0 table-hover">
        <thead>
          <tr>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">장소 번호</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">장소 정보</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">장소 유형</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">방문 날짜</th>
          </tr>
        </thead>
        <tbody>
          <place-item v-for="place in placeList" :key="place.placeId" :place="place"
            @click="() => routePlace(place.placeId)"></place-item>
        </tbody>
      </table>
      <br />
      <Pagination @click-page="clickPage" :totalPgno="placeListPageCount" :pgno="listInfo.page" />
    </div>
  </div>
</template>

<style scoped>
.current-page-text {
  color: white;
}

.searchBtn {
  display: flex;
  font-size: 16px;
  justify-content: center;
  align-items: center;
  padding: 10px;
  background-color: #4caf50;
  color: white;
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  width: 10%;
}

.searchBtn:hover {
  background-color: #40a027;
}
</style>
