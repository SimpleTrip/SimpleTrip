<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { answerQnA, getQnA, deleteQnA } from '@/api/QnA.js';
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
const route = useRoute();
const router = useRouter();
const qnaNo = ref(route.query.qnaNo);

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
            // access만 만료 됐었어서 refresh로 새롭게 가져옴
            userInfo.value = refreshData.dataBody;
            // 새로운 토큰으로 재시도
            await getQnA(
              qnaNo.value,
              (success) => {
                // refresh 후 등록 성공
                qna.value = success.dataBody;
              },
              (fail) => {
                // 새로운 토큰으로 했는데 서버에서 에러남
                // 로그인 관련 문제 아님
                alert(fail.dataHeader.resultMessage);
                router.go(-1);
              }
            );
          } else {
            // 로그인 만료(access/refresh 둘 다)
            // 쿠키에 아무것도 없음
            noAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        // UNAUTHORIZED 이외의 오류
        alert(fail.dataHeader.resultMessage);
        router.go(-1);
      }
    }
  );
};

const deleteAns = () => {
  const params = {
    qnaNo: qnaNo.value,
    answer: null,
    answerAdminId: null,
  };

  answerQnA(
    params,
    (success) => {
      alert('답변이 삭제 되었습니다.');
      router.go(0);
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
                alert('답변이 삭제 되었습니다.');
                router.go(0);
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

const deleteQna = () => {
  deleteQnA(
    qnaNo.value,
    qna.value.questionUserId,
    (success) => {
      alert('QnA가 삭제 되었습니다.');
      router.replace({ name: 'QnAList' });
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh();
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody;
            await deleteQnA(
              qnaNo.value,
              (success) => {
                alert('QnA가 삭제 되었습니다.');
                router.replace({ name: 'QnAList' });
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

const goBack = () => {
  router.go(-1);
};
</script>

<template>
  <h2 class="text-center">Show QnA</h2>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>User ID</h5>
        <div class="input-group input-group-outline form-control form-control-md">
          <div class="col form-control form-control-md border rounded">
            {{ qna.questionUserId }}
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Question</h5>
        <div class="input-group input-group-outline form-control form-control-md">
          <div class="col form-control form-control-md border rounded">
            {{ qna.question }}
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Answer</h5>
        <div class="input-group input-group-outline form-control form-control-md">
          <div class="col form-control form-control-md border rounded">
            {{ qna.answer || '미답변 상태입니다.' }}
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <div class="d-flex flex-column align-items-center container">
      <div class="col-6 mb-2 mt-2">
        <h5>Admin ID</h5>
        <div class="input-group input-group-outline form-control form-control-md">
          <div class="col form-control form-control-md border rounded">
            {{ qna.answerAdminId || '미답변 상태입니다.' }}
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="d-flex justify-content-center gap-2">
    <a @click="goBack" class="btn btn-secondary btn-md">목록으로 돌아가기</a>
    <router-link :to="{ name: 'QnAAnswer', query: { qnaNo: qna.qnaNo } }" class="btn btn-success btn-md" v-if="qna.answer == null && userInfo.userRole == 'ROLE_ADMIN'" replace>답변 작성</router-link>
    <router-link :to="{ name: 'QnAAnswer', query: { qnaNo: qna.qnaNo } }" class="btn btn-success btn-md" v-if="qna.answer != null && userInfo.userRole == 'ROLE_ADMIN'" replace>답변 수정</router-link>
    <a @click="deleteAns" class="btn btn-warning btn-md" v-if="qna.answer != null && userInfo.userRole == 'ROLE_ADMIN'">답변 삭제</a>

    <a @click="deleteQna" class="btn btn-danger btn-md" v-if="userInfo.userRole == 'ROLE_ADMIN' || userInfo.userId == qna.questionUserId">QnA 삭제</a>
  </div>
</template>

<style scoped></style>
