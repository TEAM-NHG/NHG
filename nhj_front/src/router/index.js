// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from "@/stores/auth";
import HomeView from '../views/HomeView.vue'
import CurationView from '../views/CurationView.vue'
import NewsView from '../views/NewsView.vue'
import PlannerView from '../views/PlannerView.vue'
import ProfileView from '../views/ProfileView.vue'
import SignupView from '@/views/SignupView.vue'
import LoginView from '@/views/LoginView.vue'
import PlannerCard from '@/components/users/PlannerCard.vue'
import ProfileComment from '@/components/users/ProfileComment.vue'
import AdminView from '@/views/AdminView.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/curation',
      name: 'curation',
      component: CurationView
    },
    {
      path: '/news/:word?',
      name: 'news',
      component: NewsView
    },
    {
      path: "/board/:word?",
      name: "board",
      // component: TheBoardView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import("../views/TheBoardView.vue"),
      redirect: { name: "article-list" },
      children: [
        {
          path: "list",
          name: "article-list",
          component: () => import("@/components/boards/BoardList.vue"),
        },
        {
          path: "view/:articleNo",
          name: "article-view",
          component: () => import("@/components/boards/BoardDetail.vue"),
        },
        {
          path: "write",
          name: "article-write",
          component: () => import("@/components/boards/BoardWrite.vue"),
        },
        {
          path: "modify/:articleno",
          name: "article-modify",
          component: () => import("@/components/boards/BoardModify.vue"),
        },
      ],
    },
    {
      path: '/planner',
      name: 'planner',
      component: PlannerView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      redirect: { name: 'my-travels' }, // 기본 리다이렉션 추가
      children: [
        {
          path: '/my-travels',
          name: 'my-travels', // name 추가
          component: PlannerCard
        },
        {
          path: '/notifications',
          name: 'notifications', // name 추가
          component: ProfileComment
        }
      ]
    },
    {
      path: '/signup',
      name: 'signup',
      component: SignupView
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/admin',
      name: 'admin',
      component: AdminView
    },
  ]
})

// Global Router Guard
router.beforeEach(async (to, from, next) => {
  const publicPages = ['home', 'article-list', 'signup', 'login', 'news']; // 로그인 필요 없는 페이지
  const authRequired = !publicPages.includes(to.name); // 로그인 필요한 경우
  const authStore = useAuthStore(); // auth.js 상태 접근
  await authStore.checkLoginStatus()

  if (authRequired && !authStore.isLoggedIn) {
    // 로그인이 필요한 페이지인데 로그인 안 되어있으면
    next({ name: 'home' }); //메인 페이지로 리다이렉트
    alert('로그인이 필요한 페이지입니다!')
  } else {
    next(); // 접근 허용
  }
});

export default router
