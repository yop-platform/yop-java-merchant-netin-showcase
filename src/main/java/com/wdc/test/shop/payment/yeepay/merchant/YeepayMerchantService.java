package com.wdc.test.shop.payment.yeepay.merchant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.model.MerchantRegistration;
import com.wdc.test.shop.payment.yeepay.YeepayConfig;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YopResponse;
import com.yeepay.yop.sdk.service.common.response.YosUploadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 易宝商户入网服务
 */
@Service
public class YeepayMerchantService {

    private static final Logger logger = LoggerFactory.getLogger(YeepayMerchantService.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    private final YeepayConfig yeepayConfig;
    private YopClient yopClient;
    
    @Autowired
    public YeepayMerchantService(YeepayConfig yeepayConfig) {
        this.yeepayConfig = yeepayConfig;
    }
    
    @PostConstruct
    public void init() {
        try {
            // 初始化YOP客户端
            yopClient = YopClientBuilder.builder().build();
            logger.info("易宝支付商户入网SDK初始化成功");
        } catch (Exception e) {
            logger.error("初始化易宝支付商户入网SDK失败", e);
        }
    }
    
    /**
     * 上传商户资质文件
     * @param file 商户资质文件
     * @return 上传结果
     */
    public YeepayMerchantFileUploadResponse uploadMerchantFile(MultipartFile file) {
        logger.info("==== 开始易宝商户资质文件上传 ====");
        
        try {
            // 将MultipartFile转换为File
            File tempFile = convertMultipartFileToFile(file);
            
            // 构建请求
            YopRequest request = new YopRequest(yeepayConfig.getMerchantFileUploadPath(), "POST");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getSaasMerchantNo());
            request.addMultiPartFile("merQual", new FileInputStream(tempFile));
            
            logger.info("请求API：{}", yeepayConfig.getMerchantFileUploadPath());
            
            // 发起请求
            YosUploadResponse response = yopClient.upload(request);
            
            // 处理响应
            String result = response.getStringResult();
            YeepayMerchantFileUploadResponse uploadResponse = OBJECT_MAPPER.readValue(result, YeepayMerchantFileUploadResponse.class);
            
            logger.info("易宝商户资质文件上传结果：{}", uploadResponse);
            
            // 删除临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
            
            return uploadResponse;
        } catch (Exception e) {
            logger.error("易宝商户资质文件上传异常", e);
            YeepayMerchantFileUploadResponse errorResponse = new YeepayMerchantFileUploadResponse();
            errorResponse.setReturnCode("ERROR");
            errorResponse.setReturnMsg("文件上传异常：" + e.getMessage());
            return errorResponse;
        } finally {
            logger.info("==== 结束易宝商户资质文件上传 ====");
        }
    }
    
    /**
     * 商户注册
     * @param registration 商户注册信息
     * @return 注册结果
     */
    public YeepayMerchantRegisterResponse registerMerchant(MerchantRegistration registration) {
        logger.info("==== 开始易宝商户入网 ====");
        
        try {
            // 构建请求参数
            Map<String, Object> params = buildRegisterParams(registration);
            logger.debug("易宝商户入网请求参数：{}", OBJECT_MAPPER.writeValueAsString(params));
            
            // 构建请求
            YopRequest request = new YopRequest(yeepayConfig.getMerchantRegisterPath(), "POST");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getSaasMerchantNo());
            
            // 添加请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    request.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                }
            }
            
            logger.info("请求API：{}", yeepayConfig.getMerchantRegisterPath());
            
            // 发起请求
            YopResponse response = yopClient.request(request);
            
            // 处理响应
            String result = response.getStringResult();
            YeepayMerchantRegisterResponse registerResponse = OBJECT_MAPPER.readValue(result, YeepayMerchantRegisterResponse.class);
            
            logger.info("易宝商户入网结果：{}", registerResponse);
            
