<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { writeQnA } from '@/api/QnA.js';
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

const router = useRouter();
const question = ref('');

const onClickWrite = () => {
  writeQna();
};

const writeQna = () => {
  const params = {
    question: question.value,
    questionUserId: userInfo.value.userId,
  };
  writeQnA(
    params,
    // success
    () => {
      alert('QnA가 작성 되었습니다.');
      router.push({ name: 'QnAList' });
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await writeQnA(
              params,
              // success
              () => {
                alert('QnA가 작성 되었습니다.');
                router.push({ name: 'QnAList' });
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
</script>

<template>
  <h2 class="text-center">Write QnA</h2>
  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Question</h5>
        <div class="input-group input-group-outline" style="min-height: 300px">
          <label class="form-label"></label>
          <textarea class="text-area" rows="5" cols="37" style="width: 100%" v-model="question"></textarea>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <router-link :to="{ name: 'QnAList' }" class="btn btn-secondary btn-md">목록으로 돌아가기</router-link>
    <a @click="onClickWrite" class="btn btn-success btn-md">Write</a>
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
