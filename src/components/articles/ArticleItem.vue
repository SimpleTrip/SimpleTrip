<script setup>
import { useRouter } from 'vue-router'

const props = defineProps({
  article: Object
})

const router = useRouter()
const routeArticleDetail = function() {
  console.log('test')
  console.log(props.article)
  console.log(props.article.articleId)
  router.push({ name: 'articleDetail', params: { articleId: props.article.articleId } })
}
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
        {{ props.article.userId }}
      </p>
    </td>
    <td>
      <div class="d-flex px-2 py-1">
        <div class="d-flex flex-column justify-content-center">
          <h6 class="mb-0 text-xs">{{ props.article.articleTitle }}</h6>
          <p class="text-xs text-secondary mb-0">{{ props.article.articleContent }}</p>
        </div>
      </div>
    </td>
  </tr>
</template>

<style scoped></style>
