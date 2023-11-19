<script setup>
import { ref } from 'vue';

const selectedValue = ref('all');

const emit = defineEmits(['selectedChange']);

const handleChange = () => {
  emit('selectedChange', selectedValue.value);
};
</script>

<template>
  <select class="form-select" aria-label="select search" style="text-align-last: center; margin: 2px" v-model="selectedValue" @change="handleChange">
    <option value="all" selected>전체</option>
    <option value="place-id">글번호</option>
    <option value="place-name">장소명</option>
    <option value="place-addr">주소</option>
    <option value="place-type">유형</option>
    <option value="place-content">내용</option>
  </select>
</template>

<style scoped></style>
