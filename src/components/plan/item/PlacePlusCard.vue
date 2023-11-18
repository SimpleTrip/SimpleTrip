<script setup>
import { ref, computed } from 'vue';
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

const emit = defineEmits(['clickMinus', 'clickDetail']);

const clickMinus = () => {
  emit('clickMinus', props.idx);
};

const clickDetail = () => {
  emit('clickDetail', props.idx);
};
</script>

<template>
  <div class="place-plus-card-container" @click="clickDetail">
    <div class="title-container">
      <div>
        <i class="fas" :class="bindClass" style="font-size: 14px; margin: 4px; color: #4caf50; z-index: 1"></i>
        <span style="font-size: 14px; font-weight: bold; margin: 4px">{{ props.idx + 1 }}. {{ props.place.place_name }}</span>
      </div>
      <i class="fas fa-minus minus-icon" @click.stop="clickMinus"></i>
    </div>
    <span style="font-size: 12px; margin: 2px">주소: {{ props.place.address_name }}</span>
    <a style="font-size: 12px; margin: 2px; text-overflow: ellipsis; overflow: hidden" :href="props.place.place_url" target="_blank" @click.stop>{{ props.place.place_url }}</a>
  </div>
</template>

<style scoped>
.place-plus-card-container {
  display: flex;
  border-radius: 10px;
  flex-direction: column;
  margin: 4px;
  margin-bottom: 10px;
  padding: 8px;
  box-shadow: 0px 2px 4px 0px #8bc34a;
}

.place-plus-card-container:hover {
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

.minus-icon {
  color: #ef5350;
  z-index: 0;
}

.minus-icon:hover {
  color: #b71c1c;
  z-index: 0;
}
</style>
