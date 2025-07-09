package com.wdc.test.shop.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.repository.PaymentRepository;
import com.wdc.test.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class WechatPayChannel implements PaymentChannel {
    
    private static final Logger logger = LoggerFactory.getLogger(WechatPayChannel.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    @Value("${mcp.api.baseurl:https://api.mcp.com}")
    private String mcpBaseUrl;
    
    @Value("${mcp.api.refund.path:/rest/v1.0/trade/refund}")
    private String mcpRefundPath;
    
    @Value("${mcp.merchant.no:10012426723}")
    private String merchantNo;
    
    @Value("${mcp.parent.merchant.no:10012426723}")
    private String parentMerchantNo;
    
    @Value("${mcp.refund.notify.url:http://example.com/payments/refund-callback/WECHAT}")
    private String refundNotifyUrl;
    
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final RestTemplate restTemplate;
    
    @Autowired
    public WechatPayChannel(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.restTemplate = new RestTemplate();
    }
    
    @Override
    public String getChannelName() {
        return "WECHAT";
    }

    @Override
    public String initiatePayment(Payment payment) {
        logger.info("==== 开始微信支付下单流程 ====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}", 
                payment.getId(), payment.getSystemOrderId(), payment.getOrderId());
        
        try {
            // 模拟微信支付流程
            String transactionId = "WX" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            payment.setTransactionId(transactionId);
            paymentRepository.save(payment);
            
            logger.info("生成微信支付交易ID：{}", transactionId);
            
            // 返回支付链接或二维码
            return "https://wechat.example.com/pay?orderId=" + payment.getOrderId() + 
                    "&amount=" + payment.getAmount() + "&transactionId=" + transactionId;
        } catch (Exception e) {
            logger.error("微信支付下单异常", e);
            return null;
        } finally {
            logger.info("==== 结束微信支付下单流程 ====");
        }
    }

    @Override
    public boolean processCallback(String transactionId, String status) {
        try {
            logger.info("==== 开始处理微信支付回调 ====");
            logger.info("交易ID：{}，状态：{}", transactionId, status);
            
            Payment payment = paymentRepository.findAll().stream()
                    .filter(p -> transactionId.equals(p.getTransactionId()))
                    .findFirst()
                    .orElse(null);
                    
            if (payment != null) {
                logger.info("找到对应支付记录，支付ID：{}，原状态：{}", payment.getId(), payment.getStatus());
                
                payment.setStatus(status);
                paymentRepository.save(payment);
                logger.info("更新支付状态为：{}", status);
                
                // 更新订单状态
                if ("SUCCESS".equals(status)) {
                    logger.info("支付成功，更新订单状态为已支付，系统订单ID：{}", payment.getSystemOrderId());
                    orderService.updateOrderStatus(payment.getSystemOrderId(), "PAID");
                    return true;
                }
                return true;
            }
            
            logger.warn("未找到交易ID对应的支付记录：{}", transactionId);
            return false;
        } catch (Exception e) {
            // 记录异常信息
            logger.error("处理微信支付回调时发生错误: {}", e.getMessage(), e);
            return false;
        } finally {
            logger.info("==== 结束处理微信支付回调 ====");
        }
    }
    
    @Override
    public boolean refund(Payment payment, double refundAmount, String refundReason) {
        logger.info("==== 开始微信支付退款流程 ====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}，退款金额：{}，退款原因：{}", 
                payment.getId(), payment.getSystemOrderId(), payment.getOrderId(), refundAmount, refundReason);
        
        try {
            // 校验支付状态
            if (!"SUCCESS".equals(payment.getStatus())) {
                logger.error("支付状态不是成功状态，无法退款，当前状态：{}", payment.getStatus());
                return false;
            }
            
            // 校验退款金额
            if (refundAmount > payment.getAmount()) {
                logger.error("退款金额（{}）大于支付金额（{}），无法退款", refundAmount, payment.getAmount());
                return false;
            }
            
            // 调用MCP接口发起退款
            Map<String, String> refundRequest = buildRefundRequest(payment, refundAmount, refundReason);
            logger.debug("构建MCP退款请求参数：{}", OBJECT_MAPPER.writeValueAsString(refundRequest));
            
            // 发送退款请求到MCP
            Map<String, Object> response = sendRefundRequest(refundRequest);
            
            if (response != null) {
                // 解析响应
                String code = (String) response.get("code");
                String message = (String) response.get("message");
                String status = (String) response.get("status");
                
                logger.info("微信退款响应：code={}，message={}，status={}", code, message, status);
                
                // 根据MCP返回判断是否成功
                if ("OPR00000".equals(code)) {
                    if ("SUCCESS".equals(status)) {
                        // 退款成功，更新支付状态
                        payment.setStatus("REFUNDED");
                        paymentRepository.save(payment);
                        
                        // 更新订单状态
                        orderService.updateOrderStatus(payment.getSystemOrderId(), "REFUNDED");
                        
                        logger.info("微信退款成功");
                        return true;
                    } else if ("PROCESSING".equals(status)) {
                        // 处理中，等待回调
                        logger.info("微信退款处理中，等待回调通知");
                        return true;
                    } else {
                        logger.error("微信退款失败：{}", message);
                        return false;
                    }
                } else {
                    logger.error("MCP接口返回错误：{}，错误码：{}", message, code);
                    return false;
                }
            } else {
                logger.error("MCP退款接口响应为空");
                return false;
            }
        } catch (Exception e) {
            logger.error("处理微信退款时发生错误", e);
            return false;
        } finally {
            logger.info("==== 结束微信支付退款流程 ====");
        }
    }
    
    /**
     * 构建MCP退款请求参数
     */
    private Map<String, String> buildRefundRequest(Payment payment, double refundAmount, String refundReason) {
        Map<String, String> request = new HashMap<>();
        
        // 设置商户信息
        request.put("parentMerchantNo", parentMerchantNo);
        request.put("merchantNo", merchantNo);
        
        // 使用全局订单ID
        request.put("orderId", payment.getOrderId());
        
        // 生成退款请求号
        String refundRequestId = "WXREF_" + payment.getId() + "_" + System.currentTimeMillis();
        request.put("refundRequestId", refundRequestId);
        request.put("refundAmount", String.format("%.2f", refundAmount));
        
        // 设置退款原因和备注
        request.put("description", refundReason);
        request.put("memo", "退款-" + payment.getSystemOrderId());
        
        // 设置通知URL
        request.put("notifyUrl", refundNotifyUrl);
        
        logger.info("微信退款请求参数：orderId={}, refundRequestId={}, amount={}", 
                payment.getOrderId(), refundRequestId, refundAmount);
        
        return request;
    }
    
    /**
     * 发送退款请求到MCP
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> sendRefundRequest(Map<String, String> refundParams) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            // 构建表单参数
            StringBuilder formParams = new StringBuilder();
            for (Map.Entry<String, String> entry : refundParams.entrySet()) {
                if (formParams.length() > 0) {
                    formParams.append("&");
                }
                formParams.append(entry.getKey()).append("=").append(entry.getValue());
            }
            
            HttpEntity<String> entity = new HttpEntity<>(formParams.toString(), headers);
            String url = mcpBaseUrl + mcpRefundPath;
            
            logger.info("发送MCP退款请求，URL：{}", url);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            
            return response.getBody();
        } catch (Exception e) {
            logger.error("发送MCP退款请求异常", e);
            return null;
        }
    }
}