/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.service;

import com.wdc.test.shop.model.mcp.McpMerchantRegisterRequest;
import com.wdc.test.shop.model.mcp.McpMerchantRegisterResponse;

/**
 * title: MCP商户进件服务接口<br>
 * description: 定义MCP进件服务方法，独立于原有进件逻辑。<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/08
 */
public interface McpMerchantRegisterService {
    /**
     * MCP商户进件
     * @param request 进件参数
     * @return 进件响应
     */
    McpMerchantRegisterResponse register(McpMerchantRegisterRequest request);
} 