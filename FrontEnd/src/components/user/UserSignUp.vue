<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { joinUser } from '@/api/user.js';
import setMaterialInput from '@/assets/js/material-input';

const router = useRouter();
const userInput = ref({
  userId: '',
  userPw: '',
  userSex: '',
  userAge: '',
  userExit: false,
});

const clickJoin = async () => {
  if (userInput.value.userId == '' || userInput.value.userPw == '' || userInput.value.userSex == '' || userInput.value.userAge == '') {
    alert('모든 정보를 입력해주세요.');
  } else {
    await joinUser(
      userInput.value,
      (success) => {
        alert('회원가입이 완료되었습니다.');
        router.replace({ name: 'login' });
      },
      (fail) => {
        alert(fail.dataHeader.resultMessage);
      }
    );
  }
};

onMounted(() => {
  setMaterialInput();
});
</script>
<template>
  <div class="my-auto w-100">
    <div class="row my-2">
      <div class="col-lg-4 col-md-8 col-12 mx-auto">
        <div class="card z-index-0 fadeIn3 fadeInBottom">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
              <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">회원 가입</h4>
              <div class="row mt-3">
                <div class="col-2 text-center ms-auto">
                  <a class="btn btn-link px-3" href="javascript:;">
                    <i class="fa fa-facebook text-white text-lg"></i>
                  </a>
                </div>
                <div class="col-2 text-center px-1">
                  <a class="btn btn-link px-3" href="javascript:;">
                    <i class="fa fa-github text-white text-lg"></i>
                  </a>
                </div>
                <div class="col-2 text-center me-auto">
                  <a class="btn btn-link px-3" href="javascript:;">
                    <i class="fa fa-google text-white text-lg"></i>
                  </a>
                </div>
              </div>
            </div>
          </div>
          <div class="card-body">
            <form role="form" class="text-start">
              <div class="input-group input-group-outline my-3">
                <label class="form-label">이메일</label>
                <input type="email" class="form-control" v-model="userInput.userId" />
              </div>
              <div class="input-group input-group-outline my-3">
                <label class="form-label">비밀번호</label>
                <input type="password" class="form-control" v-model="userInput.userPw" />
              </div>
              <div class="my-3">
                <select class="select-option form-select text-gray" id="sex" style="padding-left: 10px; padding-right: 10px" v-model="userInput.userSex">
                  <option value="" disabled selected>성별</option>
                  <option value="M">남자</option>
                  <option value="F">여자</option>
                </select>
              </div>
              <div class="input-group input-group-outline my-3">
                <label class="form-label">나이</label>
                <input type="number" min="10" class="form-control" v-model="userInput.userAge" />
              </div>

              <div class="text-center" style="padding-top: 20px; padding-bottom: 10px">
                <a @click="clickJoin" class="btn btn-success btn-md" style="width: 100%; margin: 0px">등록하기</a>
              </div>
              <p class="mt-4 text-sm text-center">
                계정이 이미 있으신가요?
                <RouterLink :to="{ name: 'login' }" class="text-success text-gradient font-weight-bold">로그인</RouterLink>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
