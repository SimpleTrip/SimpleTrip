<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import setMaterialInput from '@/assets/js/material-input';
import { getMyPass, sendEmail, checkEmail } from '@/api/user.js';

const router = useRouter();
const inputEmail = ref('');
const isSend = ref(false);
const authCode = ref('');

onMounted(() => {
  setMaterialInput();
});

const goLogin = () => {
  router.replace({ name: 'login' });
};

const clickFind = () => {
  sendEmail(
    inputEmail.value,
    (success) => {
      alert('이메일이 발송되었습니다.');
      isSend.value = true;
    },
    (fail) => {
      alert('이메일 발송에 실패했습니다.');
    }
  );
};

const clickCheckCode = () => {
  const params = {
    email: inputEmail.value,
    authCode: authCode.value,
  };
  checkEmail(
    params,
    (success) => {
      alert(`비밀번호 : ${success.dataBody}`);
    },
    (fail) => {
      alert(fail.dataHeader.resultMessage);
    }
  );
};
</script>

<template>
  <div class="w-100">
    <div class="row my-6">
      <div class="col-lg-4 col-md-8 col-12 mx-auto">
        <div class="card z-index-0 fadeIn3 fadeInBottom">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
              <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">FIND PASS</h4>
            </div>
          </div>
          <div class="card-body">
            <form role="form" class="text-start">
              <div class="input-group input-group-static is-filled my-4">
                <label class="form-label">아이디로 이메일 인증</label>
                <input type="text" class="form-control" v-model="inputEmail" />
              </div>
              <div v-if="isSend" class="input-group input-group-static is-filled my-4">
                <label class="form-label">인증코드</label>
                <input type="text" class="form-control" v-model="authCode" />
              </div>
            </form>
            <div v-if="!isSend" class="d-flex justify-content-around" style="padding-top: 10px">
              <a @click="goLogin" class="btn btn-secondary btn-md" style="width: 40%; margin: 0px">로그인으로 돌아가기</a>
              <a @click="clickFind" class="btn btn-success btn-md" style="width: 40%; margin: 0px">이메일 보내기</a>
            </div>
            <div v-else class="d-flex justify-content-between" style="padding-top: 10px; padding-bottom: 10px">
              <a @click="goLogin" class="btn btn-secondary btn-md" style="width: 40%; margin: 0px">로그인으로 돌아가기</a>
              <a @click="clickCheckCode" class="btn btn-success btn-md" style="width: 40%; margin: 0px">인증하기</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.select-sex {
  border-width: 0px;
  border-bottom-width: 1px;
  border-radius: 0px;
}

.select-sex:hover {
  border-width: 0px;
  border-bottom-width: 1px;
  border-radius: 0px;
}
</style>
