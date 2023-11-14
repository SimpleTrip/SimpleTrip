<script setup>
import { ref, onMounted } from 'vue';

import ArticleItem from '@/components/articles/ArticleItem.vue';

import { getArticleList } from '@/api/article.js';

const articleList = ref([]);

const currentPage = ref(2);
const isCurrentPage = function (page) {
  return currentPage.value == page;
};

const setArticleList = function () {
  getArticleList(({ data }) => {
    articleList.value = data;
  });
};

onMounted(() => {
  setArticleList();
});
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
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center col-2">작성자</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center col-8">내용</th>
          </tr>
        </thead>
        <tbody>
          <article-item v-for="article in articleList" :key="article.articleId" :article="article"></article-item>
        </tbody>
      </table>
      <br />
      <div class="d-flex justify-content-center">
        <ul class="pagination pagination-success">
          <li class="page-item">
            <a class="page-link" href="javascript:;"><i class="fa fa-angle-double-left" aria-hidden="true"></i></a>
          </li>
          <li :class="{ 'page-item': true, active: isCurrentPage(page) }" v-for="page in 5" :key="page">
            <a class="page-link" :class="{ 'current-page-text': isCurrentPage(page) }" href="javascript:;">{{ page }}</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="javascript:;"><i class="fa fa-angle-double-right" aria-hidden="true"></i></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.current-page-text {
  color: white;
}
</style>
