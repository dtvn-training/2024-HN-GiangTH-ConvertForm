import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/userStorage'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: () => import('../views/HomeView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue')
    },
    {
      path: '/history',
      name: 'history',
      component: () => import('../views/HistoryView.vue')
    },
  ]
})

// router.beforeEach((to) => {
//   const userStore = useUserStore();

//   if (!userStore.isLoggedIn && to.path !== '/login') {
//     return '/login';
//   }

//   if (userStore.isLoggedIn && to.path === '/login') {
//     return '/home';
//   }
// });

export default router
