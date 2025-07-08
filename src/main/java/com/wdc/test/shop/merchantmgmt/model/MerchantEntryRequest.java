/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * title: 商户进件申请参数<br>
 * description: SaaS服务商商户/小微商户进件参数模型，字段与YOP接口一致<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantEntryRequest implements Serializable {
    /** 入网请求号 */
    private String requestNo;
    /** 商户业务角色 */
    private String businessRole;
    /** 上级商户编号 */
    private String parentMerchantNo;
    /** 通知回调地址 */
    private String notifyUrl;
    /** 营业执照图片URL */
    private String licenceUrl;
    /** 商户签约名 */
    private String signName;
    /** 商户签约类型 */
    private String signType;
    /** 商户证件号码 */
    private String licenceNo;
    /** 商户简称 */
    private String shortName;
    /** 法人姓名 */
    private String legalName;
    /** 法人证件类型 */
    private String legalLicenceType;
    /** 法人证件号码 */
    private String legalLicenceNo;
    /** 法人证件正面图片URL */
    private String legalLicenceFrontUrl;
    /** 法人证件背面图片URL */
    private String legalLicenceBackUrl;
    /** 联系人姓名 */
    private String contactName;
    /** 联系人手机号 */
    private String contactMobile;
    /** 联系人邮箱 */
    private String contactEmail;
    /** 联系人证件号码 */
    private String contactLicenceNo;
    /** 管理员邮箱 */
    private String adminEmail;
    /** 管理员手机号 */
    private String adminMobile;
    /** 一级行业分类编码 */
    private String primaryIndustryCategory;
    /** 二级行业分类编码 */
    private String secondaryIndustryCategory;
    /** 经营省 */
    private String province;
    /** 经营市 */
    private String city;
    /** 经营区 */
    private String district;
    /** 经营地址 */
    private String address;
    /** 结算方向 */
    private String settlementDirection;
    /** 开户总行编码 */
    private String bankCode;
    /** 银行账户类型 */
    private String bankAccountType;
    /** 银行账户号码 */
    private String bankCardNo;
    /** 开通产品信息(JSON) */
    private String productInfo;
    /** 额外信息 */
    private String extraInfo;
} 