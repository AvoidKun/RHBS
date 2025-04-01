import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
//导入组件
// import ButtonVue from './components/Button.vue';
// import ArticleVue from './components/Article.vue'
import LoginVue from '@/view/login.vue'
import layoutVue from '@/view/layout.vue';
import ArticleCategoryVue from '@/view/article/ArticleCategory.vue'
import ArticleManageVue from '@/view/article/ArticleManage.vue';
import UserAvatarVue from '@/view/user/UserAvatar.vue';
import UserInfoVue from '@/view/user/UserInfo.vue';
import UserResetPasswordVue from '@/view/user/UserResetPassword.vue';
import PhysiologicalDataManageVue from '@/view/physiologicalData/PhysiologicalDataManage.vue'
import PhysiologicalChartVue from '@/view/physiologicalData/PhysiologicalChart.vue'
//定义路由关系
const routes = [
    { path: '/login', component: LoginVue },
    {
        path: '/', component: layoutVue,redirect:"/article/category", children: [
            {
                path: '/article/category', component: ArticleCategoryVue
            },
            {
                path:'/article/manage',component:ArticleManageVue
            },
            {
                path:'/user/info',component:UserInfoVue
            },
            {
                path:'/user/avatar',component:UserAvatarVue
            },
            {
                path:'/user/resetPassword',component:UserResetPasswordVue
            },
            {
                path:'/physiologicalData/info',component:PhysiologicalDataManageVue
            },
            {
                path:'/physiologicalData/Chart',component:PhysiologicalChartVue
            }
        ]
    }

]

//创建路由器
const router = createRouter({
    history: createWebHashHistory(),
    // history:createWebHistory() ,
    routes: routes
})

//导出路由
export default router