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
 * title: 商户信息修改进度查询请求<br>
 * description: 商户信息修改进度查询参数模型，对应YOP接口requestNo<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantInfoModifyQueryRequest implements Serializable {
    /** 商户信息修改请求号 */
    private String requestNo;
} 