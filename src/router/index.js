import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
//导入组件
// import ButtonVue from './components/Button.vue';
// import ArticleVue from './components/Article.vue'
import LoginVue from '@/view/login.vue'
import layoutVue from '@/view/layout.vue';
import AdminVue from '@/view/admin.vue'
import ArticleCategoryVue from '@/view/article/ArticleCategory.vue'
import ArticleManageVue from '@/view/article/ArticleManage.vue';
import UserAvatarVue from '@/view/user/UserAvatar.vue';
import UserInfoVue from '@/view/user/UserInfo.vue';
import UserResetPasswordVue from '@/view/user/UserResetPassword.vue';
import PhysiologicalDataManageVue from '@/view/physiologicalData/PhysiologicalDataManage.vue'
import PhysiologicalChartVue from '@/view/physiologicalData/PhysiologicalChart.vue'
import EvaluationManageVue from '@/view/evaluation/EvaluationManage.vue';
import UserMedicinesVue from '@/view/medicines/UserMedicines.vue';
import UserAnnouncementVue from '@/view/medicines/UserAnnouncement.vue';
import AdviceUserVue from '@/view/Advice/AdviceUser.vue';


import AdminCategoryVue from '@/view/category/AdminCategory.vue';
import AdminAvatarVue from '@/view/admin/AdminAvatar.vue';
import AdminInfoVue from '@/view/admin/AdminInfo.vue';
import AdminResetpasswordVue from '@/view/admin/AdminResetpassword.vue';
import AdminMedicinesVue from '@/view/medicines/AdminMedicines.vue';
import AnnouncementCategoryVue from '@/view/announcement/AnnouncementCategory.vue';
import AnnouncementManageVue from '@/view/announcement/AnnouncementManage.vue';
import AdviceAdminVue from '@/view/Advice/AdviceAdmin.vue';
import PhysiologicalAdminVue from '@/view/physiologicalData/PhysiologicalAdmin.vue';
import EvaluationAdminVue from '@/view/evaluation/EvaluationAdmin.vue';
import AdviceUserRegisterVue from '@/view/Advice/AdviceUserRegister.vue';
import AdvicePhysiologicalVue from '@/view/Advice/AdvicePhysiological.vue';
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
            },
            {
                path:'/evaluation/manage',component:EvaluationManageVue
            },
            {
                path:'/user/medicines',component:UserMedicinesVue
            },
            {
                path:'/user/announcement',component:UserAnnouncementVue
            },
            {
                path:'/user/advice',component:AdviceUserVue
            }
        ]
    },
    {
        path:'/admin',component:AdminVue,redirect:"/admin/category", children: [
            {
                path:'/admin/category',component:AdminCategoryVue
            },
            {
                path:'/admin/info',component:AdminInfoVue
            },
            {
                path:'/admin/avatar',component:AdminAvatarVue
            },
            {
                path:'/admin/resetPassword',component:AdminResetpasswordVue
            },
            {
                path:'/admin/medicines',component:AdminMedicinesVue
            },
            {
                path:'/admin/announcementCategory',component:AnnouncementCategoryVue
            },
            {
                path:'/admin/announcementManage',component:AnnouncementManageVue
            },
            {
                path:'/admin/advice',component:AdviceAdminVue
            },
            {
                path:'/admin/physiological',component:PhysiologicalAdminVue
            },
            {
                path:'/admin/evaluation',component:EvaluationAdminVue
            },
            {
                path:'/admin/register',component:AdviceUserRegisterVue
            },
            {
                path:'/admin/physiologicalState',component:AdvicePhysiologicalVue
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