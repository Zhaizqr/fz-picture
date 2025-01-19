import { createRouter, createWebHistory } from 'vue-router';

// 导入页面组件
import Home from '../views/Home.vue';
import About from '../views/About.vue';

// 定义路由
const routes = [
    {
        path: '/',
        name: 'Home',
        component: Home,
    },
    {
        path: '/about',
        name: 'About',
        component: About,
    },
];

// 创建路由实例
const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
