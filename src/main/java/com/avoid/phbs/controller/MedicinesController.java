package com.avoid.phbs.controller;


import com.avoid.phbs.model.Article;
import com.avoid.phbs.model.Medicines;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.MedicinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {
    @Autowired
    private MedicinesService medicinesService;
    @PostMapping
    public Result add(@RequestBody @Validated Medicines medicines){
        medicinesService.add(medicines);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Medicines>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state
    ){
        PageBean<Medicines> pm = medicinesService.list(pageNum,pageSize,state);
        return Result.success(pm);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Medicines medicines){
        medicinesService.update(medicines);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        medicinesService.deleteById(id);
        return Result.success();
    }
}

