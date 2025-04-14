<script setup>
import { ref } from "vue";
import { Document } from "@element-plus/icons-vue";
import {
evaluationAdviceService
} from "@/api/evaluation.js";
import{
    userInfoService
}from "@/api/user"
//用户列表数据
const users = ref([]);
//建议数据
const advices = ref([]);
//分页数据
const pageNum = ref(1); //当前页
const total = ref(20); //总
const pageSize = ref(3); //每页
const onSizeChange = (size) => {
  pageSize.value = size;
  adviceList();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  adviceList();
};
//管理员输入用户的名字
//think....
const userId = ref("");
//管理员搜索选中的发布状态
const state = ref("");
const userInfo = async () => {
  let result = await userInfoService();
  users.value = result.data;
  // console.log(users.value)
};

const userNames = ref("");
const adviceList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    userId: userId.value ? userId.value : null,
    state: state.value ? state.value : null,
  };
  let result = await evaluationAdviceService(params);
  //   console.log(result.data)
  total.value = result.data.total;
  advices.value = result.data.items;
};
userInfo();
adviceList();

import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
//控制抽屉是否显示
const visibleDrawer = ref(false);
const adviceModel = ref({
  title: "",
  content: "",
  state: "",
});
//查
const titles = ref("");
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改建议";
  adviceModel.value.title = row.title;
  adviceModel.value.content = row.content;
  adviceModel.value.id = row.id;
};
const  isReadOnly=ref(true)
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>健康建议</span></div>
      </div>

    </template>
    <el-table :data="advices" style="width: 100%">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="建议标题" prop="title"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
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
    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      :size="size"
      :disabled="disabled"
      :background="background"
      layout="jumper, total, sizes, prev, pager, next"
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />

    <el-drawer v-model="visibleDrawer" :title="titles" direction="rtl" size="40%">
      <el-form class="drawer-form" :model="adviceModel" label-width="120px">
        <el-form-item label="公告标题">
          <el-input v-model="adviceModel.title" placeholder="请输入标题" disabled> </el-input>
        </el-form-item>
        <el-form-item label="建议内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              contentType="html"
              v-model:content="adviceModel.content"
              style="height: 400px"
               :readOnly="isReadOnly"
            >
            </quill-editor>
          </div>
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
.drawer-form {
  flex-flow: column nowrap;
  width: 100%;
}
.el-drawer .el-form {
  width: 100%;
}
.el-drawer .el-form .el-form-item {
  margin-top: 20px;
  width: 100%;
}
.form-item-type .el-select {
  width: 60%;
}
.editor {
  width: 100%;
}
.ql-ditor {
  min-height: 200px;
}
</style>
