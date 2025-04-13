package com.avoid.phbs.service;

import com.avoid.phbs.model.Advice;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.PhysiologicalData;
import com.avoid.phbs.model.User;

import java.util.List;

public interface AdviceService {
    void add(Advice advice);

    PageBean<Advice> list(Integer pageNum, Integer pageSize, Integer userId, String state);

    void update(Advice advice);

    void deleteById(Integer id);

    List<User> findUserInfo();

    PageBean<PhysiologicalData> findUserPhysiologicalData(Integer pageNum, Integer pageSize, Integer createUser, String state);


    //找user
   
}
