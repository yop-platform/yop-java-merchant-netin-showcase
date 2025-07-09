/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * title: 商户进件实体<br>
 * description: 持久化进件主要信息，便于列表与详情展示<br>
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
public class MerchantEntry implements Serializable {
    /** 主键ID */
    private Long id;
    /** 入网请求号 */
    private String requestNo;
    /** 商户签约名 */
    private String signName;
    /** 商户简称 */
    private String shortName;
    /** 营业执照图片URL */
    private String licenceUrl;
    /** 商户证件号码 */
    private String licenceNo;
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
    /** 进件申请单号 */
    private String applicationNo;
    /** 商户编号 */
    private String merchantNo;
    /** 进件状态 */
    private String applicationStatus;
    /** 响应信息 */
    private String returnMsg;
    /** 创建时间 */
    private Date createTime;
} 