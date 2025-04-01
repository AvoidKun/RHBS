package com.avoid.phbs.service;

import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.PhysiologicalData;

import java.util.List;

public interface PhysiologicalDataService {
    void add(PhysiologicalData physiologicalData);

    List<PhysiologicalData> list();

    void update(PhysiologicalData physiologicalData);

    void delete(Integer id);

    PageBean<PhysiologicalData> pageList(Integer pageNum, Integer pageSize, String state);
}
