/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * title: 文件上传服务<br>
 * description: 负责对接YOP文件上传API，返回文件URL<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
public interface FileUploadService {
    /**
     * 上传文件到YOP
     * @param file 文件
     * @param fileType 文件类型
     * @return 文件URL
     * @throws Exception 上传失败抛出异常
     */
    String uploadToYop(MultipartFile file, String fileType) throws Exception;
} 