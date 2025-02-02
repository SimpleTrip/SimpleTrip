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
          path: 'admin/answer',
          name: 'QnAAnswer',
          component: () => import('@/components/QnA/QnAAnswer.vue'),
        },
      ],
    },
    {
      path: '/spots',
      name: 'spot',
      component: () => import('@/views/SpotView.vue'),
      redirect: { name: 'spotScan' },
      children: [
        {
          path: 'scan',
          name: 'spotScan',
          component: () => import('@/components/spot/SpotScan.vue'),
        },
        {
          path: 'popular',
          name: 'spotPopular',
          component: () => import('@/components/spot/SpotPopular.vue'),
        },
      ],
    },
    {
      path: '/places',
      name: 'place',
      component: () => import('@/views/PlaceView.vue'),
      redirect: { name: 'placeRegister' },
      children: [
        {
          path: 'register',
          name: 'placeRegister',
          component: () => import('@/components/place/PlaceRegister.vue'),
        },
        {
          path: '',
          name: 'placeList',
          component: () => import('@/components/place/PlaceList.vue'),
        },
        {
          path: '/places/:placeId',
          name: 'placeDetail',
          component: () => import('@/components/place/PlaceDetail.vue'),
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
        {
          path: 'findpass',
          name: 'findpass',
          component: () => import('@/components/user/UserFindPass.vue'),
        },
      ],
    },
    {
      path: '/plan',
      name: 'plan',
      redirect: { name: 'planList' },
      component: () => import('@/views/PlanView.vue'),
      children: [
        {
          path: 'list',
          name: 'planList',
          component: () => import('@/components/plan/PlanList.vue'),
        },
        {
          path: 'detail',
          name: 'planDetail',
          component: () => import('@/components/plan/PlanDetail.vue'),
        },
        {
          path: 'write',
          name: 'planWrite',
          component: () => import('@/components/plan/PlanWrite.vue'),
        },
      ],
    },
  ],
});

export default router;
