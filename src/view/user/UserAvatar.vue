<script setup>
import { Plus, Upload } from "@element-plus/icons-vue";
import { ref } from "vue";
import avatar from "@/assets/3.jpg";
const uploadRef = ref();
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore()
import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore=useUserInfoStore();
//头像地址 
const imgUrl = ref("");
//上传成功后的回调函数
const uploadSuccess =(result)=>{
  imgUrl.value =result.data.replace('@/assets/', '../../assets/');
  console.log(imgUrl.value)
}

</script>
<template>
  <el-card>
    <template #header>
      <div class="header">
        <span>更换头像</span>
      </div>
    </template>
    <el-row>
      <el-col>
        <el-upload 
        ref="uploadRef"
        :show-file-list="false"
        :auto-upload="true"
        action="/api/upload"
        name="file"
        :headers="{'Authorization':tokenStore.token}"
        :on-success="uploadSuccess"
        >
          <img v-if="imgUrl" :src="imgUrl" />
          <img v-else :src="avatar" width="278" />
        </el-upload>
        <br/>
        <el-button  :icon="Plus" type="primary" size="large" @click="uploadRef.$el.querySelector('input').click()">
        选择图片
        </el-button>
        <el-button :icon="Upload" type="success" size="large">
        上传图片
        </el-button>
      </el-col>
    </el-row>
  </el-card>
</template>
