package com.avoid.phbs.service.impl;


import com.avoid.phbs.mapper.MedicinesMapper;

import com.avoid.phbs.model.Medicines;

import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.service.MedicinesService;

import com.avoid.phbs.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class MedicinesServiceImpl implements MedicinesService {
    @Autowired
    private MedicinesMapper medicinesMapper;
    @Override
    public void add(Medicines medicines) {
        medicines.setCreateTime(LocalDateTime.now());
        medicines.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        medicines.setCreateAdmin(adminId);
        medicinesMapper.add(medicines);
    }

    @Override
    public PageBean<Medicines> list(Integer pageNum, Integer pageSize, String state) {
        PageBean<Medicines> pb = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map =ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        List<Medicines> as = medicinesMapper.list(adminId,state);
        Page<Medicines> page = (Page<Medicines>) as;
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }

    @Override
    public void update(Medicines medicines) {
        medicines.setUpdateTime(LocalDateTime.now());
        medicinesMapper.update(medicines);
    }

    @Override
    public void deleteById(Integer id) {
        medicinesMapper.deleteById(id);
    }

}




//
//    @Override
//    public void deleteById(Integer id) {
//        articleMapper.deleteById(id);
//    }