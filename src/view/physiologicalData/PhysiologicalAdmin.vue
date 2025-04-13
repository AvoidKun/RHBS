<script setup>
import { ref,computed } from "vue";
import { Document } from "@element-plus/icons-vue";
import { advicePhysiologicalService, adviceUserInfoService } from "@/api/advice.js";
// 分页配置
const pageNum = ref(1); //当前页
const total = ref(20); //总
const pageSize = ref(3); //每页
const onSizeChange = (size) => {
  pageSize.value = size;
  physiologicalList();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  pageNum.value = num;
  physiologicalList();
};

const physiological = ref([]);
const users = ref([]);
const state = ref("");
const createUser = ref("");
const titles = ref("");
const userNames = ref("");
// 加载数据
const userInfo = async () => {
  let result = await adviceUserInfoService();
  users.value = result.data;
  //   console.log(users.value)
};
const physiologicalList = async () => {
  let params = {
    pageNum: pageNum.value,
    pageSize: pageSize.value,
    createUser: createUser.value ? createUser.value : null,
    state: state.value ? state.value : null,
  };
  let result = await advicePhysiologicalService(params);
  total.value = result.data.total;
  physiological.value = result.data.items;
//   console.log();
  for (let i = 0; i < physiological.value.length; i++) {
    let advice = physiological.value[i];
    // console.log(advice);
    for (let j = 0; j < users.value.length; j++) {
      if (advice.createUser == users.value[j].id) {
        advice.userName = users.value[j].username;
        // console.log( advice.userName )
      }
    }
  }
};

userInfo();
physiologicalList();
const visibleDrawer = ref(false);
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
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "查看数据";
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
const physiologicalLists = () => {
  if (userNames.value != "") {
    for (let h = 0; h < users.value.length; h++) {
      if (userNames.value == users.value[h].username) {
        createUser.value = users.value[h].id;
        physiologicalList();
      }
    }
  } else {
    physiologicalList();
  }
};

// 完整的健康标准配置
const healthStandards = {
  temperature: { 
    normal: [36.0, 37.5],
    warning: [[37.6, 38.0], [35.0, 35.9]], // 发热或低体温
    danger: [[38.1, 45.0], [0, 34.9]]      // 高烧或严重低体温
  },
  bloodGlucose: {
    normal: [3.9, 6.1],
    warning: [[6.2, 7.0], [3.0, 3.8]],     // 偏高或偏低
    danger: [[7.1, 20.0], [0, 2.9]]        // 糖尿病或低血糖危险
  },
  bloodPressure: {
    normal: { systolic: [90, 120], diastolic: [60, 80] },
    warning: { 
      systolic: [[121, 139], [85, 89]],    // 偏高或偏低
      diastolic: [[81, 89], [50, 59]] 
    },
    danger: { 
      systolic: [[140, 300], [0, 84]],     // 高血压危象或严重低血压
      diastolic: [[90, 200], [0, 49]] 
    }
  },
  heart: {
    normal: [60, 100],
    warning: [[101, 120], [50, 59]],       // 心动过速或过缓
    danger: [[121, 300], [0, 49]]          // 严重心律失常
  },
  bmi: {
    normal: [18.5, 24],
    warning: [[24.1, 28], [17, 18.4]],     // 过重或过轻
    danger: [[28.1, 50], [0, 16.9]]        // 肥胖或严重消瘦
  },
  step: {
    normal: [5000, 10000],
    warning: [[10001, 20000], [2000, 4999]], // 过量或不足
    danger: [[20001, 50000], [0, 1999]]     // 极端情况
  },
  sleep: {
    normal: [6, 9],
    warning: [[9.1, 12], [4, 5.9]],        // 睡眠过多或不足
    danger: [[12.1, 24], [0, 3.9]]          // 极端情况
  }
};

// 判断是否警告状态
const isWarning = (type, value) => {
  if (!value) return false;
  const standard = healthStandards[type];
  if (type === 'bloodPressure') {
    const [systolic, diastolic] = value.split('/').map(Number);
    return (
      (systolic >= standard.warning.systolic[0] && systolic <= standard.warning.systolic[1]) ||
      (diastolic >= standard.warning.diastolic[0] && diastolic <= standard.warning.diastolic[1])
    );
  }
  return value >= standard.warning[0] && value <= standard.warning[1];
};

