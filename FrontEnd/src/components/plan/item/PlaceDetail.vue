<script setup>
import { ref } from 'vue';

const inputMemo = ref('');
const emit = defineEmits(['closeDetail', 'writeDetail']);
const props = defineProps({
  place: {
    type: Object,
  },
  idx: {
    type: Number,
  },
});

const closeDetail = () => {
  emit('closeDetail');
};

const writeDetail = () => {
  emit('writeDetail', inputMemo.value);
};
</script>

<template>
  <div class="detail-container">
    <div class="top-container">
      <h3 style="padding: 0px; margin: 0px; color: white; font-weight: bold">여행 계획 세부 작성</h3>
      <i class="fas fa-times icon" @click="closeDetail"></i>
    </div>
    <div class="content-container">
      <div style="width: 45%; overflow-wrap: anywhere">
        <p style="font-size: 22px">지역명:</p>
        <p>{{ props.place.place_name }}</p>
        <hr />
        <p style="font-size: 22px">주소/도로명:</p>
        <p>{{ props.place.address_name }}</p>
        <p>{{ props.place.road_address_name }}</p>
        <hr />
        <p style="font-size: 22px">카테고리:</p>
        <p>{{ props.place.category_name }}</p>
        <hr />
        <a :href="props.place.place_url" target="_blank">{{ props.place.place_url }}</a>
      </div>
      <div class="input-group input-group-outline" style="width: 45%">
        <span>간단 메모</span>
        <textarea class="text-area form-contol form-control-md col-12" maxlength="200" style="border-radius: 6px" rows="10" v-model="inputMemo" :placeholder="props.place.memo"></textarea>
      </div>
    </div>
    <div style="display: flex; width: 100%; justify-content: center; align-items: center">
      <a class="btn btn-success btn-md" style="width: 100%; margin: 20px" @click="writeDetail">메모 등록</a>
    </div>
  </div>
</template>

<style scoped>
.detail-container {
  display: flex;
  width: 100%;
  flex-direction: column;
}

.top-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  background-color: #4caf50;
  padding: 15px;
  border-top-right-radius: 6px;
  border-top-left-radius: 6px;
}

.icon {
  font-size: 30px;
  color: white;
}

.icon:hover {
  color: lightgray;
}

.content-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
  padding: 20px;
  height: 100%;
}

p {
  padding: 0px;
  margin: 0px;
  color: black;
  font-size: 18px;
  font-weight: bold;
}
</style>
