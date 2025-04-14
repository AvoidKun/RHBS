package com.avoid.phbs.service;

import com.avoid.phbs.model.Evaluation;
import com.avoid.phbs.model.Medicines;
import com.avoid.phbs.model.PageBean;

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
}
