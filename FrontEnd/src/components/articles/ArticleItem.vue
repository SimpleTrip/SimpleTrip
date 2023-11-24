<script setup>
import { useRouter } from 'vue-router';

const props = defineProps({
  article: Object,
});

const router = useRouter();
const routeArticleDetail = function () {
  router.push({ name: 'articleDetail', params: { articleId: props.article.articleId } });
};
</script>

<template>
  <tr @click="routeArticleDetail">
    <td class="align-middle text-center">
      <a href="#" class="text-secondary font-weight-bold text-xs" data-toggle="tooltip" data-original-title="Edit user">
        {{ props.article.articleId }}
      </a>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">
        {{ props.article.articleTitle }}
      </p>
    </td>
    <td>
      <p class="text-xs font-weight-bold mb-0 text-center">
        {{ props.article.userId }}
      </p>
    </td>
  </tr>
</template>

<style scoped></style>
