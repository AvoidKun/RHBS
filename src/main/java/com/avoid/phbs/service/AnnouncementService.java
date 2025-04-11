package com.avoid.phbs.service;

import com.avoid.phbs.model.Announcement;
import com.avoid.phbs.model.PageBean;

public interface AnnouncementService {
    void add(Announcement announcement);

    PageBean<Announcement> list(Integer pageNum, Integer pageSize, Integer announcementCategoryId, String state);

    void update(Announcement announcement);

    void deleteById(Integer id);
}
