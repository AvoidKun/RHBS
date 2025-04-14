package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.EvaluationMapper;
import com.avoid.phbs.model.Evaluation;
import com.avoid.phbs.model.Medicines;
import com.avoid.phbs.model.PageBean;
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
}
