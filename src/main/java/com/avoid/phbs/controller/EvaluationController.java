package com.avoid.phbs.controller;

import com.avoid.phbs.model.Article;
import com.avoid.phbs.model.Evaluation;
import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.EvaluationService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}