package com.avoid.phbs.controller;


import com.avoid.phbs.model.*;
import com.avoid.phbs.service.AdviceService;
import com.avoid.phbs.service.UserService;
import com.avoid.phbs.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/advice")
public class AdviceController {
    @Autowired
    private AdviceService adviceService;
    @PostMapping
    public Result add(@RequestBody @Validated Advice advice) {
        adviceService.add(advice);
        return Result.success();
    }

    @GetMapping
    public Result<PageBean<Advice>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Advice> ap = adviceService.list(pageNum, pageSize, userId, state);
        return Result.success(ap);
    }

    @PutMapping
    public Result update(@RequestBody @Validated Advice advice) {
        adviceService.update(advice);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id) {
        adviceService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/userinfo")
    public Result<List<User>> userInfo() {
        List<User> user = adviceService.findUserInfo();
        return Result.success(user);
    }
    @GetMapping("/physiological")
    public Result<PageBean<PhysiologicalData>> physiologicalData(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer createUser,
            @RequestParam(required = false) String state
    ) {
        PageBean<PhysiologicalData> physiological = adviceService.findUserPhysiologicalData(pageNum,pageSize,createUser,state);
        return Result.success(physiological);
    }

   @GetMapping("/chart")
    public Result<List<PhysiologicalData>> physiologicalChart(){
        List<PhysiologicalData> physiological = adviceService.findPhysiologicalData();
        return Result.success(physiological);
   }
}
