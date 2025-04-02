<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  articleCategoryListService,
  articleListService,
  articleAddService,
  articleUpdateService,
  articleDeleteService,
} from "@/api/article.js";
const categories = ref([]);
// 文章列表数据模型
const articles = ref([]);
//分页数据
const pageNum = ref(1); //当前页
const total = ref(20); //总
const pageSize = ref(3); //每页
//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  articleList();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  articleList();
};
//用户搜索时选中的分类id
const categoryId = ref("");

//用户搜索时选中的发布状态
const state = ref("");
//文章分类
const articleCategoryList = async () => {
  let result = await articleCategoryListService();
  categories.value = result.data;
  // console.log(categories)
};
//获取文章列表数据
const articleList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    categoryId: categoryId.value ? categoryId.value : null,
    state: state.value ? state.value : null,
  };
  let result = await articleListService(params);
  //渲染
  // console.log(result.data)
  total.value = result.data.total;
  articles.value = result.data.items;

  //处理数据categoryName
  for (let i = 0; i < articles.value.length; i++) {
    let article = articles.value[i];
    for (let j = 0; j < categories.value.length; j++) {
      if (article.categoryId == categories.value[j].id) {
        article.categoryName = categories.value[j].categoryName;
      }
    }
  }
};

articleCategoryList();
articleList();
import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import { Plus } from "@element-plus/icons-vue";
//控制抽屉是否显示
const visibleDrawer = ref(false);
//添加表单数据模型
const articleModel = ref({
  title: "",
  categoryId: "",
  content: "",
  state: "",
});

//添加文章
import { ElMessage, ElMessageBox } from "element-plus";
const addArticle = async (clickState) => {
  articleModel.value.state = clickState;
  articleModel.value.content = articleModel.value.content.replace(/<p[^>]*>|<\/p>/g, "");
  let result = await articleAddService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");

  visibleDrawer.value = false;
  articleList();
};

//
const titles = ref("");
//修改文章
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改文章";
  articleModel.value.title = row.title;
  articleModel.value.categoryId = row.categoryId;
  articleModel.value.content = row.content;
  articleModel.value.id = row.id;
};
const updateArticle = async (clickState) => {
  articleModel.value.state = clickState;
  articleModel.value.content = articleModel.value.content.replace(/<p[^>]*>|<\/p>/g, "");
  // console.log(articleModel.value);
  let result = await articleUpdateService(articleModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");
  visibleDrawer.value = false;
  articleList();
};
const clearDate = () => {
  articleModel.value.title = "";
  articleModel.value.categoryId = "";
  articleModel.value.content = "<p></p>";
  articleModel.value.state = "";
};
const deleteArticle = (row) => {
  ElMessageBox.confirm("你确认要删除该文章吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await articleDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      articleList();
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
        <div><span>文章管理</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加文章';
              clearDate();
            "
            >添加文章</el-button
          >
        </div>
      </div>
    </template>

    <el-form :inline="true">
      <el-form-item label="文章分类:">
        <el-select placeholder="请选择" clearable v-model="categoryId">
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
        <el-button type="primary" @click="articleList">搜索</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
            articleList();
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table :data="articles" style="width: 100%">
      <el-table-column label="文章标题" width="260" prop="title"></el-table-column>
      <el-table-column label="分类" prop="categoryName"></el-table-column>
      <el-table-column label="发表时间" width="300" prop="createTime"> </el-table-column>
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
            @click="deleteArticle(row)"
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
      <el-form class="drawer-form" :model="articleModel" label-width="120px">
        <el-form-item label="文章标题">
          <el-input v-model="articleModel.title" placeholder="请输入标题"> </el-input>
        </el-form-item>
        <el-form-item label="文章分类" class="form-item-type">
          <el-select placeholder="请选择" v-model="articleModel.categoryId">
            <el-option
              v-for="c in categories"
              :key="c.id"
              :label="c.categoryName"
              :value="c.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="文章内容">
          <div class="editor">
            <quill-editor
              theme="snow"
              contentType="html"
              v-model:content="articleModel.content"
              style="height: 400px"
            >
            </quill-editor>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button
            type="primary"
            @click="titles == '添加文章' ? addArticle('已发布') : updateArticle('已发布')"
            >发布</el-button
          >
          <el-button
            type="info"
            @click="titles == '添加文章' ? addArticle('草稿') : updateArticle('草稿')"
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
