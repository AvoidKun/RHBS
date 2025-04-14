<script setup>
import { ref } from "vue";
import {Document} from "@element-plus/icons-vue";
import {
    evaluationMedicinesService
} from "@/api/evaluation.js";
const medicines = ref([]);
const state = ref("");
const titles = ref("");
//分页
const pageNum = ref(1);
const total = ref(20);
const pageSize = ref(3);
const onSizeChange = (size) => {
  pageSize.value = size;
  medicinesList();
};
const onCurrentChange = (num) => {
  pageNum.value = num;
  medicinesList();
};
const visibleDrawer = ref(false);
const medicinesList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    state: state.value ? state.value : null,
  };
  let result = await evaluationMedicinesService(params);
  total.value = result.data.total;
  medicines.value = result.data.items;
};
medicinesList();

const medicinesModel = ref({
  medicinename: "",
  genericname: "",
  description: "",
  state: "",
});

//改
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改药品";
  medicinesModel.value.medicinename = row.medicinename;
  medicinesModel.value.genericname = row.genericname;
  medicinesModel.value.description = row.description;
  medicinesModel.value.id = row.id;
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>药物数据</span></div>
      </div>
    </template>
    <el-form :inline="true">
      <el-form-item label="药品状态">
        <el-select placeholder="请选择" clearable v-model="state">
          <el-option label="盈余" value="盈余" />
          <el-option label="告罄" value="告罄" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="medicinesList">搜索</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
            medicinesList();
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-table :data="medicines">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="药品名" prop="medicinename"></el-table-column>
      <el-table-column label="俗名" prop="genericname"></el-table-column>
      <el-table-column label="状态" prop="state"> </el-table-column>
      <el-table-column label="发布时间" prop="createTime"></el-table-column>
      <el-table-column label="操作" width="120">
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
    </el-table>

    <el-pagination
      v-model:current-page="pageNum"
      v-model:page-size="pageSize"
      :page-sizes="[3, 5, 10, 15]"
      :size="size"
      :disable="disabled"
      :backgroud="backgroud"
      layout="jumper, total,sizes,prev,pager,next"
      :total="total"
      @size-change="onSizeChange"
      @current-change="onCurrentChange"
      style="margin-top: 20px; justify-content: flex-end"
    />

    <el-drawer v-model="visibleDrawer" :title="titles" size="40%">
      <el-form :Model="medicinesModel" label-width="120px">
        <el-form-item label="药品名称">
          <el-input
            v-model="medicinesModel.medicinename"
            placeholder="请您输入需要提交的药品名称"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="药品别名">
          <el-input
            v-model="medicinesModel.genericname"
            placeholder="请您输入需要提交的药品的别名"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="药品效果">
          <el-input
            v-model="medicinesModel.description"
            style="width: 100%"
            :rows="2"
            type="textarea"
            disabled
          ></el-input>
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
.el-drawer .el-form {
  flex-flow: column nowrap;
  width: 100%;
}
.el-drawer .el-form-item {
  width: 95%;
}
</style>
