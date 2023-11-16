<script setup>
import { ref, onMounted } from 'vue';
import Pagination from '@/components/common/Pagination.vue';
import ArticleItem from '@/components/articles/ArticleItem.vue';
import { getArticleList } from '@/api/article.js';

const articleList = ref([]);
const totalPgno = ref(1);

const setArticleList = function () {
  const params = { pgno: 1 };
  getArticleList(params, ({ data }) => {
    articleList.value = data.articleList;
    totalPgno.value = data.totalPgno;
  });
};

onMounted(() => {
  setArticleList();
});

const clickPage = (curLabel) => {
  const params = { pgno: curLabel };
  getArticleList(params, ({ data }) => {
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
        <router-link :to="{ name: 'articleWrite' }" class="btn btn-outline-success btn-md">글 작성</router-link>
      </div>
      <div class="d-flex">
        <div class="col-6">
          <div class="input-group input-group-outline">
            <label class="form-label"></label>
            <input type="text" id="text" class="form-control form-control-md" />
          </div>
        </div>
        <button class="btn btn-success btn-md ms-2">글 검색</button>
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
      <Pagination @click-page="clickPage" :totalPgno="totalPgno" />
    </div>
  </div>
</template>

<style scoped>
.current-page-text {
  color: white;
}
</style>
