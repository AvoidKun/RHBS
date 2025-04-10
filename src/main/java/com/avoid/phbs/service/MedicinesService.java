package com.avoid.phbs.service;


import com.avoid.phbs.model.Medicines;
import com.avoid.phbs.model.PageBean;

public interface MedicinesService {
    void add(Medicines medicines);
    PageBean<Medicines> list(Integer pageNum, Integer pageSize, String state);

    void update(Medicines medicines);

    void deleteById(Integer id);
}
