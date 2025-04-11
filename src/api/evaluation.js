import request from '@/utils/request.js'
//获取列表
export const evaluationListService=(params)=>{
    return request.get("/evaluation",{params:params})
}
//增加评论
export const evaluationAddService=(evaluation)=>{
    return request.post("/evaluation",evaluation)
}
//更新
export const evaluationUpdateService=(evaluation)=>{
    return request.put("/evaluation",evaluation)
}
//删除
export const evaluationDeleteService=(id)=>{
    return request.delete("/evaluation?id="+id)
}
