import request from '@/utils/request.js'
//获取列表
export const adviceListService=(params)=>{
    return request.get("/advice",{params:params})
}
//增
export const adviceAddService=(advice)=>{
    return request.post("/advice",advice)
}
//改
export const adviceUpdateService=(advice)=>{
    return request.put("/advice",advice)
}
//删
export const adviceDeleteService=(id)=>{
    return request.delete("/advice?id="+id)
}
//获取用户信息
export const adviceUserInfoService=()=>{
    return request.get("/advice/userinfo")
}
//注册用户
//注册接口
export const adviceUserRegisterService = (registerData) => {
    const params = new URLSearchParams()
    for (let key in registerData) {
        params.append(key, registerData[key])
    }
    return request.post('/user/register', params)

}

//获取健康数据
export const advicePhysiologicalService=(params)=>{
    return request.get("/advice/physiological",{params:params})
}
export const advicePhysiologicalChartService=()=>{
    return request.get("/advice/chart")
}