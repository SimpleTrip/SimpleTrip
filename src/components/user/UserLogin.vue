<script setup>
import { onMounted, ref } from 'vue';
import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';
import MaterialSwitch from '@/components/material/MaterialSwitch.vue';
import setMaterialInput from '@/assets/js/material-input';
import { loginUser } from '@/api/user.js';
import { useCookies } from 'vue3-cookies';
import { useUserStore } from '@/stores/user';

const { cookies } = useCookies();
const userStore = useUserStore();
const { isLogin, userInfo } = storeToRefs(userStore);

const userInput = ref({
  userId: '',
  userPw: '',
});

const router = useRouter();

onMounted(() => {
  setMaterialInput();
});

const clickLogin = async () => {
  await loginUser(
    userInput.value,
    (success) => {
      userInfo.value = success.dataBody;
      isLogin.value = true;
      router.replace({ name: 'main' });
    },
    (fail) => {
      alert(fail.dataHeader.resultMessage);
    }
  );
};
</script>
<template>
  <div class="container my-auto">
    <div class="row my-5">
      <div class="col-lg-4 col-md-8 col-12 mx-auto">
        <div class="card z-index-0 fadeIn3 fadeInBottom">
          <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
            <div class="bg-gradient-success shadow-success border-radius-lg py-3 pe-1">
              <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">Sign in</h4>
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
                <label class="form-label">id</label>
                <input type="email" class="form-control" v-model="userInput.userId" />
              </div>
              <div class="input-group input-group-outline my-3">
                <label class="form-label">password</label>
                <input type="password" class="form-control" v-model="userInput.userPw" />
              </div>

              <MaterialSwitch class="d-flex align-items-center mb-3" id="rememberMe" labelClass="mb-0 ms-3" checked>Remember me</MaterialSwitch>

              <div class="text-center" style="padding-top: 20px; padding-bottom: 10px">
                <a @click="clickLogin" class="btn btn-success btn-md" style="width: 100%; margin: 0px">Sign in</a>
              </div>

              <p class="mt-4 text-sm text-center">
                Don't have an account?
                <RouterLink :to="{ name: 'signup' }" class="text-success text-gradient font-weight-bold">Sign up</RouterLink>
              </p>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
