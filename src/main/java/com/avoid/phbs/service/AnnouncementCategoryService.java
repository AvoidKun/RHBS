package com.avoid.phbs.service;


import com.avoid.phbs.model.AnnouncementCategory;

import java.util.List;

public interface AnnouncementCategoryService {
    void add(AnnouncementCategory announcementCategory);


    List<AnnouncementCategory> list();

    void update(AnnouncementCategory announcementCategory);

    void delete(Integer id);
}
