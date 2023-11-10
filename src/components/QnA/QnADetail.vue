<script setup>
defineProps({
  qna: Object,
});
</script>

<template>
  <tr>
    <td class="align-middle text-center">
      <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">{{ qna.qnaNo }}</a>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">{{ qna.questionUserId }}</p>
    </td>
    <td>
      <div class="d-flex px-2 py-1" style="align-items: center; justify-content: center">
        <div class="d-flex flex-column justify-content-center">
          <h6 class="mb-0 text-xs">{{ qna.question }}</h6>
          <p class="text-xs text-secondary mb-0">{{ qna.answer ? '답변 완료' : '답변 미완료' }}</p>
        </div>
      </div>
    </td>
  </tr>
</template>

<style scoped></style>
