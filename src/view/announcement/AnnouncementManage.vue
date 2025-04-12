<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  announcementCategoryListService,
  announcementListService,
  announcementAddService,
  announcementUpdateService,
  announcementDeleteService,
} from "@/api/announcement.js";
// import {
//     userInfoUpdateService
// }from '@/api/user.js'
const categories = ref([]);
// 文章列表数据模型
const announcements = ref([]);
//分页数据
const pageNum = ref(1); //当前页
const total = ref(20); //总
const pageSize = ref(3); //每页
const onSizeChange = (size) => {
  pageSize.value = size;
  announcementList();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  announcementList();
};

// const userId = ref("");
//管理员搜索时选中的分类id
const announcementCategoryId = ref("");
//用户搜索时选中的发布状态
const state = ref("");
const announcementCategoryList = async () => {
  let result = await announcementCategoryListService();
  categories.value = result.data;
};
const announcementList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    announcementCategoryId: announcementCategoryId.value
      ? announcementCategoryId.value
      : null,
    state: state.value ? state.value : null,
  };
  let result = await announcementListService(params);
  // console.log(result.data)
  total.value = result.data.total;
  announcements.value = result.data.items;
  // console.log(announcements.value)
  for (let i = 0; i < announcements.value.length; i++) {
    let announcement = announcements.value[i];
    for (let j = 0; j < categories.value.length; j++) {
      if (announcement.announcementCategoryId == categories.value[j].id) {
        announcement.categoryName = categories.value[j].categoryName;
        // console.log(announcement);
      }
    }
  }

};
announcementCategoryList();
announcementList();
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
//控制抽屉是否显示
const visibleDrawer = ref(false);
const announcementModel = ref({
  title: "",
  announcementCategoryId: "",
  content: "",
  state: "",
});

//添加公告
import { ElMessage, ElMessageBox } from "element-plus";
const addAnnouncement = async (clickState) => {
  announcementModel.value.state = clickState;
  announcementModel.value.content = announcementModel.value.content.replace(
    /<p[^>]*>|<\/p>/g,
    ""
  );
  let result = await announcementAddService(announcementModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");

  visibleDrawer.value = false;
  announcementList();
};
const titles = ref("");
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改公告";
  announcementModel.value.title = row.title;
  announcementModel.value.announcementCategoryId = row.announcementCategoryId;
  announcementModel.value.content = row.content;
  announcementModel.value.id = row.id;
};
const updateAnnouncement = async(clickState)=>{
  announcementModel.value.state = clickState;
  announcementModel.value.content = announcementModel.value.content.replace(/<p[^>]*>|<\/p>/g, "")
  result=await announcementUpdateService(announcementModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");
  visibleDrawer.value = false;
  announcementList();
}
const clearDate = () => {
  announcementModel.value.title = "";
  announcementModel.value.announcementCategoryId = "";
  announcementModel.value.content = "<p></p>";
  announcementModel.value.state = "";
};
const deleteAnnouncement = (row) => {
  ElMessageBox.confirm("你确认要删除该公告吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await announcementDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      announcementList();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "取消删除",
      });
    });
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>公告管理</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加公告';
              clearDate();
            "
            >添加公告</el-button
          >
        </div>
      </div>
      <el-form :inline="true">
        <el-form-item label="公告分类:">
          <el-select placeholder="请选择" clearable v-model="announcementCategoryId">
            <el-option
              v-for="c in categories"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="发布状态:">
          <el-select placeholder="请选择" clearable v-model="state">
            <el-option label="已发布" value="已发布" />
            <el-option label="草稿" value="草稿" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="announcementList">搜索</el-button>
          <el-button
            @click="
              announcementCategoryId = '';
              state = '';
              announcementList();
            "
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </template>
    <el-table :data="announcements" style="width: 100%">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="公告标题" prop="title"></el-table-column>
      <el-table-column label="公告类型" prop="categoryName"></el-table-column>
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
            @click="deleteAnnouncement(row)"
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
      <el-form class="drawer-form" :model="announcementModel" label-width="120px">
        <el-form-item label="公告标题">
          <el-input v-model="announcementModel.title" placeholder="请输入标题">
          </el-input>
        </el-form-item>
        <el-form-item label="公告分类" class="form-item-type">
          <el-select placeholder="请选择" v-model="announcementModel.announcementCategoryId">
            <el-option
              v-for="c in categories"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公告内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              contentType="html"
              v-model:content="announcementModel.content"
              style="height: 400px"
            >
            </quill-editor>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="
              titles == '添加公告'
                ? addAnnouncement('已发布')
                : updateAnnouncement('已发布')
            "
            >发布</el-button
          >
          <el-button
            type="info"
            @click="
              titles == '添加公告' ? addAnnouncement('草稿') : updateAnnouncement('草稿')
            "
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
