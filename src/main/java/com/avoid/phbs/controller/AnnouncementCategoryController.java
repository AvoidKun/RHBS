package com.avoid.phbs.controller;

import com.avoid.phbs.model.AnnouncementCategory;
import com.avoid.phbs.model.Category;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.AnnouncementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcementCategory")
public class AnnouncementCategoryController {
    @Autowired
    private AnnouncementCategoryService announcementCategoryService;
    @PostMapping
    public Result add(@RequestBody @Validated AnnouncementCategory announcementCategory){
        announcementCategoryService.add(announcementCategory);
        return Result.success();
    }
    @GetMapping
    public Result<List<AnnouncementCategory>> list(){
        List<AnnouncementCategory> categoryLists = announcementCategoryService.list();
        return Result.success(categoryLists);
    }
    @PutMapping
    public Result update(@RequestBody @Validated AnnouncementCategory announcementCategory){
        announcementCategoryService.update(announcementCategory);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        announcementCategoryService.delete(id);
        return Result.success();
    }
}
