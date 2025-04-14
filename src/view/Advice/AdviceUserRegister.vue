<script setup>
import { ref } from "vue";
import {Document, Delete } from "@element-plus/icons-vue";
import { adviceUserRegisterService, adviceUserInfoService } from "@/api/advice.js";


//用户列表数据
const users = ref([]);
//建议数据
const advices = ref([]);

//管理员输入用户的名字
//think....
const userId = ref("");
//管理员搜索选中的发布状态
const state = ref("");
const userInfo = async () => {
  let result = await adviceUserInfoService();
  users.value = result.data;
};

const userNames = ref("");

userInfo();

//控制抽屉是否显示
const visibleDrawer = ref(false);
const userModel = ref({
  username: "",
  nickname: "",
  age: "",
  birth: "",
  sex: "",
  email: "",
  phone: "",
});

//注册
const register = async () => {
  let result = await adviceUserRegisterService(userModel.value);
  ElMessage.success(result.msg ? result.msg : "注册成功");
};

//改

const titles = ref("");

const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改信息";
  userModel.value.username = row.username;
  userModel.value.nickname = row.nickname;
  userModel.value.age = row.age;
  userModel.value.birth = row.birth;
  userModel.value.sex = row.sex;
  userModel.value.email = row.email;
  userModel.value.phone = row.phone;
  userModel.value.id = row.id;
};

const clearDate = () => {
  userModel.value.username = "";
  userModel.value.nickname = "";
  userModel.value.age = "";
  userModel.value.birth = "";
  userModel.value.sex = "";
  userModel.value.email = "";
  userModel.value.phone = "";
};

const userLists = () => {
  if (userNames.value !== "") {
    // 使用find方法查找匹配的用户
    const foundUser = users.value.find((user) => user.username === userNames.value);

    if (foundUser) {
      // 如果找到匹配用户，更新users数组（这里根据您的需求可能需要调整）
      users.value = [foundUser]; // 如果只需要保留匹配的用户
      // 或者如果只需要返回匹配的用户：
      // return foundUser;
    } else {
      // 如果没有找到匹配用户，可以清空数组或保持原样
      users.value = [];
      // 或者显示未找到的提示
      ElMessage({
        type: "info",
        message: "未发现该村民",
      });
    }
  }
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>村民信息</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加村民';
              clearDate();
            "
            >添加村民</el-button
          >
        </div>
      </div>
      <el-form :inline="true">
        <el-form-item label="村民:">
          <el-input placeholder="请输入" clearable v-model="userNames"> </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="userLists">搜索</el-button>
          <el-button
            @click="
              userId = '';
              userNames = '';
              state = '';
              userinfo();
            "
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </template>
    <el-table :data="users" style="width: 100%">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="村民" prop="username"></el-table-column>
      <el-table-column label="电话号码" prop="phone"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"> </el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Document"
            circle
            plain
            type="primary"
            @click="showDrawer(row)"
          ></el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="没有数据" />
      </template>
    </el-table>

    <el-drawer v-model="visibleDrawer" :title="titles" direction="rtl" size="40%">
      <el-form
        class="extraForm"
        :model="users"
        :rules="rules"
        label-width="100px"
        size="large"
      >
        <el-form-item label="登录名称">
          <el-input v-model="userModel.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="村民昵称" prop="nickname">
          <el-input v-model="userModel.nickname" disabled></el-input>
        </el-form-item>
        <el-form-item label="村民年龄" prop="age">
          <el-input v-model="userModel.age" disabled></el-input>
        </el-form-item>
        <el-form-item label="村民出生日期" prop="birth">
          <el-input v-model="userModel.birth" disabled></el-input>
        </el-form-item>
        <el-form-item label="村民性别" prop="sex">
          <el-input v-model="userModel.sex" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户邮箱" prop="email">
          <el-input v-model="userModel.email" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户电话" prop="phone">
          <el-input v-model="userModel.phone" disabled></el-input>
        </el-form-item>
      </el-form>
    </el-drawer>
  </el-card>
</template>
<style scoped>
.page-container {
  width: 99%;
  height: 99%;
  box-sizing: border-box;
}
.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.el-form {
  width: 100%;
  display: flex;
  align-items: center;
}
.el-form-item {
  width: 260px;
}
.extraForm {
  flex-flow: column nowrap;
  width: 100%;
}
.extraForm .el-form-item {
  width: 95%;
}
</style>
