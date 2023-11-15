<script setup>
import { defineProps, ref } from 'vue';
import { rewriteComment, deleteComment } from '@/api/comment.js';

const emit = defineEmits(['deleteComment', 'rewriteComment']);

const props = defineProps({
  comment: { type: Object, default: { commentContent: '', commentUserId: '' } },
});

const reWriteInput = ref('');
const isReWrite = ref(false);
const clickReWriteIcon = () => {
  isReWrite.value = !isReWrite.value;
};

const clickReWrite = async () => {
  await rewriteComment(props.comment, ({ data }) => {});
  clickReWriteIcon();
  emit('rewriteComment');
};

const clickDelete = async () => {
  await deleteComment(props.comment.commentNo, ({ data }) => {});
  emit('deleteComment');
};
</script>

<template>
  <div class="commentContainer">
    <div class="align-items-center">
      <span style="font-size: 14px">작성자: {{ props.comment.commentUserId }}</span>
      <i class="fas fa-trash" style="font-size: 12px; margin-left: 6px; color: crimson" @click="clickDelete"></i>
    </div>
    <div class="content" v-if="!isReWrite">
      <div class="comment-text">{{ props.comment.commentContent }}</div>
      <i class="fas fa-edit" style="font-size: 14px" @click="clickReWriteIcon"></i>
    </div>
    <div class="content" v-else>
      <div class="input-group input-group-outline" style="margin-right: 6px">
        <label class="form-label"></label>
        <input :placeholder="props.comment.commentContent" type="text" id="text" class="form-control form-control-sm" v-model="props.comment.commentContent" />
      </div>
      <i class="fas fa-times" style="font-size: 14px; margin-right: 6px; color: red" @click="clickReWriteIcon"></i>
      <i class="fas fa-check" style="font-size: 14px; color: green" @click="clickReWrite"></i>
    </div>
  </div>
</template>

<style scoped>
.commentContainer {
  display: flex;
  flex-direction: column;
  border: 1px #dcedc8 solid;
  border-radius: 10px;
  padding: 14px;
  margin-top: 6px;
  margin-bottom: 6px;
  justify-content: space-around;
  box-shadow: 0px 1px #81c784;
}

.content {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.comment-text {
  font-size: 14px;
  font-weight: bold;
  display: flex; /* 수정된 부분 */
  overflow-wrap: anywhere;
  margin-right: 6px;
}
</style>
