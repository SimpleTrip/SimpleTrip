<script setup>
import CommentsList from '@/components/comments/CommentsList.vue';
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getArticle, deleteArticle } from '@/api/article.js';

const route = useRoute();
const router = useRouter();

const article = ref({});
const articleId = ref(route.params.articleId);

const setArticle = function () {
  getArticle(articleId.value, ({ data }) => {
    article.value = data;
  });
};

const deleteHandler = function () {
  const response = confirm('삭제하시겠습니까?');
  if (response) {
    deleteArticle(article.value.articleId);
    alert('삭제 완료');
    router.push({ name: 'articleList' });
  } else {
    alert('삭제 실패');
  }
};

onMounted(() => {
  setArticle();
});
</script>

<template>
  <div class="d-flex container justify-content-between align-items-center">
    <div class="container" style="width: 70%">
      <div class="d-flex justify-content-between align-items-center container gap-8 mb-2">
        <h3>{{ article.articleId }}. {{ article.articleTitle }}</h3>
      </div>
      <div class="justify-content-between container mb-2">
        <div class="justify-content-center gap-2">
          <div class="d-flex flex-column">
            <h5>작성자</h5>
            <div class="input-group input-group-outline form-control form-control-md">
              <div class="col form-control form-control-md border rounded">
                {{ article.userId }}
              </div>
            </div>
          </div>
        </div>
        <div class="justify-content-center gap-2">
          <div class="d-flex flex-column">
            <h5>내용</h5>
            <div class="input-group input-group-outline">
              <label class="form-label"></label>
              <textarea readonly class="text-area form-control form-control-md col-12" v-model="article.articleContent" rows="14"></textarea>
            </div>
          </div>
        </div>
      </div>

      <div class="d-flex justify-content-center align-items-center">
        <router-link :to="{ name: 'articleList' }" class="btn btn-secondary btn-sm" style="margin: 4px">목록으로 돌아가기</router-link>
        <router-link :to="{ name: 'articleModify' }" class="btn btn-success btn-sm" style="margin: 4px">수정</router-link>
        <a @click="deleteHandler" class="btn btn-danger btn-sm" style="margin: 4px">삭제</a>
      </div>
    </div>
    <CommentsList style="width: 30%" :articleId="articleId" />
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
