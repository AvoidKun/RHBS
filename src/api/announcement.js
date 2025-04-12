import request from '@/utils/request.js'
//公告分类
export const announcementCategoryListService =() =>{
    return request.get('/announcementCategory')
}
//添加分类
export const announcementCategoryAddService=(announcementData)=>{
    return request.post('/announcementCategory',announcementData)
}
//修改分类
export const announcementCategoryUpdateService=(announcementData)=>{
    return request.put('/announcementCategory',announcementData)
}
//分类删除
export const announcementCategoryDeleteService=(id)=>{
    return request.delete('/announcementCategory?id='+id)
}
//公告列表查询
export const announcementListService=(params)=>{
    return request.get('/announcement',{params:params})
}
//文章添加
export const announcementAddService = (announcementData)=>{
    return request.post('/announcement',announcementData)
}
//文章内容修改
export const announcementUpdateService = (announcementData)=>{
    return request.put('/announcement',announcementData)
}
//文章删除
export const announcementDeleteService = (id)=>{
    return request.delete('/announcement?id='+id)
}