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
 * title: 商户信息修改响应<br>
 * description: 商户管理模块信息修改响应参数，含YOP返回主要字段<br>
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
public class MerchantInfoModifyResponse implements Serializable {
    /** 响应编码，成功为NIG00000 */
    private String returnCode;
    /** 响应信息 */
    private String returnMsg;
    /** 商户编号 */
    private String merchantNo;
    /** 其他扩展字段 */
    private String extraInfo;

    /**
     * 是否成功
     */
    public boolean isSuccess() {
        return "NIG00000".equals(returnCode);
    }
} 