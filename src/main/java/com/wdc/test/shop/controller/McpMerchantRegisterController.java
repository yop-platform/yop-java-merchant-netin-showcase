/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.mcp.McpMerchantRegisterRequest;
import com.wdc.test.shop.model.mcp.McpMerchantRegisterResponse;
import com.wdc.test.shop.service.McpMerchantRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * title: MCP商户进件接口<br>
 * description: MCP /rest/v2.0/mer/register/saas/micro专用进件接口，独立于原有进件逻辑。<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/08
 */
@Slf4j
@RestController
@RequestMapping("/mcp/merchant/register/saas/micro")
public class McpMerchantRegisterController {

    @Autowired
    private McpMerchantRegisterService mcpMerchantRegisterService;

    /**
     * MCP商户进件接口
     * @param request 进件参数
     * @return 进件响应
     */
    @PostMapping
    public McpMerchantRegisterResponse register(@RequestBody McpMerchantRegisterRequest request) {
        log.info("[MCP进件] 请求参数: {}", request);
        try {
            McpMerchantRegisterResponse response = mcpMerchantRegisterService.register(request);
            log.info("[MCP进件] 响应: {}", response);
            return response;
        } catch (Exception e) {
            log.error("[MCP进件] 异常: {}", e.getMessage(), e);
            McpMerchantRegisterResponse error = new McpMerchantRegisterResponse();
            error.setReturnCode("ERROR");
            error.setReturnMsg("系统异常: " + e.getMessage());
            return error;
        }
    }
} 