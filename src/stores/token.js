//定义store
import { defineStore } from "pinia";
import { ref } from "vue";
// 第一个参数，名字，唯一性
//第二个参数，函数，函数内部定义状态的所有内容
//返回值：函数
export const useTokenStore = defineStore('token', () => {
    //定义状态内容
    const token = ref('')
    //定义一个函数，修改token值
    const setToken = (newToken) => {
        token.value = newToken
    }
    //函数，移除token的值
    const removeToken = () => {
        token.value = ''
    }
    return {
        token, setToken, removeToken
    }
},{
    persist:true//持久化存储
}

);