<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { answerQnA, getQnA, deleteQnA } from '@/api/QnA.js';

const qna = ref({});
const route = useRoute();
const router = useRouter();
const qnaNo = ref(route.query.qnaNo);

const user = ref({
  userId: 'ssafy',
});

onMounted(() => {
  getqna();
});

const getqna = () => {
  getQnA(qnaNo.value, ({ data }) => {
    qna.value = data;
  });
};

const deleteAns = () => {
  const params = {
    qnaNo: qnaNo.value,
    answer: null,
    answerAdminId: null,
  };

  answerQnA(params, ({ data }) => {});

  alert('답변이 삭제 되었습니다.', router.go(0));
};

const deleteQna = () => {
  deleteQnA(qnaNo.value, ({ data }) => {});
  alert('QnA가 삭제 되었습니다.', router.replace({ name: 'QnAList' }));
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
    <router-link :to="{ name: 'QnAList' }" class="btn btn-secondary btn-md">뒤로가기</router-link>

    <!-- 나중에 admin/user 분기로 갈라주기 -->
    <router-link :to="{ name: 'QnAAnswer', query: { qnaNo: qna.qnaNo } }" class="btn btn-success btn-md" v-if="qna.answer == null">답변 작성</router-link>
    <router-link :to="{ name: 'QnAAnswer', query: { qnaNo: qna.qnaNo } }" class="btn btn-success btn-md" v-if="qna.answer != null">답변 수정</router-link>
    <a @click="deleteAns" class="btn btn-warning btn-md" v-if="qna.answer != null">답변 삭제</a>

    <!-- 나중에 본인 글만 삭제할 수 있게 하기 + 관리자는 전부 다 삭제 가능 -->
    <a @click="deleteQna" class="btn btn-danger btn-md">QnA 삭제</a>
  </div>
</template>

<style scoped></style>
