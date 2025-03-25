package com.avoid.phbs.controller;

import com.avoid.phbs.model.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUpLoadController {
    private static final String ACCESS_BASE_URL = "@/assets/RHBS_FILE/";
    private String accessBaseUrl = ACCESS_BASE_URL;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //把文件内容储存到本地磁盘上
        String originalFilename =  file.getOriginalFilename();
        //保证文件名字唯一防覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("E:\\Project\\over\\test\\RHBS\\RHBS_FRONT\\src\\assets\\RHBS_FILE\\"+filename));
        String fileAcceessUrl =accessBaseUrl +filename;
        return Result.success(fileAcceessUrl);
    }
}
