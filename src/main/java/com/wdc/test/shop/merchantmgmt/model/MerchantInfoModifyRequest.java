/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * title: 商户信息修改参数<br>
 * description: 商户管理模块信息修改参数模型，字段与YOP接口一致<br>
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
public class MerchantInfoModifyRequest implements Serializable {
    /** 商户编号 */
    private String merchantNo;
    /** 商户签约名 */
    private String signName;
    /** 商户简称 */
    private String shortName;
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
    /** 经营地址 */
    private String address;
    /** 额外信息 */
    private String extraInfo;
    /** 信息修改请求号 */
    private String requestNo;
    /** 回调地址 */
    private String notifyUrl;
    /** 商户主体信息(JSON) */
    private String merchantSubjectInfo;
    /** 商户联系人信息(JSON) */
    private String merchantContactInfo;
    /** 经营地址信息(JSON) */
    private String businessAddressInfo;
} 