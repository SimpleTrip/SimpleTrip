<script setup>

const props = defineProps({
  place: Object,
});

</script>

<template>
  <tr>
    <td class="align-middle text-center">
      <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
        {{ props.place.placeId }}
      </a>
    </td>
    <td>
      <div class="d-flex px-2 py-1">
        <div><img :src="place.placeImgUrl" class="avatar avatar-sm me-3"></div>
        <div class="d-flex flex-column justify-content-center">
          <h6 class="mb-0 text-xs">{{ place.placeName }}</h6>
          <p class="text-xs text-secondary mb-0">{{ place.placeAddr }}</p>
        </div>
      </div>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">
        {{ props.place.placeType }}
      </p>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">
        {{ props.place.placeDate }}
      </p>
    </td>
  </tr>
</template>

<style scoped></style>
