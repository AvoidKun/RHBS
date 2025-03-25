<script setup>
import { User, Lock } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import {useRouter} from 'vue-router'
//控制显示注册/登录
const isRegister = ref(true)

//定义数据模型
const registerData = ref({
    username: '',
    password: '',
    rePassword: ''
})

const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保俩次输入的密码是一样的'))
    } else {
        callback()
    }
}

const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 1, max: 5, message: '姓名长度为1~5个字', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16非空字符', trigger: 'blur' }
    ],
    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]

}

//调用后台接口，完成注册
import { userRegisterService, userLoginService } from '@/api/user.js'
const register = async () => {
    let result = await userRegisterService(registerData.value);
    //    if(result.code===0){
    //     alert(result.msg ? result.msg:'注册成功');
    //    }else{
    //     alert('注册失败');
    //    }
    ElMessage.success(result.msg ? result.msg : '注册成功');
}

//登录函数
import { useTokenStore } from '@/stores/token.js'
const router = useRouter()
const tokenStore = useTokenStore()
const login = async () => {
    // 调用接口
    let result = await userLoginService(registerData.value);
    // if(result.code === 0 ){
    //     alert(result.msg ? result.msg:'登录成功')
    // }else{
    //     alert('登录失败')
    // }
    ElMessage.success(result.msg ? result.msg : '登录成功');
    //把得到的token存到pinia中
    tokenStore.setToken(result.data)
    router.push('/')

}

//清空数据
const clearRegisterData = () => {
    registerData.value = {
        username: '',
        password: ''
    }

}
</script>

<template>

    <el-row class="common-layout">
        <el-col :span="12">
            <div class="bg-purple"></div>
        </el-col>
        <el-col :span="8" :offset="2" class="form">
            <!--登录表单-->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister" :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>登录</h1>
                </el-form-item>
                <el-form-item label="Activity name" prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"> </el-input>
                </el-form-item>
                <el-form-item label="Activity name" prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox label="记住我" value="Value A" />
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="login">登录</el-button>
                </el-form-item>
                <el-from-item class="flex">
                    <el-link type="info" :underline="false"
                        @click="isRegister = flase; clearRegisterData()">注册→</el-link>
                </el-from-item>
            </el-form>

            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else :rules="rules" :model="registerData">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码"
                        v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码"
                        v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" auto-insert-space @click="register">
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true; clearRegisterData()">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>

</template>

<style>
* {
    padding: 0;
    margin: 0;
}
.common-layout {
    height: 100%;
    width: 100%;

}

.bg-purple {

    height: 100vh;
    background: url("../assets/1.png") no-repeat center;
    border-radius: 0 20px 20px 0;
    background-position: 65% 50%;
}

.el-link {
    margin-right: 8px;
    vertical-align: text-bottom;

}

.button {
    width: 100%;
}

.form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;
}

.flex {
    width: 100%;
    display: flex;
    justify-content: space-between;
}
</style>