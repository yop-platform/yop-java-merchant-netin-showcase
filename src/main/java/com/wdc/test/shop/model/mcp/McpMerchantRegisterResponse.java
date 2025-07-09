/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.model.mcp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * title: MCP商户进件响应模型<br>
 * description: 对应MCP /rest/v2.0/mer/register/saas/micro接口响应结构，所有字段与官方文档一致，便于后续扩展。<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McpMerchantRegisterResponse {
    /**
     * 响应编码，成功为NIG00000
     */
    private String returnCode;
    /**
     * 响应信息
     */
    private String returnMsg;
    /**
     * 申请状态
     * REVIEWING:申请审核中
     * REVIEW_BACK:申请已驳回
     * AUTHENTICITY_VERIFYING:真实性验证中
     * AGREEMENT_SIGNING:协议待签署
     * BUSINESS_OPENING:业务开通中
     * COMPLETED:申请已完成
     */
    private String applicationStatus;
    /**
     * 申请单编号
     */
    private String applicationNo;
    /**
     * 入网请求号
     */
    private String requestNo;
    /**
     * 商户编号
     */
    private String merchantNo;

    /**
     * 判断业务是否成功
     * @return true-成功，false-失败
     */
    public boolean isSuccess() {
        return "NIG00000".equals(returnCode);
    }
} 