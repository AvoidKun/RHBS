import request from '@/utils/request.js'
//获取列表
export const medicinesListService=(params)=>{
    return request.get("/medicines",{params:params})
}
//增
export const medicinesAddService=(medicines)=>{
    return request.post("/medicines",medicines)
}
//更新
export const medicinesUpdateService=(medicines)=>{
    return request.put("/medicines",medicines)
}
//删
export const medicinesDeleteService=(id)=>{
    return request.delete("/medicines?id="+id)
}