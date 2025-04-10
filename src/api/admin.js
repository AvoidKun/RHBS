import request from '@/utils/request'
//登录接口
export const adminLoginService=(loginData)=>{
    const params = new URLSearchParams();
    for (let key in loginData){
        params.append(key,loginData[key])
    }
    return request.post('/admin/login',params)

}
//获取信息
export const adminInfoService=()=>{
    return request.get('/admin/adminInfo')
}

//修改
export const adminInfoUpdateService=(adminInfoData)=>{
    return request.put('/admin/update',adminInfoData)
}

//修改密码
export const adminPasswordUpdateService=(passwordData)=>{
    return request.patch('/admin/updatePwd',passwordData);
}