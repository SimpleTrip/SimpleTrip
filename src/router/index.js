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
      path: '/board',
      name: 'board',
      component: () => import('@/views/BoardView.vue'),
      redirect: { name: 'list' },
      children: [
        {
          path: 'list',
          name: 'list',
          component: () => import('@/components/board/BoardList.vue'),
        },
        {
          path: 'write',
          name: 'write',
          component: () => import('@/components/board/BoardWrite.vue'),
        },
        {
          path: 'detail',
          name: 'detail',
          component: () => import('@/components/board/BoardDetail.vue'),
        },
      ],
    },
    {
      path: '/QnA',
      name: 'QnA',
      component: () => import('@/views/QnAView.vue'),
      redirect: { name: 'QnAlist' },
      children: [
        {
          path: 'list',
          name: 'QnAlist',
          component: () => import('@/components/QnA/QnAList.vue'),
        },
        {
          path: 'detail',
          name: 'QnAdetail',
          component: () => import('@/components/QnA/QnADetail.vue'),
        },
      ],
    },
  ],
});

export default router;
