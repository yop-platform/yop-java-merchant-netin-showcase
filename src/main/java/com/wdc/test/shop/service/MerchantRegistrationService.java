package com.wdc.test.shop.service;

import com.wdc.test.shop.model.MerchantRegistration;
import com.wdc.test.shop.payment.yeepay.merchant.YeepayMerchantFileUploadResponse;
import com.wdc.test.shop.payment.yeepay.merchant.YeepayMerchantRegisterResponse;
import com.wdc.test.shop.payment.yeepay.merchant.YeepayMerchantService;
import com.wdc.test.shop.repository.MerchantRegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商户入网业务逻辑
 */
@Service
public class MerchantRegistrationService {
    
    private static final Logger logger = LoggerFactory.getLogger(MerchantRegistrationService.class);
    
    private final MerchantRegistrationRepository merchantRegistrationRepository;
    private final YeepayMerchantService yeepayMerchantService;
    
    @Autowired
    public MerchantRegistrationService(MerchantRegistrationRepository merchantRegistrationRepository,
                                       YeepayMerchantService yeepayMerchantService) {
        this.merchantRegistrationRepository = merchantRegistrationRepository;
        this.yeepayMerchantService = yeepayMerchantService;
    }
    
    @PostConstruct
    public void init() {
        merchantRegistrationRepository.initTable();
        logger.info("商户入网表初始化完成");
    }
    
    /**
     * 获取所有商户入网记录
     */
    public List<MerchantRegistration> getAllMerchantRegistrations() {
        return merchantRegistrationRepository.findAll();
    }
    
    /**
     * 根据ID获取商户入网记录
     */
    public MerchantRegistration getMerchantRegistrationById(Long id) {
        return merchantRegistrationRepository.findById(id);
    }
    
    /**
     * 上传商户资质文件
     * @param file 文件
     * @param fileType 文件类型（licence:营业执照, legalFront:法人证件正面, legalBack:法人证件背面）
     * @return 上传结果
     */
    public UploadResult uploadMerchantFile(MultipartFile file, String fileType) {
        logger.info("上传商户资质文件，类型：{}", fileType);
        
        if (file.isEmpty()) {
            logger.error("文件为空");
            return new UploadResult(false, "上传文件不能为空", null);
        }
        
        try {
            // 调用易宝上传文件API
            YeepayMerchantFileUploadResponse response = yeepayMerchantService.uploadMerchantFile(file);
            
            if (response.isSuccess()) {
                logger.info("文件上传成功，文件URL：{}", response.getMerQualUrl());
                return new UploadResult(true, "文件上传成功", response.getMerQualUrl());
            } else {
                logger.error("文件上传失败：{}", response.getReturnMsg());
                return new UploadResult(false, "文件上传失败：" + response.getReturnMsg(), null);
            }
        } catch (Exception e) {
            logger.error("文件上传异常", e);
            return new UploadResult(false, "文件上传异常：" + e.getMessage(), null);
        }
    }
    
    /**
     * 提交商户入网申请
     */
    public MerchantRegisterResult registerMerchant(MerchantRegistration registration) {
        logger.info("提交商户入网申请：{}", registration.getSignName());
        
        try {
            // 生成请求号
            if (registration.getRequestNo() == null) {
                String requestNo = generateRequestNo();
                registration.setRequestNo(requestNo);
                logger.info("生成入网请求号：{}", requestNo);
            }
            
            // 设置默认值
            if (registration.getBusinessRole() == null) {
                registration.setBusinessRole("ORDINARY_MERCHANT");
            }
            
            // 设置初始状态
            registration.setApplicationStatus("PROCESSING");
            
            // 保存入网信息
            MerchantRegistration savedRegistration = merchantRegistrationRepository.save(registration);
            logger.info("保存商户入网信息，ID：{}", savedRegistration.getId());
            
            // 调用易宝入网API
            YeepayMerchantRegisterResponse response = yeepayMerchantService.registerMerchant(savedRegistration);
            
            if (response.isSuccess()) {
                // 更新入网信息
                savedRegistration.setApplicationNo(response.getApplicationNo());
                savedRegistration.setApplicationStatus(response.getApplicationStatus());
                savedRegistration.setMerchantNo(response.getMerchantNo());
                merchantRegistrationRepository.save(savedRegistration);
                
                logger.info("商户入网申请成功，申请单号：{}，商户号：{}", response.getApplicationNo(), response.getMerchantNo());
                return new MerchantRegisterResult(true, "商户入网申请成功", savedRegistration);
            } else {
                // 更新状态
                savedRegistration.setApplicationStatus("FAILED");
                savedRegistration.setRejectReason(response.getReturnMsg());
                merchantRegistrationRepository.save(savedRegistration);
                
                logger.error("商户入网申请失败：{}", response.getReturnMsg());
                return new MerchantRegisterResult(false, "商户入网申请失败：" + response.getReturnMsg(), savedRegistration);
            }
        } catch (Exception e) {
            logger.error("商户入网申请异常", e);
            return new MerchantRegisterResult(false, "商户入网申请异常：" + e.getMessage(), null);
        }
    }
    
    /**
     * 处理商户入网回调
     */
    public boolean processRegisterCallback(Map<String, String> params) {
        logger.info("处理商户入网回调：{}", params);
        
        try {
            // 检查必要参数
            String requestNo = params.get("requestNo");
            String applicationStatus = params.get("applicationStatus");
            String merchantNo = params.get("merchantNo");
            
            if (requestNo == null) {
                logger.error("回调参数缺少requestNo");
                return false;
            }
            
            // 查询入网记录
            MerchantRegistration registration = merchantRegistrationRepository.findByRequestNo(requestNo);
            if (registration == null) {
                logger.error("未找到对应的入网记录：{}", requestNo);
                return false;
            }
            
            // 更新入网状态
            registration.setApplicationStatus(applicationStatus);
            if (merchantNo != null) {
                registration.setMerchantNo(merchantNo);
            }
            
            // 如果申请被拒绝，记录拒绝原因
            if ("REVIEW_BACK".equals(applicationStatus)) {
                String rejectReason = params.get("rejectReason");
                registration.setRejectReason(rejectReason);
                logger.info("入网申请被拒绝，原因：{}", rejectReason);
            }
            
            // 保存更新
            merchantRegistrationRepository.save(registration);
            logger.info("更新入网记录状态：{} -> {}", registration.getId(), applicationStatus);
            
            return true;
        } catch (Exception e) {
            logger.error("处理商户入网回调异常", e);
            return false;
        }
    }
    
    /**
     * 生成入网请求号
     */
    private String generateRequestNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return "YOP" + sdf.format(new Date()) + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }
    
    /**
     * 文件上传结果类
     */
    public static class UploadResult {
        private boolean success;
        private String message;
        private String fileUrl;
        
        public UploadResult(boolean success, String message, String fileUrl) {
            this.success = success;
            this.message = message;
            this.fileUrl = fileUrl;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public String getFileUrl() {
            return fileUrl;
        }
    }
    
    /**
     * 商户注册结果类
     */
    public static class MerchantRegisterResult {
        private boolean success;
        private String message;
        private MerchantRegistration registration;
        
        public MerchantRegisterResult(boolean success, String message, MerchantRegistration registration) {
            this.success = success;
            this.message = message;
            this.registration = registration;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
        
        public MerchantRegistration getRegistration() {
            return registration;
        }
    }
} 