<script setup>
import CommentsList from '@/components/comments/CommentsList.vue';
import { ref, onMounted, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';

import { useUserStore } from '@/stores/user'
import { getArticle, deleteArticle } from '@/api/article.js';
import { refresh } from '@/util/tokenUtil';

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
                router.go(-1)
              }
            )
          } else {
            resetAuth()
            alert(fail.dataHeader.resultMessage)
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage)
        router.go(-1)
      }
    },
  )
}

const deleteHandler = function () {
  deleteArticle(
    article.value.articleId, article.value.userId,
    // success
    () => {
      router.replace({ name: 'articleList'})
    },
    async (fail) => {
      console.log('@vue', article.value.articleId, article.value.userId)
      console.log(fail)
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh()
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody
            await deleteArticle(
              article.value.articleId, article.value.userId,
              // success
              () => {
                router.push({ name: 'articleList'})
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
        router.replace({ name: 'articleList'})
      }
    },
  );
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
  },
    { immediate: true })
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
                <textarea readonly class="text-area form-control form-control-md col-12" v-model="article.articleContent"
                  rows="14"></textarea>
              </div>
            </div>
          </div>
        </div>

        <div class="d-flex justify-content-center align-items-center">
          <router-link :to="{ name: 'articleList' }" class="btn btn-secondary btn-sm" style="margin: 4px">목록으로
            돌아가기</router-link>
          <router-link v-if="canClick" :to="{ name: 'articleModify' }" class="btn btn-success btn-sm"
            style="margin: 4px">수정</router-link>
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
}</style>
