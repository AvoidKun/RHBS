package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.AnnouncementCategoryMapper;
import com.avoid.phbs.mapper.CategoryMapper;
import com.avoid.phbs.model.AnnouncementCategory;
import com.avoid.phbs.model.Category;
import com.avoid.phbs.service.AnnouncementCategoryService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AnnouncementCategoryServiceImpl implements AnnouncementCategoryService {
    @Autowired
    private AnnouncementCategoryMapper announcementCategoryMapper;
    @Override
    public void add(AnnouncementCategory announcementCategory) {
        announcementCategory.setCreateTime(LocalDateTime.now());
        announcementCategory.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        announcementCategory.setCreateAdmin(adminId);
        announcementCategoryMapper.add(announcementCategory);
    }

    @Override
    public List<AnnouncementCategory> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer adminId=(Integer) map.get("id");
        return announcementCategoryMapper.list(adminId);
    }

    @Override
    public void update(AnnouncementCategory announcementCategory) {
        announcementCategory.setUpdateTime(LocalDateTime.now());
        announcementCategoryMapper.update(announcementCategory);
    }

    @Override
    public void delete(Integer id) {
        announcementCategoryMapper.delete(id);
    }
}
