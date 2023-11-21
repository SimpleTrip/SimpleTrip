<script setup>
import CommentsList from '@/components/comments/CommentsList.vue';
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import { getArticle, deleteArticle } from '@/api/article.js';

const route = useRoute();
const router = useRouter();

const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)

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

const canClick = ref(false)

const checkOwner = function (article, isLogin, userInfo) {
  if (userInfo.value) canClick.value = (isLogin.value && (article.value.userId === userInfo.value.userId))
  else canClick.value = false
}

onMounted(() => {
  setArticle();

  watch([userInfo, article], () => {
    checkOwner(article, isLogin, userInfo)
  })
});
</script>

<template>
  <div class="container-fluid">
    <div class="row justify-content-center align-items-center">
      <div class="col-lg-7">
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
          <router-link v-if="canClick" :to="{ name: 'articleModify' }" class="btn btn-success btn-sm" style="margin: 4px">수정</router-link>
          <a v-if="canClick" @click="deleteHandler" class="btn btn-danger btn-sm" style="margin: 4px">삭제</a>
        </div>
      </div>
      <div class="col-lg-3">
        <CommentsList :articleId="articleId" />
      </div>
    </div>
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
