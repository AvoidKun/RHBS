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