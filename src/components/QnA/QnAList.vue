<script setup>
import { ref, onMounted } from 'vue';
import { list } from '@/api/QnA.js';
import QnAItem from '@/components/QnA/QnAItem.vue';
import Pagination from '@/components/common/VPagination.vue';
import Select from '@/components/common/VSelect.vue';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);

const qnaList = ref([]);
const totalPgno = ref(1);

const inputWord = ref('');
const params = ref({
  pgno: 1,
  word: '',
  key: 'all',
});

const getList = () => {
  params.value.word = inputWord.value;
  list(params.value, ({ data }) => {
    qnaList.value = data.qnaList;
    totalPgno.value = data.totalPgno;
  });
};

onMounted(() => {
  getList();
  console.log(userInfo.value);
});

const clickPage = (curLabel) => {
  params.value.pgno = curLabel;
  params.value.word = inputWord.value;
  list(params.value, ({ data }) => {
    qnaList.value = data.qnaList;
    totalPgno.value = data.totalPgno;
  });
};

const selectedChange = (selectedValue) => {
  params.value.key = selectedValue;
  params.value.word = inputWord.value;
};

const clickSearch = () => {
  params.value.pgno = 1;
  params.value.word = inputWord.value;
  list(params.value, ({ data }) => {
    qnaList.value = data.qnaList;
    totalPgno.value = data.totalPgno;
  });
};
</script>

<template>
  <div class="container">
    <h2 class="text-center">QnA</h2>
    <div class="d-flex justify-content-between">
      <div class="ms-2">
        <router-link :to="{ name: 'QnAWrite' }" class="btn btn-outline-success btn-md" style="display: flex; align-items: center; justify-content: center; margin: 0px">Write</router-link>
      </div>
      <div class="d-flex align-items-center justify-content-between" style="width: 50%">
        <Select style="width: 30%" @selected-change="selectedChange" />
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
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">NO</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">QnA</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">AUTHOR</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">ANS</th>
          </tr>
        </thead>
        <tbody>
          <QnAItem v-for="qna in qnaList" :key="qna.qnaNo" :qna="qna" />
        </tbody>
      </table>
      <br />
      <Pagination @click-page="clickPage" :totalPgno="totalPgno" :pgno="params.pgno" />
    </div>
  </div>
</template>

<style scoped>
.current-page-text {
  color: white;
}
.page {
  display: flex;
  justify-content: center;
  align-items: center;
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
