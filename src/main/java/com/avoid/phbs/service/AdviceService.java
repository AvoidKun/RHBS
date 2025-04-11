package com.avoid.phbs.service;

import com.avoid.phbs.model.Advice;
import com.avoid.phbs.model.PageBean;

public interface AdviceService {
    void add(Advice advice);

    PageBean<Advice> list(Integer pageNum, Integer pageSize, Integer userId, String state);

    void update(Advice advice);

    void deleteById(Integer id);
}
