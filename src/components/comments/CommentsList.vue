<script setup>
import CommentsItems from '@/components/comments/CommentsItems.vue';
import { ref, onMounted, computed } from 'vue';
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'
import { getCommentList, writeComment } from '@/api/comment.js';

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

const commentList = ref([]);
const inputCommentContent = ref('');

const props = defineProps({
  articleId: String,
});

const setCommentList = async () => {
  await getCommentList(props.articleId, ({ data }) => {
    commentList.value = data;
  });
};

const lengthCommentList = computed(() => {
  return commentList.value.length;
});

const clickWrite = async () => {

  console.log(userInfo.value.userId)

  const comment = {
    commentContent: inputCommentContent.value,
    commentUserId: userInfo.value.userId,
    articleId: props.articleId,
  };

  await writeComment(comment, ({ data }) => {});
  inputCommentContent.value = '';
  await setCommentList();
};

const updateComment = async () => {
  await setCommentList();
};

onMounted(() => {
  setCommentList();
});
</script>

<template>
  <div>
    <div class="container" style="overflow-y: auto; height: 440px; margin-top: 40px">
      <CommentsItems v-for="comment in commentList" :key="comment.id" :comment="comment" v-if="lengthCommentList != 0" @delete-comment="updateComment" @rewrite-comment="updateComment" />
      <p style="height: 400px" v-else>댓글이 없습니다.</p>
    </div>

    <div class="writeContainer">
      <span style="font-size: 14px; min-width: 12%">댓글: </span>
      <div class="input-group input-group-outline">
        <label class="form-label"></label>
        <input type="text" id="text" class="form-control form-control-md" v-model="inputCommentContent" />
      </div>
      <a class="btn btn-success btn-md" style="margin: 4px; padding: 2px; padding-top: 5px; padding-bottom: 5px; min-width: 16%" @click="clickWrite">작성</a>
    </div>
  </div>
</template>

<style scoped>
.writeContainer {
  margin: 10px;
  padding: 10px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
</style>
