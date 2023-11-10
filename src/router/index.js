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
          path: 'detail',
          name: 'detail',
          component: () => import('@/components/board/BoardDetail.vue'),
        },
      ],
    },
  ],
});

export default router;
