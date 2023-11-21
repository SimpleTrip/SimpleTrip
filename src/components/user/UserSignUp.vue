<script setup>
import { onMounted } from 'vue';

//Vue Material Kit 2 components
import MaterialInput from '@/components/material/MaterialInput.vue';
import MaterialButton from '@/components/material/MaterialButton.vue';

// material-input
import setMaterialInput from '@/assets/js/material-input';
onMounted(() => {
  setMaterialInput();
});
</script>
<template>
  <div class="container my-auto">
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
              <MaterialInput id="id" class="input-group-outline my-3" :label="{ text: '아이디', class: 'form-label' }" />
              <MaterialInput id="password" class="input-group-outline mb-3" :label="{ text: '비밀번호', class: 'form-label' }" type="password" />
              <MaterialInput id="name" class="input-group-outline mb-3" :label="{ text: '이름', class: 'form-label' }" />

              <div class="my-3">
                <select class="select-option form-select text-gray" id="sex" style="padding-left: 10px; padding-right: 10px">
                  <option disabled selected>성별</option>
                  <option value="man">남자</option>
                  <option value="woman">여자</option>
                </select>
              </div>

              <MaterialInput id="age" class="input-group-outline mb-3" :label="{ text: '나이', class: 'form-label' }" />

              <div class="text-center">
                <MaterialButton class="my-4 mb-2" variant="gradient" color="success" fullWidth>등록하기</MaterialButton>
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
