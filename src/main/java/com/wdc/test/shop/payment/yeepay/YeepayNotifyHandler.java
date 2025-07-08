package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yeepay.yop.sdk.auth.credentials.provider.YopCredentialsProviderRegistry;
import com.yeepay.yop.sdk.http.YopContentType;
import com.yeepay.yop.sdk.service.common.callback.YopCallback;
import com.yeepay.yop.sdk.service.common.YopCallbackEngine;
import com.yeepay.yop.sdk.service.common.callback.YopCallbackRequest;
import com.yeepay.yop.sdk.service.common.callback.YopCallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 易宝支付通知处理器
 * 基于易宝开放平台文档实现：https://open.yeepay.com/docs-v3/platform/207.md
 */
@Component
public class YeepayNotifyHandler {
    private static final Logger logger = LoggerFactory.getLogger(YeepayNotifyHandler.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 处理支付结果通知
     *
     * @param request HTTP请求
     * @return 通知解析结果，如果验签失败则返回null
     */
    public YeepayPaymentNotification handleNotify(HttpServletRequest request) {
        try {
            logger.info("接收到易宝支付结果通知");
            
            // 解析原始通知报文
            String response = request.getParameter("response");
            String customerIdentification = request.getParameter("customerIdentification");
            
            if (response == null || customerIdentification == null) {
                logger.error("易宝支付通知参数不完整，缺少response或customerIdentification");
                return null;
            }
            
            logger.debug("通知参数: response={}, customerIdentification={}", 
                    response.length() > 50 ? response.substring(0, 50) + "..." : response, 
                    customerIdentification);
            
            // 根据源码示例构建YopCallbackRequest
            YopCallbackRequest callbackRequest = new YopCallbackRequest("/rest/v1.0/aggpay/callback", "POST")
                    .setProvider("yeepay")
                    .setEnv("sandbox")
                    .setContentType(YopContentType.FORM_URL_ENCODE)
                    .addParam("customerIdentification", customerIdentification)
                    .addParam("response", response);
            
            // 使用YopCallbackEngine解析回调请求
            YopCallback callback = YopCallbackEngine.parse(callbackRequest);
            
            if (callback == null) {
                logger.error("易宝支付通知解析失败");
                return null;
            }
            
            // 验签结果
            if (!callback.isValidSign()) {
                logger.error("易宝支付通知验签失败");
                return null;
            }
            
            logger.info("易宝支付通知验签成功，开始处理通知内容");
            
            // 获取响应报文，并转换为支付通知对象
            String responseContent = callback.getBizData();
            logger.debug("验签后的通知内容：{}", responseContent);
            
            YeepayPaymentNotification notification = OBJECT_MAPPER.readValue(responseContent, YeepayPaymentNotification.class);
            logger.info("解析通知内容成功: orderId={}, status={}, uniqueOrderNo={}", 
                    notification.getOrderId(), notification.getStatus(), notification.getUniqueOrderNo());
            
            return notification;
        } catch (Exception e) {
            logger.error("处理易宝支付通知异常", e);
            return null;
        }
    }
    
    /**
     * 处理退款结果通知
     *
     * @param request HTTP请求
     * @return 退款通知解析结果，如果验签失败则返回null
     */
    public YeepayRefundNotification handleRefundNotify(HttpServletRequest request) {
        try {
            logger.info("接收到易宝退款结果通知");
            String response = request.getParameter("response");
            String customerIdentification = request.getParameter("customerIdentification");
            if (response == null || customerIdentification == null) {
                logger.error("易宝退款通知参数不完整，缺少response或customerIdentification");
                return null;
            }
            logger.debug("退款通知参数: response={}, customerIdentification={}",
                    response.length() > 50 ? response.substring(0, 50) + "..." : response,
                    customerIdentification);
            // 构建YopCallbackRequest，路径可为退款API路径
            YopCallbackRequest callbackRequest = new YopCallbackRequest("/rest/v1.0/trade/refund/notify", "POST")
                    .setProvider("yeepay")
                    .setEnv("sandbox")
                    .setContentType(YopContentType.FORM_URL_ENCODE)
                    .addParam("customerIdentification", customerIdentification)
                    .addParam("response", response);
            YopCallback callback = YopCallbackEngine.parse(callbackRequest);
            if (callback == null) {
                logger.error("易宝退款通知解析失败");
                return null;
            }
            if (!callback.isValidSign()) {
                logger.error("易宝退款通知验签失败");
                return null;
            }
            logger.info("易宝退款通知验签成功，开始处理通知内容");
            String responseContent = callback.getBizData();
            logger.debug("退款验签后的通知内容：{}", responseContent);
            YeepayRefundNotification notification = OBJECT_MAPPER.readValue(responseContent, YeepayRefundNotification.class);
            logger.info("解析退款通知内容成功: refundRequestId={}, status={}, uniqueRefundNo={}",
                    notification.getRefundRequestId(), notification.getStatus(), notification.getUniqueRefundNo());
            return notification;
        } catch (Exception e) {
            logger.error("处理易宝退款通知异常", e);
            return null;
        }
    }
    
    /**
     * 处理清算结果通知
     * @param request HTTP请求
     * @return 清算通知解析结果，如果验签失败则返回null
     */
    public YeepaySettleNotification handleSettleNotify(HttpServletRequest request) {
        try {
            logger.info("接收到易宝清算结果通知");
            String response = request.getParameter("response");
            String customerIdentification = request.getParameter("customerIdentification");
            if (response == null || customerIdentification == null) {
                logger.error("易宝清算通知参数不完整，缺少response或customerIdentification");
                return null;
            }
            logger.debug("清算通知参数: response={}, customerIdentification={}",
                    response.length() > 50 ? response.substring(0, 50) + "..." : response,
                    customerIdentification);
            YopCallbackRequest callbackRequest = new YopCallbackRequest("/rest/v1.0/settle/notify", "POST")
                    .setProvider("yeepay")
                    .setEnv("sandbox")
                    .setContentType(YopContentType.FORM_URL_ENCODE)
                    .addParam("customerIdentification", customerIdentification)
                    .addParam("response", response);
            YopCallback callback = YopCallbackEngine.parse(callbackRequest);
            if (callback == null) {
                logger.error("易宝清算通知解析失败");
                return null;
            }
            if (!callback.isValidSign()) {
                logger.error("易宝清算通知验签失败");
                return null;
            }
            logger.info("易宝清算通知验签成功，开始处理通知内容");
            String responseContent = callback.getBizData();
            logger.debug("清算验签后的通知内容：{}", responseContent);
            YeepaySettleNotification notification = OBJECT_MAPPER.readValue(responseContent, YeepaySettleNotification.class);
            logger.info("解析清算通知内容成功: orderId={}, status={}, uniqueOrderNo={}",
                    notification.getOrderId(), notification.getStatus(), notification.getUniqueOrderNo());
            return notification;
        } catch (Exception e) {
            logger.error("处理易宝清算通知异常", e);
            return null;
        }
    }
    
    /**
     * 构建响应内容，告知易宝支付服务器通知处理结果
     * 
     * @param success 处理是否成功
     * @return 响应内容
     */
    public String buildResponse(boolean success) {
        // 根据开放平台文档，成功时返回"SUCCESS"，失败时可返回自定义错误信息
        return success ? "SUCCESS" : "FAILURE";
    }
    
    /**
     * 从请求中获取所有参数，用于调试
     * 
     * @param request HTTP请求
     * @return 参数Map
     */
    private Map<String, String> getAllRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        
        try {
            Enumeration<String> paramNames = request.getParameterNames();
            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();
                String paramValue = request.getParameter(paramName);
                params.put(paramName, paramValue);
                
                // 日志中不打印完整的response内容，避免日志过大
                if ("response".equals(paramName) && paramValue != null && paramValue.length() > 100) {
                    logger.debug("请求参数: {}={}", paramName, paramValue.substring(0, 100) + "...(省略)");
                } else {
                    logger.debug("请求参数: {}={}", paramName, paramValue);
                }
            }
        } catch (Exception e) {
            logger.warn("获取请求参数异常", e);
        }
        
        return params;
    }
} 