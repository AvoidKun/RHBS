<script setup>
import {
  Management,
  Promotion,
  UserFilled,
  User,
  Crop,
  EditPen,
  SwitchButton,
  CaretBottom,
  Message,
} from "@element-plus/icons-vue";

import avatar from "@/assets/3.jpg";
import { adminInfoService } from "@/api/admin.js";
import adminAdminInfoStore from '@/stores/adminInfo.js'
import { useTokenStore } from '@/stores/token.js'
const tokenStore = useTokenStore();
const adminInfoStore = adminAdminInfoStore();
const getAdminInfo = async () => {
  let result = await adminInfoService();
  adminInfoStore.setInfo(result.data);
};
getAdminInfo();
import { useRouter } from "vue-router";
const router = useRouter();
import { ElMessage, ElMessageBox } from "element-plus";
const handleCommand = (command) => {
  //判断
  if (command === "logout") {
    ElMessageBox.confirm("你确认要退出登录吗？", "温馨提示", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        //清空用户信息
        tokenStore.removeToken();
        adminInfoStore.removeInfo();
        //回到登录页面
        router.push("/login");
        ElMessage({
          type: "success",
          message: "退出登录成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "管理员取消了退出登录",
        });
      });
  } else {
    router.push("/admin/" + command);
  }
};
</script>
<template>
  <el-container class="common-layout">
    <el-aside width="20%">
      <div class="aside-logo"></div>
      <!-- <h3>村民</h3> -->
      <el-menu active-text-color="#ffd04b" background-color="#68848E" text-color="#fff" router>
        <el-menu-item index="/admin/category">
          <el-icon>
            <Management />
          </el-icon>
          <span>文章分类</span>
        </el-menu-item>
        <!-- <el-menu-item>
          <el-icon>
            <EditPen />
          </el-icon>
          <span>健康数据</span>
        </el-menu-item>
        <el-menu-item>
          <el-icon>
            <EditPen />
          </el-icon>
          <span>健康档案</span>
        </el-menu-item>
        <el-menu-item>
          <el-icon>
            <Promotion />
          </el-icon>
          <span>文章管理</span>
        </el-menu-item> -->
        <el-sub-menu>
          <template #title>
            <el-icon>
              <UserFilled />
            </el-icon>
            <span>个人中心</span>
          </template>
          <!-- <el-menu-item-group> 小分组-->
          <el-menu-item index="/admin/info">
            <el-icon>
              <User />
            </el-icon>
            <span>基本资料</span>
          </el-menu-item>
          <!-- </el-menu-item-group> -->
          <el-menu-item index="/admin/avatar"> 
            <el-icon>
              <Crop />
            </el-icon>
            <span>更换头像</span>
          </el-menu-item>
          <el-menu-item index="/admin/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
            <span>重置密码</span>
          </el-menu-item>
        </el-sub-menu>
        <!-- <el-menu-item>
          <el-icon><Message /></el-icon>
          <span>服务评价</span>
        </el-menu-item> -->
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div>
          居民:<strong>{{ adminInfoStore.info.adminname }}</strong>
        </div>
        <!--command需要和路由名字绑定-->
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <span class="el-dropdown-box">
            <el-avatar :src="avatar" />
            <el-icon>
              <CaretBottom />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="Info" :icon="User">基本资料</el-dropdown-item>
              <el-dropdown-item command="avatar" :icon="Crop">更换头像</el-dropdown-item>
              <el-dropdown-item command="resetPassword" :icon="EditPen"
                >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
                >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <el-main>
        <!-- <div style="width:98%;height:80vh;border:1px solid red">
                    内容
                </div> -->
        <RouterView></RouterView>
      </el-main>

      <el-footer>Avoid</el-footer>
    </el-container>
  </el-container>
</template>

<style scoped>
.common-layout {
  height: 100vh;
  background-color: #f5f5f5;
}

.el-aside {
  background-color: #68848e;
  height: 100vh;
}

.aside-logo {
  height: 30%;
  background: url("@/assets/2.jpg") no-repeat;
  background-position: 50% 50%;
  background-size: cover;
}

.el-menu {
  border-right: none;
}

.el-header {
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.el-dropdown-box {
  display: flex;
  align-items: center;
}

.el-icon {
  color: #999;
  margin-left: 10px;
}
.el-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  color: #666;
}
</style>
