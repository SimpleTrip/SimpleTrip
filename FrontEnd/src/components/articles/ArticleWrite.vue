<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';
import { useUserStore } from '@/stores/user'
import { writeArticle } from '@/api/article.js';
import { refresh } from '@/util/tokenUtil'


const userStore = useUserStore()
const { isLogin, userInfo } = storeToRefs(userStore)
const { cookies } = useCookies()

const router = useRouter();

const resetAuth = () => {
  cookies.remove('refreshToken', '/', 'localhost');
  cookies.remove('accessToken', '/', 'localhost');
  isLogin.value = false;
  userInfo.value = {};
  router.replace({ name: 'login' });
};

const article = ref({
  articleTitle: '',
  articleContent: '',
  userId: '',
});

const writeHandler = function () {
  writeArticle(
    article.value,
    // success
    () => {
      router.push({ name: 'articleList' })
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh()
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody
            await writeArticle(
              article.value,
              // success
              () => {
                alert('글 등록 완료!')
                router.push({ name: 'articleList' })
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage)
                router.go(-1)
              }
            )
          } else {
            resetAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage)
        router.go(-1)
      }
    }
  );
};

const resetArticle = function () {
  article.value.articleTitle = '';
  article.value.articleContent = '';
};

onMounted(() => {
  article.value.userId = userInfo.value.userId
});

</script>

<template>
  <h2 class="text-center">작성글</h2>
  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>작성자</h5>
        <div class="input-group input-group-outline">
          <label class="form-label"></label>
          <input readonly v-model="article.userId" type="text" id="text" class="form-control form-control-md" />
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
          <input v-model="article.articleTitle" type="text" id="text" class="form-control form-control-md" />
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
    <button class="btn btn-success btn-md" @click="writeHandler">등록</button>
    <button class="btn btn-outline-success btn-md" @click="resetArticle">초기화</button>
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
