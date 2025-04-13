package com.avoid.phbs.service.impl;

import com.avoid.phbs.mapper.AnnouncementMapper;
import com.avoid.phbs.mapper.ArticleMapper;
import com.avoid.phbs.model.Announcement;
import com.avoid.phbs.model.Article;
import com.avoid.phbs.model.Evaluation;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.service.AnnouncementService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;
    @Override
    public void add(Announcement announcement) {
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer adminId = (Integer) map.get("id");
        announcement.setCreateAdmin(adminId);
        announcementMapper.add(announcement);
    }

    @Override
    public PageBean<Announcement> list(Integer pageNum, Integer pageSize, Integer announcementCategoryId, String state) {
        //创建PageBean对象
        PageBean<Announcement> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper
        Map<String,Object> map  = ThreadLocalUtil.get();
        Integer adminId = (Integer)map.get("id");
        List<Announcement> as = announcementMapper.list(adminId,announcementCategoryId,state);
        Page<Announcement> page = (Page<Announcement>) as ;

        //把数据传到PageBean中
        pb.setTotal(page.getTotal());
        pb.setItems(page.getResult());
        return pb;
    }

    @Override
    public void update(Announcement announcement) {
        announcement.setUpdateTime(LocalDateTime.now());
        announcementMapper.update(announcement);
    }

    @Override
    public void deleteById(Integer id) {
        announcementMapper.deleteById(id);
    }

    @Override
    public PageBean<Evaluation> findEvalution(Integer pageNum, Integer pageSize, String state) {
        PageBean<Evaluation> ep = new PageBean<>();
        PageHelper.startPage(pageNum,pageSize);
        List<Evaluation> al = announcementMapper.findEvalution(state);
        Page<Evaluation> page = (Page<Evaluation>) al;
        ep.setItems(page.getResult());
        ep.setTotal(page.getTotal());
        return ep;
    }
}

