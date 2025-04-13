<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  adviceListService,
  adviceAddService,
  adviceUpdateService,
  adviceDeleteService,
  adviceUserInfoService,
} from "@/api/advice.js";

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
  let result = await adviceUserInfoService();
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
  let result = await adviceListService(params);
  //   console.log(result.data)
  total.value = result.data.total;
  advices.value = result.data.items;
  for (let i = 0; i < advices.value.length; i++) {
    let advice = advices.value[i];
    for (let j = 0; j < users.value.length; j++) {
      if (advice.userId == users.value[j].id) {
        advice.userName = users.value[j].username;
      }
    }
  }
};
userInfo();
adviceList();

import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
//控制抽屉是否显示
const visibleDrawer = ref(false);
const adviceModel = ref({
  title: "",
  userId: "",
  content: "",
  state: "",
});

//增
import { ElMessage, ElMessageBox } from "element-plus";
const addAdvice = async (clickState) => {
  adviceModel.value.state = clickState;
  adviceModel.value.content = adviceModel.value.content.replace(/<p[^>]*>|<\/p>/g, "");
  let result = await adviceAddService(adviceModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");

  visibleDrawer.value = false;
  adviceList();
};
//改
const titles = ref("");
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改建议";
  adviceModel.value.title = row.title;
  adviceModel.value.userId = row.userId;
  adviceModel.value.content = row.content;
  adviceModel.value.id = row.id;
};
const updateAdvice = async (clickState) => {
  adviceModel.value.state = clickState;
  adviceModel.value.content = adviceModel.value.content.replace(/<p[^>]*>|<\/p>/g, "");
  let result = await adviceUpdateService(adviceModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");
  visibleDrawer.value = false;
  adviceList();
};
const clearDate = () => {
  adviceModel.value.title = "";
  adviceModel.value.userId = "";
  adviceModel.value.content = "<p></p>";
  adviceModel.value.state = "";
};
//删
const deleteAdvice = (row) => {
  ElMessageBox.confirm("你确认要删除该建议吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await adviceDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      adviceList();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};
const adviceLists = () => {
  if (userNames.value != "") {
    for (let h = 0; h < users.value.length; h++) {
      if (userNames.value == users.value[h].username) {
        userId.value = users.value[h].id;
        adviceList();
      }
    }
  } else {
    adviceList();
  }
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>健康建议</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加建议';
              clearDate();
            "
            >添加建议</el-button
          >
        </div>
      </div>
      <el-form :inline="true">
        <el-form-item label="被建议人:">
          <el-input placeholder="请输入" clearable v-model="userNames"> </el-input>
        </el-form-item>
        <el-form-item label="发布状态:">
          <el-select placeholder="请选择" clearable v-model="state">
            <el-option label="已发布" value="已发布" />
            <el-option label="草稿" value="草稿" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="adviceLists">搜索</el-button>
          <el-button
            @click="
              userId = '';
              userNames='';
              state = '';
              adviceList();
            "
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </template>
    <el-table :data="advices" style="width: 100%">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="建议标题" prop="title"></el-table-column>
      <el-table-column label="被建议人" prop="userName"></el-table-column>
      <el-table-column label="发表时间" prop="createTime"> </el-table-column>
      <el-table-column label="状态" prop="state"></el-table-column>
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button
            :icon="Edit"
            circle
            plain
            type="primary"
            @click="showDrawer(row)"
          ></el-button>
          <el-button
            :icon="Delete"
            circle
            plain
            type="danger"
            @click="deleteAdvice(row)"
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
          <el-input v-model="adviceModel.title" placeholder="请输入标题"> </el-input>
        </el-form-item>
        <el-form-item label="被建议人" class="form-item-type">
          <el-select placeholder="请选择" v-model="adviceModel.userId">
            <el-option
              v-for="c in users"
              :key="c.id"
              :label="c.username"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="建议内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              contentType="html"
              v-model:content="adviceModel.content"
              style="height: 400px"
            >
            </quill-editor>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="titles == '添加建议' ? addAdvice('已发布') : updateAdvice('已发布')"
            >发布</el-button
          >
          <el-button
            type="info"
            @click="titles == '添加建议' ? addAdvice('草稿') : updateAdvice('草稿')"
            >草稿</el-button
          >
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
