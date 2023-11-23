<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';

import { useUserStore } from '@/stores/user'
import { getArticle, modifyArticle } from '@/api/article.js';
import { refresh } from '@/util/tokenUtil'

const route = useRoute();
const router = useRouter();

const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)
const { cookies } = useCookies()

const article = ref({});
const articleId = ref(route.params.articleId);

const resetAuth = () => {
  cookies.remove('refreshToken', '/', 'localhost');
  cookies.remove('accessToken', '/', 'localhost');
  isLogin.value = false;
  userInfo.value = {};
  router.replace({ name: 'login' });
};

const setArticle = function () {
  getArticle(
    articleId.value,
    (success) => {
      article.value = success.dataBody
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh()
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody
            await getArticle(
              articleId.value,
              (success) => {
                article.value = success.dataBody
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage)
                router.push({ name: 'articleList'})
              }
            )
          } else {
            resetAuth()
            alert(fail.dataHeader.resultMessage)
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage)
        router.push({ name: 'articleList'})
      }
    },
  )
};

const modifyHandler = function () {
  modifyArticle(articleId.value, article.value,
    // success
    () => {
      alert('글이 수정 되었습니다.');
      router.push({ name: 'articleList' });
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == ' UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await modifyArticle(
              articleId.value, article.value,
              // success
              () => {
                alert('글이 수정 되었습니다.');
                router.push({ name: 'articleList' });
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage);
                router.go(-1);
              }
            );
          } else {
            resetAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage);
        router.go(-1);
      }
    }
  );
};

const resetArticle = function () {
  article.value.articleTitle = '';
  article.value.articleContent = '';
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
          <textarea v-model="article.articleContent" class="text-area form-contol form-control-md col-12"
            rows="5"></textarea>
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
