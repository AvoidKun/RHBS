<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  physiologicalPageService,
  physiologicalListService,
  physiologicalAddService,
  physiologicalUpdateService,
  physiologicalDeleteService,
} from "@/api/physiological.js";
const physiological = ref([]);
const state = ref("");
//分页数据
const pageNum = ref(1);
const total = ref(20);
const pageSize = ref(3);
//当每页条发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  physiologicalList();
};
const onCurrentChange = (num) => {
  pageNum.value = num;
  physiologicalList();
};

const visibleDrawer = ref(false);
//分类
// const physiologicalDataList = async () => {
//   let result = await physiologicalListService();
//   physiological.value = result.data;
// };

//获取数据列表分页
const physiologicalList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    state: state.value ? state.value : null,
  };
  // console.log(params);
  let result = await physiologicalPageService(params);
  // console.log(result.data)
  total.value = result.data.total;
  // console.log(total)
  physiological.value = result.data.items;
};
// physiologicalDataList();
physiologicalList();

const dataModel = ref({
  temperature: "",
  bloodGlucose: "",
  bloodPressure: "",
  heart: "",
  height: "",
  weight: "",
  step: "",
  sleep: "",
  state: "",
});

//添加数据
import { ElMessage, ElMessageBox } from "element-plus";
const addData = async () => {
  let result = await physiologicalAddService(dataModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");
  visibleDrawer.value = false;
  physiologicalList();
};
const titles = ref("");

const rules = {
  temperature: [
    { required: true, message: "请输入体温", trigger: "blur" },
    {
      pattern: /^(3[2-9]|4[0-6])$/,
      message: "请输入32-46之间的整数",
      trigger: "blur",
    },
  ],
  bloodGlucose: [
    { required: true, message: "请输入血糖", trigger: "blur" },
    {
      pattern: /^([1-9]|[12][0-9]|30)$/,
      message: "请输入0-30之间的整数",
      trigger: "blur",
    },
  ],
  bloodPressure: [
    { required: true, message: "请输入血压", trigger: "blur" },
    {
      pattern: /^([6-9][0-9]|100|1[0-9][0-9]|200)$/,
      message: "请输入60-200之间的整数",
      trigger: "blur",
    },
  ],
  heart: [
    { required: true, message: "请输入心率", trigger: "blur" },
    {
      pattern: /^([3-9][0-9]|1[0-7][0-9]|180)$/,
      message: "请输入30-180之间的整数",
      trigger: "blur",
    },
  ],
  height: [
    { required: true, message: "请输入身高", trigger: "blur" },
    {
      pattern: /^(1[0-9][0-9]|2[0-4][0-9]|250)$/,
      message: "请输入100-250之间的整数",
      trigger: "blur",
    },
  ],
  weight: [
    { required: true, message: "请输入体重", trigger: "blur" },
    {
      pattern: /^([2-9][0-9]|1[0-9][0-9]|200)$/,
      message: "请输入20-200之间的整数",
      trigger: "blur",
    },
  ],
  // step: "",
  sleep: [
    { required: false, message: "请输入体重", trigger: "blur" },
    {
      pattern: /^([0-9]|1[0-9])$/,
      message: "请输入0-19之间的整数",
      trigger: "blur",
    },
  ],
  state: [
    { required: true, message: "请输入健康状态", trigger: "blur" },
    {
      pattern: /^(健康|不健康)$/,
      message: '请输入"健康"或"不健康"',
      trigger: "blur",
    },
  ],
};

//修改数据

const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改数据";
  console.log(dataModel.value);
  dataModel.value.temperature = row.temperature;
  dataModel.value.bloodGlucose = row.bloodGlucose;
  dataModel.value.bloodPressure = row.bloodPressure;
  dataModel.value.heart = row.heart;
  dataModel.value.height = row.height;
  dataModel.value.weight = row.weight;
  dataModel.value.step = row.step;
  dataModel.value.sleep = row.sleep;
  dataModel.value.state = row.state;
  dataModel.value.id = row.id;
};
const updatePhysiological = async () => {
  let result = await physiologicalUpdateService(dataModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");
  visibleDrawer.value = false;
  physiologicalList();
};
const clearData = () => {
  dataModel.value.temperature = "";
  dataModel.value.bloodGlucose = "";
  dataModel.value.bloodPressure = "";
  dataModel.value.heart = "";
  dataModel.value.height = "";
  dataModel.value.weight = "";
  dataModel.value.step = "";
  dataModel.value.sleep = "";
  dataModel.value.state = "";
};
//删除数据
const deleteData = (row) => {
  ElMessageBox.confirm("你确认要删除该数据吗？", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      let result = await physiologicalDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      physiologicalList();
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
        <div><span>健康数据</span></div>
        <div>
          <el-button
            type="primary"
            round
            @click="
              visibleDrawer = true;
              titles = '添加数据';
              clearData();
            "
          >
            添加数据</el-button
          >
        </div>
      </div>
    </template>

    <el-form :inline="true">
      <el-form-item label="健康状态">
        <el-select placeholder="请选择" clearable v-model="state">
          <el-option label="健康" value="健康" />
          <el-option label="不健康" value="不健康" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="physiologicalList">搜索</el-button>
        <el-button
          @click="
            categoryId = '';
            state = '';
            physiologicalList();
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table :data="physiological">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="状态" prop="state"> </el-table-column>
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
            @click="deleteData(row)"
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
      <el-form class="extraForm" label-width="120px" :model="dataModel" :rules="rules">
        <el-form-item label="体温(摄氏度)" prop="temperature">
          <el-input v-model="dataModel.temperature" placeholder="请输入体温"></el-input>
        </el-form-item>
        <el-form-item label="血糖(mmol/L)" prop="bloodGlucose">
          <el-input v-model="dataModel.bloodGlucose" placeholder="请输入血糖"></el-input>
        </el-form-item>
        <el-form-item label="血压(mmHg)" prop="bloodPressure">
          <el-input v-model="dataModel.bloodPressure" placeholder="请输入血压"></el-input>
        </el-form-item>
        <el-form-item label="心率(次/分)" prop="heart">
          <el-input v-model="dataModel.heart" placeholder="请输入心率"></el-input>
        </el-form-item>
        <el-form-item label="身高(厘米)" prop="height">
          <el-input v-model="dataModel.height" placeholder="请输入身高"></el-input>
        </el-form-item>
        <el-form-item label="体重(千克)" prop="weight">
          <el-input v-model="dataModel.weight" placeholder="请输入体重"></el-input>
        </el-form-item>
        <el-form-item label="步数(步)" prop="step">
          <el-input v-model="dataModel.step" placeholder="请输入今日步数"></el-input>
        </el-form-item>
        <el-form-item label="睡眠(小时)" prop="sleep">
          <el-input
            v-model="dataModel.sleep"
            placeholder="请输入今日睡眠时间"
          ></el-input>
        </el-form-item>
        <el-form-item label="健康" prop="state">
          <el-input
            v-model="dataModel.state"
            placeholder="请输入今日状态是否健康"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="titles == '添加数据' ? addData() : updatePhysiological()"
            type="primary"
            style="width: 35%;"

            >发布</el-button
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
.extraForm {
  flex-flow: column nowrap;
  width: 100%;
}
.extraForm .el-form-item {
  width: 95%;
}
</style>
