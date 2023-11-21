<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { answerQnA, getQnA } from '@/api/QnA.js';
import { useCookies } from 'vue3-cookies';
import { refresh } from '@/util/tokenUtil';
import { useUserStore } from '@/stores/user';
import { storeToRefs } from 'pinia';

const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);
const { cookies } = useCookies();

const noAuth = () => {
  cookies.remove('refreshToken', '/', 'localhost');
  cookies.remove('accessToken', '/', 'localhost');
  isLogin.value = false;
  userInfo.value = {};
  router.replace({ name: 'login' });
};

const qna = ref({});
const router = useRouter();
const route = useRoute();

const qnaNo = ref(route.query.qnaNo);

const onClickAnswer = () => {
  answerQna();
};

const answerQna = () => {
  const params = {
    qnaNo: qnaNo.value,
    answer: qna.value.answer,
    answerAdminId: userInfo.value.user.userId,
  };

  answerQnA(
    params,
    (success) => {
      alert('답변 작성/수정이 완료 되었습니다.');
      router.replace({ name: 'QnADetail', query: { qnaNo: qnaNo.value } });
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await answerQnA(
              qnaNo.value,
              (success) => {
                alert('답변 작성/수정이 완료 되었습니다.');
                router.replace({ name: 'QnADetail', query: { qnaNo: qnaNo.value } });
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage);
                router.go(-1);
              }
            );
          } else {
            noAuth();
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

onMounted(() => {
  getqna();
});

const getqna = () => {
  getQnA(
    qnaNo.value,
    (success) => {
      qna.value = success.dataBody;
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await getQnA(
              qnaNo.value,
              (success) => (qna.value = success.dataBody),
              (fail) => {
                alert(fail.dataHeader.resultMessage);
                router.go(-1);
              }
            );
          } else {
            noAuth();
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
</script>

<template>
  <h2 class="text-center">Answer QnA</h2>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Answer</h5>
        <div class="input-group input-group-outline" style="min-height: 300px">
          <label class="form-label"></label>
          <textarea class="text-area" rows="5" cols="37" style="width: 100%" v-model="qna.answer">{{ qna.answer }}</textarea>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <router-link :to="{ name: 'QnADetail', query: { qnaNo: qnaNo } }" class="btn btn-secondary btn-md" replace>QnA로 돌아가기</router-link>
    <a @click="onClickAnswer" class="btn btn-success btn-md">Answer</a>
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
