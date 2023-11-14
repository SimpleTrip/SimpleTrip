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
    {
      path: '/user',
      name: 'user',
      component: () => import('@/views/UserView.vue'),
      children: [
        {
          path: 'info',
          name: 'info',
          component: () => import('@/components/user/UserInfo.vue'),
        },
        {
          path: 'login',
          name: 'login',
          component: () => import('@/components/user/UserLogin.vue'),
        },
        {
          path: 'signup',
          name: 'signup',
          component: () => import('@/components/user/UserSignUp.vue'),
        },
      ],
    },
  ],
});

export default router;
