<script setup>
import { ref } from "vue";
import { Edit, Delete } from "@element-plus/icons-vue";
import {
  medicinesListService,
  medicinesAddService,
  medicinesUpdateService,
  medicinesDeleteService,
} from "@/api/medicines.js";
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
  let result = await medicinesListService(params);
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
//添加
import { ElMessage, ElMessageBox } from "element-plus";
const addMedicines = async (clickState) => {
  medicinesModel.value.state = clickState;
  let result = await medicinesAddService(medicinesModel.value);
  ElMessage.success(result.msg ? result.msg : "添加成功");
  visibleDrawer.value = false;
  medicinesList();
};
//改
const showDrawer = (row) => {
  visibleDrawer.value = true;
  titles.value = "修改药品";
  medicinesModel.value.medicinename = row.medicinename;
  medicinesModel.value.genericname = row.genericname;
  medicinesModel.value.description = row.description;
  medicinesModel.value.id = row.id;
};
const updateMedicines = async (clickState) => {
  medicinesModel.value.state = clickState;
  let result = await medicinesUpdateService(medicinesModel.value);
  ElMessage.success(result.msg ? result.msg : "修改成功");
  visibleDrawer.value = false;
  medicinesList();
};
const clearDate = () => {
  medicinesModel.value.medicinename = "";
  medicinesModel.value.genericname= "";
//   medicinesModel.value.state = "";
  medicinesModel.value.description = "";
};
//删除
const deleteDate = (row)=>{
    ElMessageBox.confirm("你确认要删除该药品吗？","温馨提示",{
        confirmButtonText:'确认',
        cancelButtonText:'取消',
        type:"warning"
    })
    .then(async()=>{
        let result = await medicinesDeleteService(row.id);
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
              titles = '添加药品';
              clearDate();
            "
          >
            添加药品</el-button
          >
        </div>
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
      <el-form :Model="medicinesModel" label-width="120px">
        <el-form-item label="药品名称">
          <el-input
            v-model="medicinesModel.medicinename"
            placeholder="请您输入需要提交的药品名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="药品别名">
          <el-input
            v-model="medicinesModel.genericname"
            placeholder="请您输入需要提交的药品的别名"
          ></el-input>
        </el-form-item>
        <el-form-item label="药品效果">
          <el-input
            v-model="medicinesModel.description"
            style="width: 100%"
            :rows="2"
            type="textarea"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="titles == '添加药品' ? addMedicines('盈余') : updateMedicines('盈余')"
            >盈余</el-button
          >
          <el-button
            type="info"
            @click="titles == '添加药品' ? addMedicines('告罄') : updateMedicines('告罄')"
            >告罄</el-button
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
