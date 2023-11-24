<script setup>
import { ref, onMounted } from 'vue';
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import Pagination from '@/components/common/VPagination.vue';
import ArticleItem from '@/components/articles/ArticleItem.vue';
import Select from '@/components/common/VSelect.vue';
import { getArticleList } from '@/api/article.js';

const userStore = useUserStore()
const { isLogin } = storeToRefs(userStore)

const articleList = ref([]);
const totalPgno = ref(1);

const inputWord = ref('');
const params = ref({
  pgno: 1,
  word: '',
  key: 'all',
});

const setArticleList = function () {
  params.value.word = inputWord.value;
  getArticleList(params.value, ({ data }) => {
    articleList.value = data.articleList;
    totalPgno.value = data.totalPgno;
  });
};

onMounted(() => {
  setArticleList();
});

const clickPage = (curLabel) => {
  params.value.pgno = curLabel;
  params.value.word = inputWord.value;
  getArticleList(params.value, ({ data }) => {
    articleList.value = data.articleList;
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
  getArticleList(params.value, ({ data }) => {
    articleList.value = data.articleList;
    totalPgno.value = data.totalPgno;
  });
};
</script>

<template>
  <div class="container">
    <h2 class="text-center">글 목록</h2>
    <div class="d-flex justify-content-between">
      <div class="ms-2">
        <router-link v-if="isLogin" :to="{ name: 'articleWrite' }" class="btn btn-outline-success btn-md">글 작성</router-link>
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
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center col-2">글 번호</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center col-8">제목</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center col-2">작성자</th>
          </tr>
        </thead>
        <tbody>
          <article-item v-for="article in articleList" :key="article.articleId" :article="article"></article-item>
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
}</style>
