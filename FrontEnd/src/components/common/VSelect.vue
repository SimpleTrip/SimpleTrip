<script setup>
import { ref, onMounted } from 'vue';

const selectedValue = ref('all');

const emit = defineEmits(['selectedChange']);

const handleChange = () => {
  emit('selectedChange', selectedValue.value);
};
</script>

<template>
  <select class="form-select" aria-label="select search" style="text-align-last: center; margin: 2px" v-model="selectedValue" @change="handleChange">
    <option value="all" selected>전체</option>
    <option value="content">제목/내용</option>
    <option value="no">글번호</option>
    <option value="author">작성자</option>
  </select>
</template>

<style scoped></style>
