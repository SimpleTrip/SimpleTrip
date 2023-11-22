<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import setMaterialInput from '@/assets/js/material-input';
import { getMyPass } from '@/api/user.js';

const router = useRouter();
const userInput = ref({});

onMounted(() => {
  setMaterialInput();
});

const clickFind = () => {
  getMyPass(
    userInput.value,
    (success) => {
      alert(`비밀번호:  ${success.dataBody}`);
      userInput.value = {};
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
            <div class="text-center">
              <div class="input-group input-group-static my-4 is-filled">
                <label class="form-label">아이디</label>
                <input type="text" class="form-control" v-model="userInput.userId" />
              </div>
              <div class="input-group input-group-static my-4 is-filled">
                <label class="form-label">나이</label>
                <input type="number" min="10" class="form-control" v-model="userInput.userAge" />
              </div>
              <div class="input-group input-group-static my-4 is-filled">
                <label class="form-label">성별</label>
                <select class="select-option form-select text-lightgray select-sex" id="sex" style="border-width: 0px; border-bottom-width: 1px; border-radius: 0px" v-model="userInput.userSex">
                  <option value="" selected disabled>성별을 선택해주세요</option>
                  <option value="M">남자</option>
                  <option value="F">여자</option>
                </select>
              </div>
            </div>
            <div class="text-center" style="padding-top: 20px; padding-bottom: 10px">
              <a @click="clickFind" class="btn btn-success btn-md" style="width: 100%; margin: 0px">비밀번호 확인</a>
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