// 判断是否危险状态
const isDanger = (type, value) => {
  if (!value) return false;
  const standard = healthStandards[type];
  if (type === 'bloodPressure') {
    const [systolic, diastolic] = value.split('/').map(Number);
    return (
      systolic >= standard.danger.systolic[0] ||
      diastolic >= standard.danger.diastolic[0]
    );
  }
  return value >= standard.danger[0] || value <= standard.normal[0];
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
      <el-form-item label="被建议人:">
        <el-input placeholder="请输入" clearable v-model="userNames"> </el-input>
      </el-form-item>
      <el-form-item label="健康状态">
        <el-select placeholder="请选择" clearable v-model="state">
          <el-option label="健康" value="健康" />
          <el-option label="不健康" value="不健康" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="physiologicalLists">搜索</el-button>
        <el-button
          @click="
            createUser = '';
            userNames = '';
            state = '';
            physiologicalList();
          "
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-table :data="physiological">
      <el-table-column type="index" label="序号" width="140" />
      <el-table-column label="村民" prop="userName"></el-table-column>
      <el-table-column label="状态" prop="state" width="300"> </el-table-column>
      <el-table-column label="发布时间" prop="createTime"></el-table-column>
      <el-table-column label="查看" width="120">
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
      <el-form class="extraForm" label-width="120px" :model="dataModel" :rules="rules">
        <el-form-item label="体温(摄氏度)" prop="temperature">
          <el-input
            v-model="dataModel.temperature"
            placeholder="请输入体温"
            disabled
            :class="{
              'warning-input': isWarning('temperature', dataModel.temperature),
              'danger-input': isDanger('temperature', dataModel.temperature),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="血糖(mmol/L)" prop="bloodGlucose">
          <el-input
            v-model="dataModel.bloodGlucose"
            placeholder="请输入血糖"
            disabled
            :class="{
              'warning-input': isWarning('bloodGlucose', dataModel.bloodGlucose),
              'danger-input': isDanger('bloodGlucose', dataModel.bloodGlucose),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="血压(mmHg)" prop="bloodPressure">
          <el-input
            v-model="dataModel.bloodPressure"
            placeholder="请输入血压"
            disabled
            :class="{
              'warning-input': isWarning('bloodPressure', dataModel.bloodPressure),
              'danger-input': isDanger('bloodPressure', dataModel.bloodPressure),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="心率(次/分)" prop="heart">
          <el-input
            v-model="dataModel.heart"
            placeholder="请输入心率"
            disabled
            :class="{
              'warning-input': isWarning('heart', dataModel.heart),
              'danger-input': isDanger('heart', dataModel.heart),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="身高(厘米)" prop="height">
          <el-input
            v-model="dataModel.height"
            placeholder="请输入身高"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="体重(千克)" prop="weight">
          <el-input
            v-model="dataModel.weight"
            placeholder="请输入体重"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="步数(步)" prop="step">
          <el-input
            v-model="dataModel.step"
            placeholder="请输入今日步数"
            disabled
            :class="{
              'warning-input': isWarning('bloodstep', dataModel.bloodstep),
              'danger-input': isDanger('bloodstep', dataModel.bloodstep),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="睡眠(小时)" prop="sleep">
          <el-input
            v-model="dataModel.sleep"
            placeholder="请输入今日睡眠时间"
            disabled
            :class="{
              'warning-input': isWarning('bloodsleep', dataModel.bloodsleep),
              'danger-input': isDanger('bloodsleep', dataModel.bloodsleep),
            }"
          ></el-input>
        </el-form-item>
        <el-form-item label="健康" prop="state">
          <el-input
            v-model="dataModel.state"
            placeholder="请输入今日状态是否健康"
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
.extraForm {
  flex-flow: column nowrap;
  width: 100%;
}
.extraForm .el-form-item {
  width: 95%;
}
/* 警告状态样式 */
.warning-input :deep(.el-input__inner) {
  background-color: #fff8e6 !important;
  color: #e6a23c;
  font-weight: bold;
}

/* 危险状态样式 */
.danger-input :deep(.el-input__inner) {
  background-color: #feeff0 !important;
  color: #f56c6c;
  font-weight: bold;
}
</style>
