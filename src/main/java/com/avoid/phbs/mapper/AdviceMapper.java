package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Advice;
import com.avoid.phbs.model.PhysiologicalData;
import com.avoid.phbs.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdviceMapper {
    @Insert("insert into advice(title,user_id,admin_id,content,state,create_time,update_time)"+
            "values(#{title},#{userId},#{adminId},#{content},#{state},#{createTime},#{updateTime})")
    void add(Advice advice);

    List<Advice> list(Integer adminId, Integer userId, String state);

    @Update("update advice set title=#{title},content=#{content},state=#{state},user_id=#{userId},update_time=#{updateTime} where id =#{id} ")
    void update(Advice advice);

    @Delete("delete from advice where id = #{id}")
    void deleteById(Integer id);


    @Select("select * from user")
    List<User> findUserInfo();


    List<PhysiologicalData> findUserPhysiologicalData(Integer createUser, String state);
    @Select("select * from physiologicaldata")
    List<PhysiologicalData> findPhysiologicalData();
}
