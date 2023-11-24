<script setup>
import { computed } from 'vue';
const props = defineProps({
  place: {
    type: Object,
  },
  idx: {
    type: Number,
  },
});

const bindClass = computed(() => {
  const cate = props.place.category_group_code;
  if (cate == 'CE7') return 'fa-coffee';
  else if (cate == 'MT1' || cate == 'CS2') return 'fa-shopping-basket';
  else if (cate == 'SC4' || cate == 'PS3' || cate == 'AC5') return 'fa-school';
  else if (cate == 'PK6') return 'fa-parking';
  else if (cate == 'SW8') return 'fa-subway';
  else if (cate == 'BK9') return 'fa-piggy-bank';
  else if (cate == 'CT1') return 'fa-palette';
  else if (cate == 'AT4') return 'fa-star';
  else if (cate == 'AD5') return 'fa-bed';
  else if (cate == 'FD6') return 'fa-utensils';
  else if (cate == 'HP8') return 'fa-hospital';
  else if (cate == 'PM9') return 'fa-clinic-medical';
  else if (cate == 'OL7') return 'fa-gas-pump';
  else return 'fa-map-marker';
});
</script>

<template>
  <div class="place-card-container">
    <div class="title-container">
      <div>
        <i class="fas" :class="bindClass" style="font-size: 16px; margin: 4px; color: #4caf50; z-index: 1"></i>
        <span style="font-size: 16px; font-weight: bold; margin: 4px">{{ props.idx + 1 }}. {{ props.place.placeName }}</span>
      </div>
    </div>
    <span style="font-size: 14px; margin: 2px">주소: {{ props.place.addressName }}</span>
    <a style="font-size: 14px; margin: 2px; text-overflow: ellipsis; overflow: hidden" :href="props.place.placeUrl" target="_blank">{{ props.place.placeUrl }}</a>
    <hr />
    <div class="row">
      <span style="font-size: 16px; margin: 2px; font-weight: bold">간단 메모:</span>
      <span style="font-size: 14px; margin: 2px">{{ props.place.memo }}</span>
    </div>
  </div>
</template>

<style scoped>
.place-card-container {
  display: flex;
  border-radius: 10px;
  flex-direction: column;
  margin: 4px;
  margin: 20px;
  padding: 8px;
  box-shadow: 0px 2px 4px 0px #8bc34a;
}

.place-card-container:hover {
  box-shadow: 0px 2px 4px 0px #558b2f;
}

.title-container {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.content-container {
  display: flex;
  align-items: center;
}

.plus-icon {
  color: #03a9f4;
  z-index: 0;
}

.plus-icon:hover {
  color: #1976d2;
  z-index: 0;
}
</style>
