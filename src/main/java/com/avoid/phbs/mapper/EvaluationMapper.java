package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Evaluation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EvaluationMapper {
    //增
    @Insert("insert into evaluation(type,rating,content,state,create_user,create_time,update_time)"+
    "values(#{type},#{rating},#{content},#{state},#{createUser},#{createTime},#{updateTime})")
    void add(Evaluation evaluation);

    //查
    List<Evaluation> list(Integer userId, String state);

    //改
    @Update("update evaluation set type=#{type},rating=#{rating},content=#{content},state=#{state},update_time=#{updateTime} where id=#{id}")
    void update(Evaluation evaluation);

    //删
    @Delete("delete from evaluation where id = #{id}")
    void deleteById(Integer id);
}
