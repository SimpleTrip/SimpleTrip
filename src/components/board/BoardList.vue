<script setup>
import { ref } from "vue"
import BoardDetail from '@/components/board/BoardDetail.vue';

const currentPage = ref(2);
const isCurrentPage = function (page) {
  return currentPage.value == page;
}
</script>

<template>
  <div>
    <!-- TO BE DELETED -->
    <p>list</p>
    <RouterLink class="btn btn-outline-primary" :to="{ name: 'detail' }" type="button">router test</RouterLink>
    <br>

    <h2 class="text-center">QnA Board</h2>

    <div class="d-flex justify-content-between">
      <div class="ms-2">
        <router-link :to="{ name: 'write' }" class="btn btn-outline-success btn-md">Write</router-link>
      </div>
      <div class="d-flex">
        <div class="col-6 ">
          <div class="input-group input-group-outline">
            <label class="form-label"></label>
            <input type="text" id="text" class="form-control form-control-md">
          </div>
        </div>
        <button class="btn btn-success btn-md ms-2 me-2">Search</button>
      </div>
    </div>

    <div class="table-responsive">
      <table class="table align-items-center mb-0">
        <thead>
          <tr>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">ARTICLE NO</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">AUTHOR</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">ARTICLE</th>
            <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7 text-center">EDIT DATE</th>
          </tr>
        </thead>
        <tbody>
          <board-detail v-for="i in 5" :key="i"></board-detail>
        </tbody>
      </table>
      <br>
      <div class="d-flex justify-content-center">
        <ul class="pagination pagination-success">
          <li class="page-item"><a class="page-link" href="javascript:;"><i class="fa fa-angle-double-left"
                aria-hidden="true"></i></a></li>
          <li :class="{ 'page-item': true, active: isCurrentPage(page)}" v-for="page in 5" :key="page">
            <a class="page-link" :class="{'current-page-text': isCurrentPage(page)}" href="javascript:;">{{ page }}</a>
          </li>
          <li class="page-item "><a class="page-link" href="javascript:;"><i class="fa fa-angle-double-right"
                aria-hidden="true"></i></a></li>
        </ul>
      </div>
    </div>
  </div>
</template>

<style scoped>
.current-page-text {
  color: white
}
</style>
