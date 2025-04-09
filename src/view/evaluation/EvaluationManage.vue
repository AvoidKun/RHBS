<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  evaluationListService,
  evaluationAddService,
  evaluationUpdateService,
  evaluationDeleteService,
} from "@/api/evaluation.js";
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
  let result = await evaluationListService(params);
  total.value = result.data.total;
  evaluation.value = result.data.items;
  console.log(evaluation.value);
};

const evaluationModel = ref({
  type: "",
  rating: "",
  content: "",
  state: "",
});
//添加数据
import { ElMessage, ElMessageBox } from "element-plus";
const addEvaluation = async (clickState) => {
  evaluationModel.value.state = clickState;
  let result = await evaluationAddService(evaluationModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");
  visibleDrawer.value = false;
  evaluationList();
};
evaluationList();

//修改
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改评价";
  evaluationModel.value.type = row.type;
  evaluationModel.value.rating = row.rating;
  evaluationModel.value.content = row.content;
  evaluationModel.value.id = row.id;
};
const updateEvaluation = async(clickState)=>{
    evaluationModel.value.state = clickState;
    let result = await evaluationUpdateService(evaluationModel.value);
    ElMessage.success(result.msg ? result.msg : "修改成功");
    visibleDrawer.value = false;
    evaluationList();
}
const clearDate = () => {
 evaluationModel.value.type=""
 evaluationModel.value.rating=""
 evaluationModel.value.content=""
 evaluationModel.value.state=""
};
//删除
const deleteDate = (row)=>{
    ElMessageBox.confirm("你确认要删除该文章吗？","温馨提示",{
        confirmButtonText:'确认',
        cancelButtonText:'取消',
        type:"warning"
    })
    .then(async()=>{
        let result = await evaluationDeleteService(row.id);
        ElMessage({
            type:"success",
            message:"删除成功",
        });
        evaluationList();
    })
    .catch(()=>{
        ElMessage({
            type:"info",
            message:"取消删除"
        })
    })
}
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
            categoryId = '';
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
            @click="deleteDate(row)"
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
          ></el-input>
        </el-form-item>
        <el-form-item label="评分等级">
          <el-rate v-model="evaluationModel.rating" />
        </el-form-item>
        <el-form-item label="评论内容">
          <el-input
            v-model="evaluationModel.content"
            style="width: 100%"
            :rows="2"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="
              titles == '添加评价' ? addEvaluation('赞美') : updateEvaluation('赞美')
            "
            >赞美</el-button
          >
          <el-button
            type="info"
            @click="
              titles == '添加评价' ? addEvaluation('投诉') : updateEvaluation('投诉')
            "
            >投诉</el-button
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
.el-drawer .el-form {
  flex-flow: column nowrap;
  width: 100%;
}
.el-drawer .el-form-item {
  width: 95%;
}
</style>
