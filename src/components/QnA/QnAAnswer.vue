<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { answerQnA, getQnA } from '@/api/QnA.js';

const qna = ref({});
const router = useRouter();
const route = useRoute();

const qnaNo = ref(route.query.qnaNo);
const user = ref({
  userId: 'ssafy',
});

const onClickAnswer = () => {
  answerQna();
  alert('답변 작성/수정이 완료 되었습니다.', router.replace({ name: 'QnADetail', query: { qnaNo: qnaNo.value } }));
};

const answerQna = () => {
  const params = {
    qnaNo: qnaNo.value,
    answer: qna.value.answer,
    answerAdminId: user.value.userId,
  };

  answerQnA(params, ({ data }) => {});
};

onMounted(() => {
  getqna();
});

const getqna = () => {
  getQnA(qnaNo.value, ({ data }) => {
    qna.value = data;
  });
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
