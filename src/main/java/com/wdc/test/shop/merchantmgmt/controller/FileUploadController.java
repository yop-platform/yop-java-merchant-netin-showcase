/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.controller;

import com.wdc.test.shop.merchantmgmt.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * title: 文件上传控制器<br>
 * description: 独立商户管理模块的文件上传接口，负责接收前端文件并调用YOP上传<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@RestController
@RequestMapping("/merchantmgmt/file")
public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 文件上传接口
     * @param file 资质/证件图片
     * @param fileType 文件类型
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file,
                                      @RequestParam("fileType") String fileType) {
        logger.info("[merchantmgmt] 文件上传请求, fileType={}", fileType);
        Map<String, Object> result = new HashMap<>();
        try {
            String fileUrl = fileUploadService.uploadToYop(file, fileType);
            result.put("success", true);
            result.put("fileUrl", fileUrl);
        } catch (Exception e) {
            logger.error("[merchantmgmt] 文件上传失败", e);
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
} 