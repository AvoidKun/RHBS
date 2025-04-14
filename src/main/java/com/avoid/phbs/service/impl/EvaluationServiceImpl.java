package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.EvaluationMapper;
import com.avoid.phbs.model.*;
import com.avoid.phbs.service.EvaluationService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class EvaluationServiceImpl implements EvaluationService {
    @Autowired
    private EvaluationMapper evaluationMapper;
    @Override
    public void add(Evaluation evaluation) {
        evaluation.setCreateTime(LocalDateTime.now());
        evaluation.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");
        evaluation.setCreateUser(userId);
        evaluationMapper.add(evaluation);
    }

    @Override
    public PageBean<Evaluation> list(Integer pageNum, Integer pageSize, String state) {
        PageBean<Evaluation> ep = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Evaluation> el = evaluationMapper.list(userId,state);
        Page<Evaluation> page = (Page<Evaluation>) el;
        ep.setTotal(page.getTotal());
        ep.setItems(page.getResult());
        return ep;
    }

    @Override
    public void update(Evaluation evaluation) {
        evaluation.setUpdateTime(LocalDateTime.now());
        evaluationMapper.update(evaluation);
    }

    @Override
    public void deleteById(Integer id) {
        evaluationMapper.deleteById(id);
    }

    //药物数据
    @Override
    public PageBean<Medicines> medicinesList(Integer pageNum, Integer pageSize, String state) {
        PageBean<Medicines> pb = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Medicines> as = evaluationMapper.medicinesList(state);
        Page<Medicines> page = (Page<Medicines>) as;
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }

    @Override
    public List<AnnouncementCategory> categorylist() {
        return evaluationMapper.categorylist();
    }

    @Override
    public PageBean<Announcement> announcementlist(Integer pageNum, Integer pageSize, Integer announcementCategoryId, String state) {

        //创建PageBean对象
        PageBean<Announcement> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        List<Announcement> as = evaluationMapper.announcementlist(announcementCategoryId,state);
        Page<Announcement> page = (Page<Announcement>) as ;

        //把数据传到PageBean中
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }

    @Override
    public PageBean<Advice> advicelist(Integer pageNum, Integer pageSize, Integer userId, String state) {
        PageBean<Advice> ap = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Advice> al = evaluationMapper.advicelist(userId,state);
        Page<Advice> page = (Page<Advice>) al;
        ap.setItems(page.getResult());
        ap.setTotal(page.getTotal());
        return ap;
    }
}
