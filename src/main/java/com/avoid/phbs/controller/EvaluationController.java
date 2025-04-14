package com.avoid.phbs.controller;

import com.avoid.phbs.model.*;
import com.avoid.phbs.service.EvaluationService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public Result add(@RequestBody @Validated Evaluation evaluation) {
        evaluationService.add(evaluation);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Evaluation>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state
    ) {
        PageBean<Evaluation> ep = evaluationService.list(pageNum, pageSize, state);
        return Result.success(ep);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Evaluation evaluation) {
        evaluationService.update(evaluation);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        evaluationService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/medicines")
    public Result<PageBean<Medicines>> medicinesList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state
    ){
        PageBean<Medicines> pm = evaluationService.medicinesList(pageNum,pageSize,state);
        return Result.success(pm);
    }

    @GetMapping("/category")
    public Result<List<AnnouncementCategory>> categorylist(){
        List<AnnouncementCategory> categoryLists = evaluationService.categorylist();
        return Result.success(categoryLists);
    }
    @GetMapping("/announcement")
    public Result<PageBean<Announcement>> announcementlist(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer announcementCategoryId,
            @RequestParam( defaultValue= "已发布" ) String state
    ){
        PageBean<Announcement> pb = evaluationService.announcementlist(pageNum,pageSize,announcementCategoryId,state);
        return Result.success(pb);
    }
    @GetMapping("/advice")
    public Result<PageBean<Advice>> advicelist(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue= "已发布") String state
    ) {
        PageBean<Advice> ap = evaluationService.advicelist(pageNum, pageSize, userId, state);
        return Result.success(ap);
    }

}