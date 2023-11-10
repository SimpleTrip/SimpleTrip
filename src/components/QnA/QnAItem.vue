<script setup>
import { onMounted, ref } from 'vue';
import { getQnA } from '@/api/QnA.js';

const props = defineProps({
  qna: Object,
});

import { useRouter } from 'vue-router';

const router = useRouter();

const onPressDetail = () => {
  router.push({ name: 'QnADetail', query: { qnaNo: props.qna.qnaNo } });
};
</script>

<template>
  <tr @click="onPressDetail">
    <td class="align-middle text-center">
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.qna.qnaNo }}</p>
    </td>
    <td>
      <div class="d-flex px-2 py-1" style="align-items: center; justify-content: center">
        <div class="d-flex flex-column justify-content-center">
          <h6 class="mb-0 text-xs">{{ props.qna.question }}</h6>
        </div>
      </div>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.qna.questionUserId }}</p>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.qna.answer != null ? '답변 완료' : '답변 미완료' }}</p>
    </td>
  </tr>
</template>

<style scoped></style>
