package com.avoid.phbs.controller;

import com.avoid.phbs.model.PageBean;
import com.avoid.phbs.model.PhysiologicalData;
import com.avoid.phbs.model.Result;
import com.avoid.phbs.service.PhysiologicalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/physiologicalData")
public class PhysiologicalDataController {
    @Autowired
    private PhysiologicalDataService physiologicalDataService;
    @PostMapping
    public Result add(@RequestBody @Validated PhysiologicalData physiologicalData){
        physiologicalDataService.add(physiologicalData);
        return Result.success();
    }

    @GetMapping
    public Result<List<PhysiologicalData>> list(){
        List<PhysiologicalData> physiologicalDataList = physiologicalDataService.list();
        return Result.success(physiologicalDataList);
    }
    @GetMapping("/page")
    public Result<PageBean<PhysiologicalData>> pages(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state
    ){
        PageBean<PhysiologicalData> pp = physiologicalDataService.pageList(pageNum,pageSize,state);
        return Result.success(pp);
    }

    @PutMapping
    public Result update(@RequestBody @Validated PhysiologicalData physiologicalData){
        physiologicalDataService.update(physiologicalData);
        return Result.success();
    }
    @DeleteMapping
    public Result delete(Integer id){
        physiologicalDataService.delete(id);
        return Result.success();
    }

}
