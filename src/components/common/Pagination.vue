<script setup>
import { ref, computed, watch } from 'vue';
import MaterialPagination from '@/components/material/MaterialPagination.vue';
import MaterialPaginationItem from '@/components/material/MaterialPaginationItem.vue';

const props = defineProps({
  totalPgno: {
    type: Number,
    default: 1,
  },
});

const curLabel = ref(1);
const firstLabels = ref(1);
const lastLabels = ref(5);
const pageSize = 5;

const labels = computed(() => {
  const tmpLabels = [];
  for (let i = 0; i < pageSize; i++) {
    if (firstLabels.value + i <= props.totalPgno) tmpLabels.push(firstLabels.value + i);
  }
  lastLabels.value = tmpLabels[tmpLabels.length - 1];
  return tmpLabels;
});

const clickLabel = (clickedLabel) => {
  curLabel.value = clickedLabel;
};

const clickNext = () => {
  if (lastLabels.value != props.totalPgno) {
    firstLabels.value = firstLabels.value + pageSize;
    curLabel.value = firstLabels.value;
  }
};

const clickPrev = () => {
  if (firstLabels.value != 1) {
    firstLabels.value = firstLabels.value - pageSize;
    curLabel.value = firstLabels.value + pageSize - 1;
  }
};

const emit = defineEmits(['clickPage']);
const clickPage = () => {
  emit('clickPage', curLabel.value);
};
</script>

<template>
  <div class="page">
    <MaterialPagination>
      <MaterialPaginationItem
        prev
        @click="
          () => {
            clickPrev();
            clickPage();
          }
        "
      />
      <MaterialPaginationItem
        v-for="label in labels"
        :key="label"
        :label="String(label)"
        :active="label == curLabel"
        @click="
          () => {
            clickLabel(label);
            // emit 처리 해서 curLabel 넘겨서 pgno로 쓰고...
            // 그 pgno를 다시 axios로 보내서 test 해보면 될 듯?
            clickPage();
          }
        "
      />
      <MaterialPaginationItem
        next
        @click="
          () => {
            clickNext();
            clickPage();
          }
        "
      />
    </MaterialPagination>
  </div>
</template>

<style scoped>
.page {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
