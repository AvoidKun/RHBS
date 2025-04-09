package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Admin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    //查
    @Select("select * from admin where adminname= #{adminname}")
    Admin findByAdminName(String adminname);
    //注册
    @Insert("insert into admin(adminname,password,create_time,update_time)"
    +"values(#{adminname},#{password},now(),now())")
    void add(String adminname, String password);

    //修改信息
    @Update("update admin set adminname=#{adminname},email=#{email},phone=#{phone},position=#{position},update_time=#{updateTime} where id = #{id}")
    void update(Admin admin);

    //该密码
    @Update("update admin set password=#{md5String},update_time=now() where id= #{id}")
    void updatePwd(String md5String, Integer id);
}
