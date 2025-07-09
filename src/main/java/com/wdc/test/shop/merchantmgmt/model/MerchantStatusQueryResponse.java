/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.merchantmgmt.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * title: 商户状态查询响应<br>
 * description: 商户状态查询响应参数模型，对应YOP接口返回字段<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantStatusQueryResponse implements Serializable {
    /** 响应编码 */
    private String returnCode;
    /** 响应描述 */
    private String returnMsg;
    /** 商户编号 */
    private String merchantNo;
    /** 商户状态（ACTIVE/FROZEN/OPERATION_EXCEPTION） */
    private String merchantStatus;
    /** 运营异常原因 */
    private List<String> reasonList;
    /** 是否成功 */
    public boolean isSuccess() {
        return "NIG00000".equals(returnCode);
    }
} 