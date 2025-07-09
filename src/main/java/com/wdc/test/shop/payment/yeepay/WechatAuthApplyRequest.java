package com.wdc.test.shop.payment.yeepay;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信实名认证申请单申请-请求参数
 */
@Data
@EqualsAndHashCode
public class WechatAuthApplyRequest {
    /** 请求号，必填 */
    private String requestNo;
    /** 商户编号，必填 */
    private String subMerchantNo;
    /** 申请人类型，必填 LEGAL/TRANSACTOR */
    private String applicantType;
    /** 申请人姓名，部分场景必填 */
    private String applicantName;
    /** 申请人手机号，必填 */
    private String applicantPhone;
    /** 申请人身份证号，部分场景必填 */
    private String applicantIdCard;
    /** 经办人信息，JSON字符串，部分场景必填 */
    private String transactorInfo;
    /** 法人证件类型，部分场景必填 */
    private String identificationType;
    /** 法人证件正面，部分场景必填 */
    private String identificationFrontCopy;
    /** 法人证件反面，部分场景必填 */
    private String identificationBackCopy;
    /** 法人证件有效期，必填，格式["yyyy-MM-dd","forever"] */
    private String identificationValidDate;
    /** 法人证件居住地址，部分场景必填 */
    private String identificationAddress;
    /** 主体证件照片，部分场景必填 */
    private String certCopy;
    /** 主体注册地址，必填 */
    private String companyAddress;
    /** 主体证件有效期，部分场景必填 */
    private String licenceValidDate;
    /** 是否金融机构，部分场景必填 */
    private Boolean isFinanceInstitution;
    /** 金融机构许可证，部分场景必填 */
    private String financeInstitutionInfo;
    /** 登记证书类型，部分场景必填 */
    private String certType;
    /** 登记证书编号，部分场景必填 */
    private String certNumber;
    /** 单位证明函照片，部分场景必填 */
    private String companyProveCopy;
    /** 法人是否为受益人，部分场景必填 */
    private Boolean owner;
    /** 受益人信息列表(UBO)，部分场景必填 */
    private String uboInfoList;
    /** 报备费率，部分场景必填 */
    private String reportFee;
    /** 渠道号，部分场景必填 */
    private String channelId;
    /** 小微经营类型，部分场景必填 */
    private String microBizType;
    /** 门店名称，部分场景必填 */
    private String storeName;
    /** 门店省市编码，部分场景必填 */
    private String storeAddressCode;
    /** 门头照片，部分场景必填 */
    private String storeHeaderCopy;
    /** 店内照片，部分场景必填 */
    private String storeIndoorCopy;
} 