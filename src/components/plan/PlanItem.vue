<script setup>
import { onMounted, ref } from 'vue';

const props = defineProps({
  plan: Object,
});

import { useRouter } from 'vue-router';

const router = useRouter();

const onPressDetail = () => {
  router.push({ name: 'planDetail', query: { planNo: props.plan.planNo } });
};
</script>

<template>
  <tr @click="onPressDetail">
    <td class="align-middle text-center">
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.plan.planNo }}</p>
    </td>
    <td>
      <div class="d-flex px-2 py-1" style="align-items: center; justify-content: center">
        <div class="d-flex flex-column justify-content-center">
          <h6 class="mb-0 text-xs">{{ props.plan.planTitle }}</h6>
        </div>
      </div>
    </td>
    <td class="align-middle text-center">
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.plan.startDate }}</p>
    </td>
    <td class="align-middle text-center">
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.plan.endDate }}</p>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.plan.planUserId }}</p>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">{{ props.plan.registDate }}</p>
    </td>
  </tr>
</template>

<style scoped></style>
