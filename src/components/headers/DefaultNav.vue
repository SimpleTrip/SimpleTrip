<script setup>
import { storeToRefs } from 'pinia';
import { RouterLink, useRouter } from 'vue-router';
import { useUserStore } from '@/stores/user';
import { logout } from '@/api/user';
import { useCookies } from 'vue3-cookies';

const cookies = useCookies();
const userStore = useUserStore();
const { isLogin } = storeToRefs(userStore);
const router = useRouter();

const clickLogout = () => {
  logout(
    (success) => {
      cookies.remove('refreshToken', '/', 'localhost');
      cookies.remove('accessToken', '/', 'localhost');
      cookies.alert('로그아웃 되었습니다.', router.replace({ name: 'main' }));
    },
    (fail) => {
      isLogin.value = false;
      alert('로그아웃 실패!', fail.dataHeader.resultMessage);
    }
  );
};
</script>

<template>
  <nav class="navbar navbar-expand-lg top-0 my-3 blur border-radius-lg z-index-3 py-2 shadow py-2 start-0 end-0 mx-4 position-absolute mt-4">
    <div class="container-fluid px-0">
      <router-link class="navbar-brand d-none d-md-block" :to="{ name: 'main' }" style="font-weight: bold">SIMPLE TRIP</router-link>
      <router-link class="navbar-brand d-block d-md-none" :to="{ name: 'main' }" style="font-weight: bold">SIMPLE TRIP</router-link>
      <button class="navbar-toggler shadow-none ms-2" data-bs-toggle="collapse" data-bs-target="#navCollapse" aria-controls="navCollapse" aria-expanded="false">
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      <div class="collapse navbar-collapse w-100 pt-3 pb-2 py-lg-0" id="navCollapse">
        <ul class="navbar-nav navbar-nav-hover ms-auto">
          <li class="nav-item">
            <a v-if="isLogin" class="nav-link" @click="clickLogout">로그아웃</a>
            <router-link v-else class="nav-link" :to="{ name: 'login' }">로그인</router-link>
          </li>
          <li>
            <router-link class="nav-link" :to="{ name: 'articles' }">게시판</router-link>
          </li>
          <li>
            <router-link class="nav-link" :to="{ name: 'plan' }">여행 플래너</router-link>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <router-link class="nav-link" :to="{ name: 'spotScan' }">여행지 찾기</router-link>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" data-bs-toggle="dropdown" aria-expanded="false">장소</a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <router-link class="dropdown-item" :to="{ name: 'placeRegister' }">장소 등록</router-link>
                <router-link class="dropdown-item" :to="{ name: 'placeList' }">장소 목록</router-link>
              </li>
            </ul>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" data-bs-toggle="dropdown" aria-expanded="false"> HELP DESK </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <router-link class="dropdown-item" :to="{ name: 'QnA' }">QnA</router-link>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
