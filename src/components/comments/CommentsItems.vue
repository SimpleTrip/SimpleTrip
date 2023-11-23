<script setup>
import { defineProps, ref, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { storeToRefs } from 'pinia'
import { useCookies } from 'vue3-cookies';
import { useUserStore } from '@/stores/user'
import { rewriteComment, deleteComment } from '@/api/comment.js';
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

const emit = defineEmits(['deleteComment', 'rewriteComment']);

const props = defineProps({
  comment: { type: Object, default: { commentContent: '', commentUserId: '' } },
});

const reWriteInput = ref('');
const isReWrite = ref(false);
const clickReWriteIcon = () => {
  isReWrite.value = !isReWrite.value;
  reWriteInput.value = '';
};

const clickReWrite = async () => {
  props.comment.commentContent = reWriteInput.value;
  await rewriteComment(
    props.comment,
    // success
    () => {
      // 
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh()
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody
            await rewriteComment(
              props.comment,
              // success
              () => {
                // 
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage)
              }
            )
          } else {
            resetAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage)
      }
    }
  );
  clickReWriteIcon();
  emit('rewriteComment');
};

const clickDelete = async () => {
  await deleteComment(
    props.comment.commentNo,
    props.comment.commentUserId,
    // success
    () => {
      alert('댓글이 삭제되었습니다.')
      emit('deleteComment');
    },
    async (fail) => {
      if (fail.dataHeader.resultCode == 'UNAUTHORIZED' && fail.dataHeader.successCode == 1) {
        const refreshData = await refresh()
        if (refreshData != null) {
          if (refreshData.dataHeader.successCode == 0) {
            userInfo.value = refreshData.dataBody
            await deleteComment(
              props.comment.commentNo,
              props.comment.commentUserId,
              // success
              () => {
                alert('댓글이 삭제되었습니다.')
                emit('deleteComment');
              },
              (fail) => {
                alert(fail.dataHeader.resultMessage)
              }
            )
          } else {
            resetAuth();
            alert(fail.dataHeader.resultMessage);
          }
        }
      } else {
        alert(fail.dataHeader.resultMessage)
      }
    }
  );
};

const canClick = ref(false)

const checkOwner = function (comment, isLogin, userInfo) {
  if (userInfo.value) canClick.value = isLogin.value && (comment.commentUserId === userInfo.value.userId)
  else canClick.value = false
}

onMounted(() => {
  watch([userInfo.value, props.comment], () => {
    checkOwner(props.comment, isLogin, userInfo)
  }, { immediate: true })
});
</script>

<template>
  <div class="commentContainer">
    <div class="align-items-center">
      <span style="font-size: 14px">작성자: {{ props.comment.commentUserId }}</span>
      <i v-if="canClick" class="fas fa-trash" style="font-size: 12px; margin-left: 6px; color: crimson"
        @click="clickDelete"></i>
    </div>
    <div class="content" v-if="!isReWrite">
      <div class="comment-text">{{ props.comment.commentContent }}</div>
      <i v-if="canClick" class="fas fa-edit" style="font-size: 14px" @click="clickReWriteIcon"></i>
    </div>
    <div class="content" v-else>
      <div class="input-group input-group-outline" style="margin-right: 6px">
        <label class="form-label"></label>
        <input :placeholder="props.comment.commentContent" type="text" id="text" class="form-control form-control-sm"
          v-model="reWriteInput" />
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
  display: flex;
  /* 수정된 부분 */
  overflow-wrap: anywhere;
  margin-right: 6px;
}
</style>
