/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service.impl;

import com.wdc.test.shop.merchantmgmt.service.FileUploadService;
import com.wdc.test.shop.merchantmgmt.payment.yop.YopFileUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * title: 文件上传服务实现<br>
 * description: 实现文件上传到YOP，返回文件URL<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    @Autowired
    private YopFileUploadClient yopFileUploadClient;

    @Override
    public String uploadToYop(MultipartFile file, String fileType) throws Exception {
        logger.info("[merchantmgmt] 调用YOP上传文件, fileType={}", fileType);
        return yopFileUploadClient.upload(file, fileType);
    }
} 