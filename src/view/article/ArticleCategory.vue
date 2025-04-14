<script setup>
import {
  articleCategoryListService,
  // articleCategoryAddService,
  // articleCategoryUpdateService,
  // articleCategoryDeleteService,
} from "@/api/article.js";
import {Document } from "@element-plus/icons-vue";
import { ref } from "vue";
const categories = ref([]);
const articleCategoryList = async () => {
  let result = await articleCategoryListService();
  categories.value = result.data;
};
articleCategoryList();

//弹窗
const dialogFormVisible = ref(false);
const categoryModel = ref({
  categoryName: "",
  categoryAlias: "",
});
const rules = {
  categoryName: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
  categoryAlias: [{ required: true, message: "请输入分类别名", trigger: "blur" }],
};

//调用接口，添加分类
import { ElMessage, ElMessageBox } from "element-plus";
const addCategory = async () => {
  let result = await articleCategoryAddService(categoryModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");
  //刷新一下
  articleCategoryList();
  dialogFormVisible.value = false;
};

//定义变量，控制标题
const title = ref("");
//展示编辑弹窗
const showDialog = (row) => {
  dialogFormVisible.value = true;
  title.value = "编辑分类";
  categoryModel.value.categoryName = row.categoryName;
  // console.log(categoryModel.value.categoryName)
  categoryModel.value.categoryAlias = row.categoryAlias;
  categoryModel.value.id = row.id;
};

// //编辑分类
// const updateCategory = async () => {
//   //调用接口
//   let result = await articleCategoryUpdateService(categoryModel.value);
//   ElMessage.success(result.msg ? result.msg : "更改成功");
//   articleCategoryList();
//   dialogFormVisible.value = false;
// };
// //清空数据
// const clearData = () => {
//   categoryModel.value.categoryName = "";
//   categoryModel.value.categoryAlias = "";
// };
// //删除
// const deleteCategory = (row) => {
//   ElMessageBox.confirm("你确认要删除该分类信息吗？", "温馨提示", {
//     confirmButtonText: "确认",
//     cancelButtonText: "取消",
//     type: "warning",
//   })
//     .then(async () => {
//       //调用接口
//       let result = await articleCategoryDeleteService(row.id);
//       ElMessage({
//         type: "success",
//         message: "删除成功",
//       });
//       //重置
//       articleCategoryList();
//     })
//     .catch(() => {
//       ElMessage({
//         type: "info",
//         message: "取消了删除",
//       });
//     });
// };
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div>文章分类</div>
        <div>
          <!-- <el-button
            type="primary"
            round
            @click="
              dialogFormVisible = true;
              title = '添加分类';
              clearData();
            "
            >添加分类</el-button
          > -->
        </div>
      </div>
    </template>

    <el-table :data="categories" style="width: 100%">
      <el-table-column type="index" label="序号" width="100" />
      <el-table-column prop="categoryName" label="分类名称" />
      <el-table-column prop="categoryAlias" label="分类别名" />
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
  </el-card>

  <!-- <el-dialog v-model="dialogFormVisible" width="30%" :title="title">
    <el-form
      :model="categoryModel"
      :rules="rules"
      lable-width="100px"
      style="padding-right: 30px"
    >
      <el-form-item label="分类名字" prop="categoryName">
        <el-input
          v-model="categoryModel.categoryName"
          autocomplete="off"
          minlenth="1"
          maxlength="10"
        />
      </el-form-item>
      <el-form-item label="分类别名" prop="categoryAlias">
        <el-input
          v-model="categoryModel.categoryAlias"
          autocomplete="off"
          minlenth="1"
          maxlength="10"
        /> -->
      <!-- </el-form-item> -->
      <!-- <el-select v-model="form.region" placeholder="Please select a zone">
                <el-option label="Zone No.1" value="shanghai" />
                <el-option label="Zone No.2" value="beijing" />
            </el-select> -->
    <!-- </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="title == '添加分类' ? addCategory() : updateCategory()"
          >确定</el-button
        >
      </span>
    </template>
  </el-dialog> -->
</template>
<style  scoped>
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
</style>
