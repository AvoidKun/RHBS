<script setup>
import { ref } from "vue";
import adminAdminInfoStore from "@/stores/adminInfo.js";
const adminInfoStore = adminAdminInfoStore();
const adminInfo = ref({
  ...adminInfoStore.info,
});
const rules = {
  phone: [
    { required: true, message: "请输入管理员手机号", trigger: "blur" },
    {
      pattern: /^\S{6,12}$/,
      message: "手机号必须是6-12位的非空字符串",
      trigger: "blur",
    },
  ],
  email: [
    { required: true, message: "请输入管理员邮箱", trigger: "blur" },
    { type: "email", message: "邮箱格式不正确", trigger: "blur" },
  ],
  position: [
    { required: true, message: "请输入管理员职务", trigger: "blur" },
    {
      pattern: /^\S{2,6}$/,
      message: "职务为2-6位非空字符串",
      trigger: "blur",
    },
  ],
};
//修改个人信息
import { adminInfoUpdateService,adminInfoService } from "@/api/admin.js";
import { ElMessage } from "element-plus";
// const adminList=async()=>{
//     let result =await adminInfoService();

// }
const updateAdminInfo = async () => {
  let result = await adminInfoUpdateService(adminInfo.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");

  //修改pinia
  adminInfoStore.setInfo(adminInfo.value);
};
</script>

<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>基本资料</span>
      </div>
    </template>
    <el-row>
      <el-col :span="12">
        <el-form :model="adminInfo" :rules="rules" label-width="100px" size="large">
          <el-form-item label="登录名称">
            <el-input v-model="adminInfo.adminname" disabled></el-input>
          </el-form-item>
          <el-form-item label="手机电话" prop="phone">
            <el-input v-model="adminInfo.phone"></el-input>
          </el-form-item>
          <el-form-item label="用户邮箱" prop="email">
            <el-input v-model="adminInfo.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="updateAdminInfo()">提交修改</el-button>
            <!-- <el-button @click="
            adminInfo()
            ">重置</el-button> -->
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-card>
</template>
