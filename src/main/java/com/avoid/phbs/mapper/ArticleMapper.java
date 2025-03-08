package com.avoid.phbs.mapper;

import com.avoid.phbs.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ArticleMapper {
    //新增
    @Insert("insert into article(title,content,state,category_id,create_user,create_time,update_time)"+
    "values(#{title},#{content},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void add(Article article);

    //文章数量分页
    List<Article> list(Integer userId, Integer categoryId, String state);

    //获取文章详情
    @Select("select * from article where id = #{id}")
    Article findById(Integer id);

    //更新文章
    @Update("update article set title=#{title},content=#{content},state=#{state},category_id=#{categoryId},update_time=#{updateTime} where id =#{id} ")
    void update(Article article);

    @Delete("delete from article where id = #{id}")
    void deleteById(Integer id);
}
