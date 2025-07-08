/*
* Copyright: Copyright (c)2014
* Company: 易宝支付(YeePay)
*/
package com.wdc.test.shop.model.mcp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

/**
 * title: MCP商户进件请求模型<br>
 * description: 对应MCP /rest/v2.0/mer/register/saas/micro接口请求结构，所有字段与官方文档一致，嵌套结构需后续补充。<br>
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
public class McpMerchantRegisterRequest {
    /**
     * 商户主体信息，JSON对象，需补充详细字段
     */
    private Object merchantSubjectInfo;
    /**
     * 法人信息，JSON对象，需补充详细字段
     */
    private Object corporationInfo;
    /**
     * 行业类别信息，JSON对象，需补充详细字段
     */
    private Object industryCategoryInfo;
    /**
     * 经营地址信息，JSON对象，需补充详细字段
     */
    private Object businessAddressInfo;
    /**
     * 账户信息，JSON对象，需补充详细字段
     */
    private Object accountInfo;
    /**
     * 产品信息，JSON对象，需补充详细字段
     */
    private Object productInfo;
    /**
     * 产品资质信息，JSON对象，需补充详细字段
     */
    private Object productQualificationInfo;
    /**
     * 功能服务信息，JSON对象，需补充详细字段
     */
    private Object functionServiceInfo;
    /**
     * 功能服务资质信息，JSON对象，需补充详细字段
     */
    private Object functionServiceQualificationInfo;
    /**
     * 业务场景信息，JSON对象，需补充详细字段
     */
    private Object businessSceneInfo;
    /**
     * 商户补充信息，JSON对象，需补充详细字段
     */
    private Object merchantExtraInfo;
    /**
     * 业务配置信息，JSON对象，需补充详细字段
     */
    private Object businessConfig;
    /**
     * 银行开户信息，JSON对象，需补充详细字段
     */
    private Object bankOpenAccountInfo;
    /**
     * 请求流水号
     */
    private String requestNo;
    /**
     * 代理商编号
     */
    private String agentMerchantNo;
    /**
     * 外部商户号
     */
    private String outMerchantNo;
    /**
     * 进件渠道
     */
    private String registerChannel;
    /**
     * 进件来源
     */
    private String registerSource;
    /**
     * 进件类型
     */
    private String registerType;
    /**
     * 进件备注
     */
    private String remark;
    // 其他参数可根据MCP文档补充
} 