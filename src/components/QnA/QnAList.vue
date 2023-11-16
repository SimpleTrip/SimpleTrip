<script setup>
import { ref, onMounted } from 'vue';
import { list } from '@/api/QnA.js';
import QnAItem from '@/components/QnA/QnAItem.vue';
import Pagination from '@/components/common/Pagination.vue';

const qnaList = ref([]);
const totalPgno = ref(1);

const getList = () => {
  const params = { pgno: 1 };
  list(params, ({ data }) => {
    qnaList.value = data.qnaList;
    totalPgno.value = data.totalPgno;
  });
};

onMounted(() => {
  getList();
});

const clickPage = (curLabel) => {
  const params = { pgno: curLabel };
  list(params, ({ data }) => {
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
        <router-link :to="{ name: 'QnAWrite' }" class="btn btn-outline-success btn-md">Write</router-link>
      </div>
      <div class="d-flex">
        <div class="col-6">
          <div class="input-group input-group-outline">
            <label class="form-label"></label>
            <input type="text" id="text" class="form-control form-control-md" />
          </div>
        </div>
        <button class="btn btn-success btn-md ms-2 me-2">Search</button>
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
      <Pagination @click-page="clickPage" :totalPgno="totalPgno" />
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
</style>
