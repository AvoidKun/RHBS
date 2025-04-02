import request from '@/utils/request.js'

export const evaluationListService=(params)=>{
    return request.get("/evaluation",{params:params})
}
export const evaluationAddService=(evaluation)=>{
    return request.post("/evaluation",evaluation)
}
export const evaluationUpdateService=(evaluation)=>{
    return request.put("/evaluation",evaluation)
}
export const evaluationDeleteService=(id)=>{
    return request.delete("/evaluation?id="+id)
}
