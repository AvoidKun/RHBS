package com.avoid.phbs.mapper;

import com.avoid.phbs.model.AnnouncementCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AnnouncementCategoryMapper {
    //增
    @Insert("insert into announcementcategory (category_name,category_alias,create_admin,create_time,update_time)"
    +"VALUES (#{categoryName},#{categoryAlias},#{createAdmin},#{createTime},#{updateTime})")
    void add(AnnouncementCategory announcementCategory);

    //查
    @Select("select * from announcementcategory")
    List<AnnouncementCategory> list(Integer adminId);

    //改
    @Update("update aannouncementcategory set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id = #{id}")
    void update(AnnouncementCategory announcementCategory);

    //删
    @Delete("delete from announcementcategory where id = #{id}")
    void delete(Integer id);
}
