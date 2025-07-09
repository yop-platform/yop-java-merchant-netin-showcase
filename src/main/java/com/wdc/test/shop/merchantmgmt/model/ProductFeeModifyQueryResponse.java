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
 * title: 产品费率变更进度查询响应<br>
 * description: 产品费率变更进度查询响应参数模型，对应YOP接口返回字段<br>
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductFeeModifyQueryResponse implements Serializable {
    /** 响应编码 */
    private String returnCode;
    /** 响应描述 */
    private String returnMsg;
    /** 产品变更请求号 */
    private String requestNo;
    /** 申请单编号 */
    private String applicationNo;
    /** 商户编号 */
    private String merchantNo;
    /** 申请状态 */
    private String applicationStatus;
    /** 审核意见 */
    private String auditOpinion;
    /** 产品变更进度说明 */
    private String progressDescription;
    /** 电子签约地址 */
    private String elecSignUrl;
    /** 意愿认证地址 */
    private String intentionAuthUrl;
    /** 开户结果(JSON) */
    private String openAccountResult;
    /** 是否成功 */
    public boolean isSuccess() {
        return "NIG00000".equals(returnCode);
    }
} 