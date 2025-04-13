package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Announcement;
import com.avoid.phbs.model.Evaluation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AnnouncementMapper {
    //增
    @Insert("insert into announcement(title,content,state,announcement_category_id,create_admin,create_time,update_time)"+
            "values(#{title},#{content},#{state},#{announcementCategoryId},#{createAdmin},#{createTime},#{updateTime})")
    void add(Announcement announcement);

    //查
    List<Announcement> list(Integer adminId, Integer announcementCategoryId, String state);

    //改
    @Update("update announcement set title=#{title},content=#{content},state=#{state},announcement_category_id=#{announcementCategoryId},update_time=#{updateTime} where id =#{id} ")
    void update(Announcement announcement);

    //删
    @Delete("delete from announcement where id = #{id}")
    void deleteById(Integer id);

    List<Evaluation> findEvalution(String state);
}