            return registerResponse;
        } catch (Exception e) {
            logger.error("易宝商户入网异常", e);
            YeepayMerchantRegisterResponse errorResponse = new YeepayMerchantRegisterResponse();
            errorResponse.setReturnCode("ERROR");
            errorResponse.setReturnMsg("商户入网异常：" + e.getMessage());
            return errorResponse;
        } finally {
            logger.info("==== 结束易宝商户入网 ====");
        }
    }
    
    /**
     * 将MultipartFile转换为File
     */
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        Path tempDir = Files.createTempDirectory("yeepay-upload-");
        String fileName = multipartFile.getOriginalFilename();
        if (fileName == null) {
            fileName = UUID.randomUUID().toString() + ".jpg";
        }
        Path filePath = Paths.get(tempDir.toString(), fileName);
        Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toFile();
    }
    
    /**
     * 构建商户入网请求参数
     */
    private Map<String, Object> buildRegisterParams(MerchantRegistration registration) throws JsonProcessingException {
        Map<String, Object> params = new HashMap<>();
        
        // 基本信息
        params.put("requestNo", registration.getRequestNo());
        params.put("businessRole", registration.getBusinessRole());
        params.put("parentMerchantNo", yeepayConfig.getSaasMerchantNo());
        params.put("notifyUrl", yeepayConfig.getMerchantRegisterNotifyUrl());
        
        // 商户主体信息
        Map<String, String> merchantSubjectInfo = new HashMap<>();
        merchantSubjectInfo.put("licenceUrl", registration.getLicenceUrl());
        merchantSubjectInfo.put("signName", registration.getSignName());
        merchantSubjectInfo.put("signType", registration.getSignType());
        merchantSubjectInfo.put("licenceNo", registration.getLicenceNo());
        merchantSubjectInfo.put("shortName", registration.getShortName());
        params.put("merchantSubjectInfo", OBJECT_MAPPER.writeValueAsString(merchantSubjectInfo));
        
        // 商户法人信息
        Map<String, String> merchantCorporationInfo = new HashMap<>();
        merchantCorporationInfo.put("legalName", registration.getLegalName());
        merchantCorporationInfo.put("legalLicenceType", registration.getLegalLicenceType());
        merchantCorporationInfo.put("legalLicenceNo", registration.getLegalLicenceNo());
        merchantCorporationInfo.put("legalLicenceFrontUrl", registration.getLegalLicenceFrontUrl());
        merchantCorporationInfo.put("legalLicenceBackUrl", registration.getLegalLicenceBackUrl());
        params.put("merchantCorporationInfo", OBJECT_MAPPER.writeValueAsString(merchantCorporationInfo));
        
        // 商户联系人信息
        Map<String, String> merchantContactInfo = new HashMap<>();
        merchantContactInfo.put("contactName", registration.getContactName());
        merchantContactInfo.put("contactMobile", registration.getContactMobile());
        merchantContactInfo.put("contactEmail", registration.getContactEmail());
        merchantContactInfo.put("contactLicenceNo", registration.getContactLicenceNo());
        merchantContactInfo.put("adminEmail", registration.getAdminEmail());
        merchantContactInfo.put("adminMobile", registration.getAdminMobile());
        params.put("merchantContactInfo", OBJECT_MAPPER.writeValueAsString(merchantContactInfo));
        
        // 经营类目
        if (registration.getPrimaryIndustryCategory() != null && registration.getSecondaryIndustryCategory() != null) {
            Map<String, String> industryCategoryInfo = new HashMap<>();
            industryCategoryInfo.put("primaryIndustryCategory", registration.getPrimaryIndustryCategory());
            industryCategoryInfo.put("secondaryIndustryCategory", registration.getSecondaryIndustryCategory());
            params.put("industryCategoryInfo", OBJECT_MAPPER.writeValueAsString(industryCategoryInfo));
        }
        
        // 经营地址
        Map<String, String> businessAddressInfo = new HashMap<>();
        businessAddressInfo.put("province", registration.getProvince());
        businessAddressInfo.put("city", registration.getCity());
        businessAddressInfo.put("district", registration.getDistrict());
        businessAddressInfo.put("address", registration.getAddress());
        params.put("businessAddressInfo", OBJECT_MAPPER.writeValueAsString(businessAddressInfo));
        
        // 结算账户信息
        if (registration.getBankCardNo() != null) {
            Map<String, String> settlementAccountInfo = new HashMap<>();
            settlementAccountInfo.put("settlementDirection", registration.getSettlementDirection());
            settlementAccountInfo.put("bankCode", registration.getBankCode());
            settlementAccountInfo.put("bankAccountType", registration.getBankAccountType());
            settlementAccountInfo.put("bankCardNo", registration.getBankCardNo());
            params.put("settlementAccountInfo", OBJECT_MAPPER.writeValueAsString(settlementAccountInfo));
        }
        
        // 产品信息
        params.put("productInfo", registration.getProductInfo());
        
        return params;
    }
    
    /**
     * 处理入网回调通知
     * @param params 回调参数
     * @return 处理结果
     */
    public boolean processRegisterCallback(Map<String, String> params) {
        logger.info("==== 开始处理易宝商户入网回调 ====");
        logger.info("回调参数：{}", params);
        
        try {
            // 验证参数
            String merchantNo = params.get("merchantNo");
            String applicationStatus = params.get("applicationStatus");
            
            if (merchantNo == null || applicationStatus == null) {
                logger.error("回调参数不完整");
                return false;
            }
            
            logger.info("商户入网回调信息：商户号={}, 状态={}", merchantNo, applicationStatus);
            
            // 处理回调，更新数据库状态等操作由业务层完成
            return true;
        } catch (Exception e) {
            logger.error("处理易宝商户入网回调异常", e);
            return false;
        } finally {
            logger.info("==== 结束处理易宝商户入网回调 ====");
        }
    }
} 