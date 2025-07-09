/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.payment.yop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YosUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * title: YOP文件上传客户端<br>
 * description: 负责对接YOP文件上传API，返回文件URL<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Component
public class YopFileUploadClient {
    private static final Logger logger = LoggerFactory.getLogger(YopFileUploadClient.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private YopClient yopClient;

    @Value("${yeepay.merchant.file.upload.path:/yos/v1.0/sys/merchant/qual/upload}")
    private String fileUploadPath;
    @Value("${yeepay.appkey.prefix:sandbox_rsa_}")
    private String appKeyPrefix;
    @Value("${yeepay.saas.merchant.no:10080662589}")
    private String saasMerchantNo;

    @PostConstruct
    public void init() {
        yopClient = YopClientBuilder.builder().build();
        logger.info("YOP文件上传客户端初始化成功");
    }

    /**
     * 上传文件到YOP
     * @param file 文件
     * @param fileType 文件类型
     * @return 文件URL
     * @throws Exception 上传失败抛出异常
     */
    public String upload(MultipartFile file, String fileType) throws Exception {
        logger.info("[merchantmgmt] YOP文件上传开始, fileType={}", fileType);
        File tempFile = null;
        try {
            tempFile = convertMultipartFileToFile(file);
            YopRequest request = new YopRequest(fileUploadPath, "POST");
            request.getRequestConfig().setAppKey(appKeyPrefix + saasMerchantNo);
            request.addMultiPartFile("merQual", new FileInputStream(tempFile));
            logger.info("请求API：{}", fileUploadPath);
            YosUploadResponse response = yopClient.upload(request);
            String result = response.getStringResult();
            logger.info("YOP文件上传响应：{}", result);
            // 解析响应，提取文件URL
            YopFileUploadResponse uploadResponse = OBJECT_MAPPER.readValue(result, YopFileUploadResponse.class);
            if (!uploadResponse.isSuccess()) {
                throw new RuntimeException("YOP文件上传失败：" + uploadResponse.getReturnMsg());
            }
            return uploadResponse.getMerQualUrl();
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
            logger.info("[merchantmgmt] YOP文件上传结束");
        }
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        Path tempDir = Files.createTempDirectory("yop-mgmt-upload-");
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            fileName = UUID.randomUUID().toString() + ".jpg";
        }
        Path filePath = Paths.get(tempDir.toString(), fileName);
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toFile();
    }

    /**
     * YOP文件上传响应模型（内部类）
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class YopFileUploadResponse {
        private String returnCode;
        private String returnMsg;
        private String merQualUrl;
        public boolean isSuccess() { return "REG00000".equals(returnCode); }
        public String getReturnCode() { return returnCode; }
        public void setReturnCode(String returnCode) { this.returnCode = returnCode; }
        public String getReturnMsg() { return returnMsg; }
        public void setReturnMsg(String returnMsg) { this.returnMsg = returnMsg; }
        public String getMerQualUrl() { return merQualUrl; }
        public void setMerQualUrl(String merQualUrl) { this.merQualUrl = merQualUrl; }
    }
} 