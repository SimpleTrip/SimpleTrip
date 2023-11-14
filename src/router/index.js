import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: () => import('@/views/MainView.vue'),
    },
    {
      path: '/articles',
      name: 'articles',
      component: () => import('@/views/ArticleView.vue'),
      redirect: { name: 'articleList' },
      children: [
        {
          path: 'write',
          name: 'articleWrite',
          component: () => import('@/components/articles/ArticleWrite.vue'),
        },
        {
          path: '/articles/:articleId',
          name: 'articleDetail',
          component: () => import('@/components/articles/ArticleDetail.vue'),
        },
        {
          path: '',
          name: 'articleList',
          component: () => import('@/components/articles/ArticleList.vue'),
        },
        {
          path: '/modify/:articleId',
          name: 'articleModify',
          component: () => import('@/components/articles/ArticleModify.vue'),
        },
      ],
    },
    {
      path: '/QnA',
      name: 'QnA',
      component: () => import('@/views/QnAView.vue'),
      redirect: { name: 'QnAList' },
      children: [
        {
          path: 'list',
          name: 'QnAList',
          component: () => import('@/components/QnA/QnAList.vue'),
        },
        {
          path: 'detail',
          name: 'QnADetail',
          component: () => import('@/components/QnA/QnADetail.vue'),
        },
        {
          path: 'write',
          name: 'QnAWrite',
          component: () => import('@/components/QnA/QnAWrite.vue'),
        },
        {
          path: 'modify',
          name: 'QnAModify',
          component: () => import('@/components/QnA/QnAModify.vue'),
        },
        {
          path: 'admin/answer',
          name: 'QnAAnswer',
          component: () => import('@/components/QnA/QnAAnswer.vue'),
        },
      ],
    },
    {
      path: '/spot',
      name: 'spot',
      component: () => import('@/views/SpotView.vue'),
      redirect: { name: 'spotSearch' },
      children: [
        {
          path: 'search',
          name: 'spotSearch',
          component: () => import('@/components/spot/spotSearch.vue'),
        },
        {
          path: 'register',
          name: 'spotRegister',
          component: () => import('@/components/spot/spotRegister.vue'),
        },
      ],
    },
  ],
});

export default router;
