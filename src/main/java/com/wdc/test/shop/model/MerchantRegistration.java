package com.wdc.test.shop.model;

import java.util.Date;

/**
 * 商户入网信息实体类
 */
public class MerchantRegistration {
    
    private Long id;
    private String requestNo;                // 入网请求号
    private String applicationNo;            // 申请单编号
    private String applicationStatus;        // 申请状态
    private String merchantNo;               // 商户编号
    private String businessRole;             // 入网商户业务角色
    private String parentMerchantNo;         // 上级商户编号
    
    // 证件图片URL
    private String licenceUrl;               // 商户证件照片地址
    private String legalLicenceFrontUrl;     // 法人证件正面照片地址
    private String legalLicenceBackUrl;      // 法人证件背面照片地址
    
    // 商户主体信息
    private String signName;                 // 商户签约名
    private String signType;                 // 商户签约类型
    private String licenceNo;                // 商户证件号码
    private String shortName;                // 商户简称
    
    // 法人信息
    private String legalName;                // 法人名称
    private String legalLicenceType;         // 法人证件类型
    private String legalLicenceNo;           // 法人证件编号
    
    // 联系人信息
    private String contactName;              // 联系人姓名
    private String contactMobile;            // 联系人手机号
    private String contactEmail;             // 联系人邮箱
    private String contactLicenceNo;         // 联系人证件号码
    private String adminEmail;               // 商户后台管理员邮箱
    private String adminMobile;              // 商户后台管理员手机号
    
    // 经营信息
    private String primaryIndustryCategory;  // 一级行业分类编码
    private String secondaryIndustryCategory;// 二级行业分类编码
    private String province;                 // 经营省
    private String city;                     // 经营市
    private String district;                 // 经营区
    private String address;                  // 经营地址
    
    // 结算账户信息
    private String settlementDirection;      // 结算方向
    private String bankCode;                 // 开户总行编码
    private String bankAccountType;          // 银行账户类型
    private String bankCardNo;               // 银行账户号码
    
    // 其他信息
    private String notifyUrl;                // 商户回调地址
    private Date createTime;                 // 创建时间
    private Date updateTime;                 // 更新时间
    private String rejectReason;             // 拒绝原因
    private String productInfo;              // 开通产品信息
    private String extraInfo;                // 额外信息

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getBusinessRole() {
        return businessRole;
    }

    public void setBusinessRole(String businessRole) {
        this.businessRole = businessRole;
    }

    public String getParentMerchantNo() {
        return parentMerchantNo;
    }

    public void setParentMerchantNo(String parentMerchantNo) {
        this.parentMerchantNo = parentMerchantNo;
    }

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
    }

    public String getLegalLicenceFrontUrl() {
        return legalLicenceFrontUrl;
    }

    public void setLegalLicenceFrontUrl(String legalLicenceFrontUrl) {
        this.legalLicenceFrontUrl = legalLicenceFrontUrl;
    }

    public String getLegalLicenceBackUrl() {
        return legalLicenceBackUrl;
    }

    public void setLegalLicenceBackUrl(String legalLicenceBackUrl) {
        this.legalLicenceBackUrl = legalLicenceBackUrl;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalLicenceType() {
        return legalLicenceType;
    }

    public void setLegalLicenceType(String legalLicenceType) {
        this.legalLicenceType = legalLicenceType;
    }

    public String getLegalLicenceNo() {
        return legalLicenceNo;
    }

    public void setLegalLicenceNo(String legalLicenceNo) {
        this.legalLicenceNo = legalLicenceNo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactLicenceNo() {
        return contactLicenceNo;
    }

    public void setContactLicenceNo(String contactLicenceNo) {
        this.contactLicenceNo = contactLicenceNo;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public String getPrimaryIndustryCategory() {
        return primaryIndustryCategory;
    }

    public void setPrimaryIndustryCategory(String primaryIndustryCategory) {
        this.primaryIndustryCategory = primaryIndustryCategory;
    }

    public String getSecondaryIndustryCategory() {
        return secondaryIndustryCategory;
    }

    public void setSecondaryIndustryCategory(String secondaryIndustryCategory) {
        this.secondaryIndustryCategory = secondaryIndustryCategory;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSettlementDirection() {
        return settlementDirection;
    }

    public void setSettlementDirection(String settlementDirection) {
        this.settlementDirection = settlementDirection;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankAccountType() {
        return bankAccountType;
    }

    public void setBankAccountType(String bankAccountType) {
        this.bankAccountType = bankAccountType;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
} 