package com.avoid.phbs.controller;


import com.avoid.phbs.model.Advice;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Autowired
    private AdviceService adviceService;
    @PostMapping
    public Result add(@RequestBody @Validated Advice advice){
        adviceService.add(advice);
        return Result.success();
    }
    @GetMapping
    public Result<PageBean<Advice>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String state
    ){
        PageBean<Advice> ap = adviceService.list(pageNum, pageSize, userId,state);
        return Result.success(ap);
    }
    @PutMapping
    public Result update(@RequestBody @Validated Advice advice){
        adviceService.update(advice);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        adviceService.deleteById(id);
        return Result.success();
    }
}
