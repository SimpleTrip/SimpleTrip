<script setup>
import { RouterLink } from 'vue-router';
</script>

<template>
  <nav class="navbar navbar-expand-lg top-0 my-3 blur border-radius-lg z-index-3 py-2 shadow py-2 start-0 end-0 mx-4 position-absolute mt-4">
    <div class="container-fluid px-0">
      <router-link class="navbar-brand d-none d-md-block" :to="{ name: 'main' }" style="font-weight: bold">SIMPLE</router-link>
      <router-link class="navbar-brand d-block d-md-none" :to="{ name: 'main' }" style="font-weight: bold">SIMPLE</router-link>
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
            <router-link class="nav-link" :to="{ name: 'login' }">로그인</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'spot' }">여행지</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" :to="{ name: 'board' }">게시판</router-link>
          </li>
          <li class="nav-item dropdown dropdown-hover mx-2">
            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" data-bs-toggle="dropdown" aria-expanded="false"> HELP DESK </a>
            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
              <li>
                <a class="dropdown-item" href="#">공지사항</a>
              </li>
              <li>
                <router-link class="dropdown-item" :to="{ name: 'QnA' }">FAQ</router-link>
              </li>
              <li>
                <a class="dropdown-item" href="#">문의하기</a>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<style scoped></style>
