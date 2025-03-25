package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.PhysiologicalDataMapper;
import com.avoid.phbs.model.PhysiologicalData;
import com.avoid.phbs.service.PhysiologicalDataService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PhysiologicalDataServiceImpl implements PhysiologicalDataService {
    @Autowired
    private PhysiologicalDataMapper physiologicalDataMapper;
    @Override
    public void add(PhysiologicalData physiologicalData) {
        physiologicalData.setCreateTime(LocalDateTime.now());
        physiologicalData.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer UserId = (Integer) map.get("id");
        physiologicalData.setCreateUser(UserId);
        physiologicalDataMapper.add(physiologicalData);
    }

    @Override
    public List<PhysiologicalData> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId =(Integer) map.get("id");
        return physiologicalDataMapper.list(userId);
    }

    @Override
    public void update(PhysiologicalData physiologicalData) {
        physiologicalData.setUpdateTime(LocalDateTime.now());
        physiologicalDataMapper.update(physiologicalData);
    }

    @Override
    public void delete(Integer id) {
        physiologicalDataMapper.delete(id);
    }
}
