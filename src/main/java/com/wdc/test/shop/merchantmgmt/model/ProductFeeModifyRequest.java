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
 * title: 产品费率修改参数<br>
 * description: 商户管理模块产品费率修改参数模型，字段与YOP接口一致<br>
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
public class ProductFeeModifyRequest implements Serializable {
    /** 商户编号 */
    private String merchantNo;
    /** 产品编码 */
    private String productCode;
    /** 费率类型 */
    private String rateType;
    /** 费率值 */
    private String percentRate;
    /** 其他扩展字段 */
    private String extraInfo;
    /** 请求号 */
    private String requestNo;
    /** 回调地址 */
    private String notifyUrl;
    /** 变更产品信息(JSON) */
    private String productInfo;
} 