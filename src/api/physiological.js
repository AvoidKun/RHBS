import request from '@/utils/request.js'
// export const articleCategoryAddService = (categoryData) => {
//     return request.post('/category', categoryData)
// }
//获取数据
export const physiologicalListService=()=>{
    return request.get("/physiologicalData")
}
//获取分页
export const physiologicalPageService=(params)=>{
    return request.get("/physiologicalData/page",{params:params})
}
//添加数据
export const physiologicalAddService=(physiologicalData)=>{
    return request.post("/physiologicalData",physiologicalData)
}
//修改数据
export const physiologicalUpdateService=(physiologicalData)=>{
   return request.put("/physiologicalData",physiologicalData)
}
//删除数据
export const physiologicalDeleteService=(id)=>{
    return request.delete("/physiologicalData?id="+id)
}