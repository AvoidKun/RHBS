<script setup>
import { ref } from "vue";
import { Document} from "@element-plus/icons-vue";
import {
    announcementEvaluationService
} from "@/api/announcement.js";
const evaluation = ref([]);
const state = ref("");
const titles = ref("");
//分页
const pageNum = ref(1);
const total = ref(20);
const pageSize = ref(3);
//当每页条发生了变化
const onSizeChange = (size) => {
  pageSize.value = size;
  evaluationList();
};
const onCurrentChange = (num) => {
  pageNum.value = num;
  evaluationList();
};
const visibleDrawer = ref(false);
const evaluationList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    state: state.value ? state.value : null,
  };
  let result = await announcementEvaluationService(params);
  total.value = result.data.total;
  evaluation.value = result.data.items;
  // console.log(evaluation.value);
};
evaluationList();
const evaluationModel = ref({
  type: "",
  rating: "",
  content: "",
  state: "",
});

//修改
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "查看评价";
  evaluationModel.value.type = row.type;
  evaluationModel.value.rating = row.rating;
  evaluationModel.value.content = row.content;
  evaluationModel.value.id = row.id;
};
</script>
<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <div><span>服务评价</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加评价';
              clearDate();
            "
          >
            添加评价</el-button
          >
        </div>
      </div>
    </template>
    <el-form :inline="true">
      <el-form-item label="评价状态">
        <el-select placeholder="请选择" clearable v-model="state">
          <el-option label="赞美" value="赞美" />
          <el-option label="投诉" value="投诉" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="evaluationList">搜索</el-button>
        <el-button
          @click="
            state = '';
            evaluationList()
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>
    <el-table :data="evaluation">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="评价类型" prop="type"></el-table-column>
      <el-table-column label="状态" prop="state" width="300"> </el-table-column>
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
      <el-form :Model="evaluationModel" label-width="120px">
        <el-form-item label="评价类型">
          <el-input
            v-model="evaluationModel.type"
            placeholder="请您输入需要提交的评价类型"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="评分等级">
          <el-rate v-model="evaluationModel.rating" disabled />
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input
            v-model="evaluationModel.content"
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
