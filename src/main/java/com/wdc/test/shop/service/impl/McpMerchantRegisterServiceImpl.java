/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.service.impl;

import com.wdc.test.shop.model.mcp.McpMerchantRegisterRequest;
import com.wdc.test.shop.model.mcp.McpMerchantRegisterResponse;
import com.wdc.test.shop.service.McpMerchantRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;

/**
 * title: MCP商户进件服务实现<br>
 * description: 实现MCP进件服务，参数过滤、日志、调用YOP-MCP接口（留TODO），独立于原有逻辑。<br>
 * Copyright: Copyright (c)2014<br>
 * Company: 易宝支付(YeePay)<br>
 *
 * @author wdc-agent
 * @version 1.0.0
 * @since 2024/06/08
 */
@Slf4j
@Service
public class McpMerchantRegisterServiceImpl implements McpMerchantRegisterService {
    @Override
    public McpMerchantRegisterResponse register(McpMerchantRegisterRequest request) {
        // 参数过滤：去除空值字段
        McpMerchantRegisterRequest filtered = filterEmptyFields(request);
        log.debug("[MCP进件] 过滤后参数: {}", filtered);
        // TODO: 调用YOP-MCP接口，组装参数并处理响应
        // 这里只返回一个模拟响应
        McpMerchantRegisterResponse resp = new McpMerchantRegisterResponse();
        resp.setReturnCode("NIG00000");
        resp.setReturnMsg("模拟成功");
        resp.setApplicationStatus("REVIEWING");
        resp.setApplicationNo("APP202406080001");
        resp.setRequestNo(filtered.getRequestNo());
        resp.setMerchantNo("MCP1234567890");
        return resp;
    }

    /**
     * 过滤掉空值字段，返回新对象
     */
    private McpMerchantRegisterRequest filterEmptyFields(McpMerchantRegisterRequest req) {
        try {
            McpMerchantRegisterRequest copy = McpMerchantRegisterRequest.builder().build();
            for (Field field : McpMerchantRegisterRequest.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(req);
                if (value != null && !(value instanceof String && ((String) value).trim().isEmpty())) {
                    field.set(copy, value);
                }
            }
            return copy;
        } catch (Exception e) {
            log.warn("[MCP进件] 参数过滤异常: {}", e.getMessage(), e);
            return req;
        }
    }
} 