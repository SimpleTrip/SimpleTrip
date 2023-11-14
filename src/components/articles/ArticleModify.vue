<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getArticle, modifyArticle } from '@/api/article.js';

const route = useRoute();
const router = useRouter();

const article = ref({});
const articleId = ref(route.params.articleId);

const setArticle = function () {
  getArticle(articleId.value, ({ data }) => {
    article.value = data;
  });
};

const modifyHandler = function () {
  const response = confirm('수정하시겠습니까?');
  if (response) {
    modifyArticle(articleId.value, article.value);
    alert('수정 완료');
    router.push({ name: 'articleList' });
  } else {
    alert('수정 실패');
  }
};

const resetArticle = function () {
  article.value.articleTitle = '';
  article.value.articleContent = '';
  article.value.userId = '';
};

onMounted(() => {
  setArticle();
});
</script>

<template>
  <h2 class="text-center">글 수정</h2>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>작성자</h5>
        <div class="input-group input-group-outline">
          <label class="form-label"></label>
          <input readonly type="text" id="text" class="form-control form-control-md" v-model="article.userId" />
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>제목</h5>
        <div class="input-group input-group-outline">
          <label class="form-label"></label>
          <input type="text" id="text" class="form-control form-control-md" v-model="article.articleTitle" />
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>내용</h5>
        <div class="input-group input-group-outline">
          <label class="form-label"></label>
          <textarea v-model="article.articleContent" class="text-area form-contol form-control-md col-12" rows="5"></textarea>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <router-link :to="{ name: 'articleList' }" class="btn btn-secondary btn-md">목록으로 돌아가기</router-link>
    <a @click="modifyHandler" class="btn btn-success btn-md">수정</a>
    <button @click="resetArticle" class="btn btn-outline-success btn-md">초기화</button>
  </div>
</template>

<style scoped>
.text-area {
  background: none;
  border: 1px solid #d2d6da;
  border-radius: 0.375rem;
  border-top-left-radius: 0.375rem !important;
  border-bottom-left-radius: 0.375rem !important;
  padding: 0.625rem 0.75rem !important;
  line-height: 1.3 !important;
}
</style>
