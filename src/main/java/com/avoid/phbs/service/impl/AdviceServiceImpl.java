package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.AdviceMapper;
import com.avoid.phbs.model.*;
import com.avoid.phbs.service.AdviceService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Service
public class AdviceServiceImpl implements AdviceService {
    @Autowired
    private AdviceMapper adviceMapper;
    @Override
    public void add(Advice advice) {
        advice.setCreateTime(LocalDateTime.now());
        advice.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        advice.setAdminId(adminId);
        adviceMapper.add(advice);
    }

    @Override
    public PageBean<Advice> list(Integer pageNum, Integer pageSize, Integer userId, String state) {
        PageBean<Advice> ap = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        List<Advice> al = adviceMapper.list(adminId,userId,state);
        Page<Advice> page = (Page<Advice>) al;
        ap.setItems(page.getResult());
        ap.setTotal(page.getTotal());
        return ap;
    }

    @Override
    public void update(Advice advice) {
        advice.setUpdateTime(LocalDateTime.now());
        adviceMapper.update(advice);
    }

    @Override
    public void deleteById(Integer id) {
        adviceMapper.deleteById(id);
    }

    //用户
    @Override
    public List<User> findUserInfo() {
        return adviceMapper.findUserInfo();
    }
    //健康数据
    @Override
    public PageBean<PhysiologicalData> findUserPhysiologicalData(Integer pageNum, Integer pageSize, Integer createUser, String state) {
        PageBean<PhysiologicalData> pp = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<PhysiologicalData> al = adviceMapper.findUserPhysiologicalData(createUser,state);
        Page<PhysiologicalData> page = (Page<PhysiologicalData>) al;
        pp.setItems(page.getResult());
        pp.setTotal(page.getTotal());
        return pp;
    }

}



