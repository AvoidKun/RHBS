package com.avoid.phbs.service;

import com.avoid.phbs.model.*;

import java.util.List;

public interface EvaluationService {
    //增
    void add(Evaluation evaluation);

    //查
    PageBean<Evaluation> list(Integer pageNum, Integer pageSize, String state);

    //改
    void update(Evaluation evaluation);

    //删
    void deleteById(Integer id);

    //药物数据
    PageBean<Medicines> medicinesList(Integer pageNum, Integer pageSize, String state);

    //公告类型
    List<AnnouncementCategory> categorylist();

    //公告详情
    PageBean<Announcement> announcementlist(Integer pageNum, Integer pageSize, Integer announcementCategoryId, String state);
    //建议列表
    PageBean<Advice> advicelist(Integer pageNum, Integer pageSize, Integer userId, String state);
}
