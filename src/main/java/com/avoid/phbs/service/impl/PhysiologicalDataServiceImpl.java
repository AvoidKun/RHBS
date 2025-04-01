package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.PhysiologicalMapper;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.PhysiologicalData;
import com.avoid.phbs.service.PhysiologicalDataService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PhysiologicalDataServiceImpl implements PhysiologicalDataService {
    @Autowired
    private PhysiologicalMapper physiologicalMapper;
    @Override
    public void add(PhysiologicalData physiologicalData) {
        physiologicalData.setCreateTime(LocalDateTime.now());
        physiologicalData.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        physiologicalData.setCreateUser(UserId);
        physiologicalMapper.add(physiologicalData);
    }

    @Override
    public List<PhysiologicalData> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");
        return physiologicalMapper.list(userId);
    }

    @Override
    public void update(PhysiologicalData physiologicalData) {
        physiologicalData.setUpdateTime(LocalDateTime.now());
        physiologicalMapper.update(physiologicalData);
    }

    @Override
    public void delete(Integer id) {
        physiologicalMapper.delete(id);
    }

    @Override
    public PageBean<PhysiologicalData> pageList(Integer pageNum, Integer pageSize, String state) {
        PageBean<PhysiologicalData> pb = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer)map.get("id");
        List<PhysiologicalData> ps = physiologicalMapper.pageList(userId,state);
        Page<PhysiologicalData> page = (Page<PhysiologicalData>) ps;
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }
}
