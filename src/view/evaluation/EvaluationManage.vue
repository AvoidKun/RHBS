<script setup>
import {ref} from 'vue'
import {
  evaluationListService,
  evaluationAddService,
  evaluationUpdateService,
  evaluationDeleteService,
} from "@/api/evaluation.js";
const value = ref()
const gets =()=>{
    console.log(value.value)
}
</script>
<template>
    <el-card>
    <el-form>
    <el-form-item label="评价类型">
    <el-input placeholder="请您输入需要提交的评价类型"></el-input>
    </el-form-item>
    <el-form-item label="评分等级">
        <el-rate v-model="value" allow-half @click="gets()"/>
    </el-form-item>
    <el-form-item>
    <el-input></el-input>
    </el-form-item>
    <el-form-item>
    <el-input></el-input>
    </el-form-item>
    </el-form>
    </el-card>
    <el-card></el-card>
</template>