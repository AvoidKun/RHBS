package com.avoid.phbs.controller;


import com.avoid.phbs.model.Announcement;
import com.avoid.phbs.model.Evaluation;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private AnnouncementService announcementService;
    @PostMapping
    public Result add(@RequestBody @Validated Announcement announcement){
        announcementService.add(announcement);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Announcement>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer announcementCategoryId,
            @RequestParam(required = false) String state
    ){
        PageBean<Announcement> pb = announcementService.list(pageNum,pageSize,announcementCategoryId,state);
        return Result.success(pb);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Announcement announcement){
        announcementService.update(announcement);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        announcementService.deleteById(id);
        return Result.success();
    }
    @GetMapping("/evaluation")
    public Result<PageBean<Evaluation>> evaluetion(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state
    ){
        PageBean<Evaluation> evaluation = announcementService.findEvalution(pageNum,pageSize,state);
        return Result.success(evaluation);
    }
}

