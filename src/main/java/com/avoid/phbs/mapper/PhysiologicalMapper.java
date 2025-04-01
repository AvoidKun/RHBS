package com.avoid.phbs.mapper;

import com.avoid.phbs.model.PhysiologicalData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PhysiologicalMapper {
    //增
    @Insert("insert into physiologicaldata(create_user,temperature,blood_glucose,blood_pressure,heart,height,weight,step,sleep,state,create_time,update_time)" +
            "values(#{createUser},#{temperature},#{bloodGlucose},#{bloodPressure},#{heart},#{height},#{weight},#{step},#{sleep},#{state},#{createTime},#{updateTime}) ")
    void add(PhysiologicalData physiologicalData);

    //查
    @Select("select * from physiologicaldata where create_user =#{userId} ")
    List<PhysiologicalData> list(Integer userId);

    //改
    @Update("update physiologicaldata set temperature=#{temperature},blood_glucose=#{bloodGlucose},blood_pressure=#{bloodPressure},heart=#{heart},height=#{height},weight=#{weight},step=#{step},sleep=#{sleep},state=#{state},update_time=#{updateTime} where id=#{id}")
    void update(PhysiologicalData physiologicalData);

    //删
    @Delete("delete from physiologicaldata where id=#{id}")
    void delete(Integer id);

    //分页
//    @Select("select * from physiologicaldata where create_user =#{userId} ")
    List<PhysiologicalData> pageList(Integer userId,String state);
}
