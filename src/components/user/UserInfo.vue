<script setup>
import { onMounted } from 'vue';
import { storeToRefs } from 'pinia'
import { useUserStore } from '@/stores/user'

//Vue Material Kit 2 components
import MaterialInput from '@/components/material/MaterialInput.vue';
import MaterialButton from '@/components/material/MaterialButton.vue';

// material-input
import setMaterialInput from '@/assets/js/material-input';

const userStore = useUserStore()
const { userInfo } = storeToRefs(userStore)

const withdraw = function () {
  console.log('withdraw')
}

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
              <h4 class="text-white font-weight-bolder text-center mt-2 mb-0">회원 정보</h4>
            </div>
          </div>
          <div class="card-body">
            <div class="text-center">
              <MaterialInput readonly class="input-group-static input-group-outline my-3" label="아이디" type="text" :placeholder="userInfo.userId"/>
              <MaterialButton class="my-4 mb-2" variant="gradient" color="danger" @click="withdraw">회원 탈퇴</MaterialButton>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
