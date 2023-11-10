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
      redirect: { name: 'boardList' },
      children: [
        {
          path: 'list',
          name: 'boardList',
          component: () => import('@/components/board/BoardList.vue'),
        },
        {
          path: 'write',
          name: 'boardWrite',
          component: () => import('@/components/board/BoardWrite.vue'),
        },
        {
          path: 'detail',
          name: 'boardDetail',
          component: () => import('@/components/board/BoardDetail.vue'),
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
      path: '/Map',
      name: 'map',
      component: () => import('@/views/MapView.vue'),
      redirect: { name: 'MapTest' },
      children: [
        {
          path: 'test',
          name: 'MapTest',
          component: () => import('@/components/Map/Test.vue'),
        },
      ],
    },
  ],
});

export default router;
