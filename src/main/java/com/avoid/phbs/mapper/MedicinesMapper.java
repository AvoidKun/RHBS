package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Medicines;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MedicinesMapper {
    //增
    @Insert("insert into medicines(medicinename,genericname,description,state,create_admin,create_time,update_time)"
    +"values(#{medicinename},#{genericname},#{description},#{state},#{createAdmin},now(),now())"
    )
    void add(Medicines medicines);

    //查
    List<Medicines> list(Integer adminId, String state);
    //改
    @Update("update medicines set medicinename=#{medicinename},genericname=#{genericname},description=#{description},state=#{state},update_time=#{updateTime} where id =#{id}")
    void update(Medicines medicines);

    //删
    @Delete("delete from medicines where id = #{id}")
    void deleteById(Integer id);
}
