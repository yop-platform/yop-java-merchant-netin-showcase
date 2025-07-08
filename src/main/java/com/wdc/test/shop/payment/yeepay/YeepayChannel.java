package com.wdc.test.shop.payment.yeepay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.payment.PaymentChannel;
import com.wdc.test.shop.repository.PaymentRepository;
import com.wdc.test.shop.service.OrderService;
import com.yeepay.yop.sdk.service.common.YopClient;
import com.yeepay.yop.sdk.service.common.YopClientBuilder;
import com.yeepay.yop.sdk.service.common.request.YopRequest;
import com.yeepay.yop.sdk.service.common.response.YopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 易宝支付渠道实现
 */
@Component
public class YeepayChannel implements PaymentChannel {

    private static final Logger logger = LoggerFactory.getLogger(YeepayChannel.class);
    private static final String CHANNEL_NAME = "YEEPAY";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final YeepayConfig yeepayConfig;
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final YeepayNotifyHandler notifyHandler;
    
    private YopClient yopClient;

    @Autowired
    public YeepayChannel(YeepayConfig yeepayConfig, PaymentRepository paymentRepository, 
                         OrderService orderService, YeepayNotifyHandler notifyHandler) {
        this.yeepayConfig = yeepayConfig;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        this.notifyHandler = notifyHandler;
    }

    @PostConstruct
    public void init() {
        try {
            // 初始化YOP客户端，使用默认配置文件方式
            // SDK会自动从classpath下的config/yop_sdk_config_default.json加载配置
            yopClient = YopClientBuilder.builder().build();
            logger.info("易宝支付SDK初始化成功，配置文件路径：config/yop_sdk_config_default.json");
        } catch (Exception e) {
            logger.error("初始化易宝支付SDK失败", e);
        }
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

    @Override
    public String initiatePayment(Payment payment) {
        logger.info("==== 开始易宝支付下单流程 ====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}, 金额：{}", 
            payment.getId(), payment.getSystemOrderId(), payment.getOrderId(), payment.getAmount());
        
        try {
            // 生成交易ID
            String transactionId = "YOP" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            payment.setTransactionId(transactionId);
            paymentRepository.save(payment);
            logger.info("生成易宝支付交易ID：{}", transactionId);
            
            // 构建易宝支付请求
            YeepayPrePayRequest request = buildPrePayRequest(payment);
            logger.debug("易宝支付请求参数：{}", OBJECT_MAPPER.writeValueAsString(request));
            
            // 发起支付请求
            logger.info("发起易宝支付请求，API路径：{}", yeepayConfig.getPrePayPath());
            YeepayPrePayResponse response = requestPrePay(request);
            
            if (response != null) {
                logger.info("易宝支付下单响应：code={}，message={}", response.getCode(), response.getMessage());
                
                if (response.isSuccess()) {
                    // 处理预支付响应，保存唯一订单号
                    processPrePayResponse(payment, response);
                    
                    logger.info("易宝支付下单成功，易宝订单号：{}，预支付标识：{}", response.getUniqueOrderNo(), response.getPrePayTn());
                    // 返回支付链接 (这里简化处理，实际应该根据返回的预支付标识构建支付URL)
//                    String payUrl = "https://yeepay.example.com/pay?prePayTn=" + response.getPrePayTn();
                    String payUrl = response.getPrePayTn();
                    logger.info("生成支付链接：{}", payUrl);
                    return payUrl;
                } else {
                    logger.error("易宝支付下单失败：{}，错误码：{}", response.getMessage(), response.getCode());
                    return null;
                }
            } else {
                logger.error("易宝支付下单响应为空");
                return null;
            }
        } catch (Exception e) {
            logger.error("易宝支付下单异常", e);
            return null;
        } finally {
            logger.info("==== 结束易宝支付下单流程 ====");
        }
    }

    @Override
    public boolean processCallback(String transactionId, String status) {
        logger.info("==== 开始处理易宝支付回调 ====");
        logger.info("交易ID：{}，状态：{}", transactionId, status);
        
        try {
            // 处理支付回调
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
                    logger.info("支付成功，更新订单状态为已支付，订单ID：{}", payment.getSystemOrderId());
                    orderService.updateOrderStatus(payment.getSystemOrderId(), "PAID");
                } else {
                    logger.info("支付结果为：{}，不更新订单状态", status);
                }
                return true;
            } else {
                logger.warn("未找到交易ID对应的支付记录：{}", transactionId);
                return false;
            }
        } catch (Exception e) {
            logger.error("处理易宝支付回调时发生错误", e);
            return false;
        } finally {
            logger.info("==== 结束处理易宝支付回调 ====");
        }
    }
    
    /**
     * 处理易宝支付结果通知
     * 
     * @param request HTTP请求
     * @return 处理结果，成功返回true，失败返回false
     */
    public boolean processNotification(HttpServletRequest request) {
        logger.info("==== 开始处理易宝支付结果通知 ====");
        
        try {
            // 使用通知处理器解析请求
            YeepayPaymentNotification notification = notifyHandler.handleNotify(request);
            
            if (notification == null) {
                logger.error("易宝支付结果通知验签失败或解析失败");
                return false;
            }
            
            logger.info("易宝支付结果通知解析成功：{}", notification);
            
            // 验证商户信息
            if (!yeepayConfig.getStandardMerchantNo().equals(notification.getMerchantNo())) {
                logger.error("商户信息不匹配，通知商户号：{}，配置商户号：{}", 
                        notification.getMerchantNo(), yeepayConfig.getStandardMerchantNo());
                return false;
            }
            
            // 根据订单号查找支付记录
            String orderId = notification.getOrderId();
            Payment payment = paymentRepository.findAll().stream()
                    .filter(p -> orderId.equals(p.getOrderId()))
                    .findFirst()
                    .orElse(null);
            
            if (payment == null) {
                logger.error("未找到对应订单号的支付记录：{}", orderId);
                return false;
            }
            
            logger.info("找到对应支付记录，支付ID：{}，系统订单ID：{}，原状态：{}", 
                    payment.getId(), payment.getSystemOrderId(), payment.getStatus());
            
            // 更新支付记录
            payment.setStatus(notification.getStatus());
            
            // 更新易宝支付唯一订单号
            if (notification.getUniqueOrderNo() != null) {
                payment.setTransactionId(notification.getUniqueOrderNo());
            }
            
            // 更新支付信息
            payment.setRawResponse(OBJECT_MAPPER.writeValueAsString(notification));
            paymentRepository.save(payment);
            
            logger.info("更新支付状态为：{}", notification.getStatus());
            
            // 更新订单状态
            if ("SUCCESS".equals(notification.getStatus())) {
                logger.info("支付成功，更新订单状态为已支付，订单ID：{}", payment.getSystemOrderId());
                orderService.updateOrderStatus(payment.getSystemOrderId(), "PAID");
            } else {
                logger.info("支付结果为：{}，不更新订单状态", notification.getStatus());
            }
            
            return true;
        } catch (Exception e) {
            logger.error("处理易宝支付结果通知时发生错误", e);
            return false;
        } finally {
            logger.info("==== 结束处理易宝支付结果通知 ====");
        }
    }

    @Override
    public boolean refund(Payment payment, double refundAmount, String refundReason) {
        logger.info("==== 开始易宝支付退款流程 ====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}，退款金额：{}，退款原因：{}", 
                payment.getId(), payment.getSystemOrderId(), payment.getOrderId(), refundAmount, refundReason);
        
        try {
            // 只有成功的支付才能退款
            if (!"SUCCESS".equals(payment.getStatus())) {
                logger.error("支付状态不是成功状态，无法退款，当前状态：{}", payment.getStatus());
                return false;
            }
            
            // 退款金额不能大于支付金额
            if (refundAmount > payment.getAmount()) {
                logger.error("退款金额（{}）大于支付金额（{}），无法退款", refundAmount, payment.getAmount());
                return false;
            }
            
            // 构建退款请求
            YeepayRefundRequest refundRequest = buildRefundRequest(payment, refundAmount, refundReason);
            logger.debug("易宝退款请求参数：{}", OBJECT_MAPPER.writeValueAsString(refundRequest));
            
            // 发起退款请求
            logger.info("发起易宝退款请求，API路径：{}", yeepayConfig.getRefundPath());
            YeepayRefundResponse response = requestRefund(refundRequest);
            
            if (response != null) {
                logger.info("易宝退款响应：code={}，message={}，status={}", 
                        response.getCode(), response.getMessage(), response.getStatus());
                
                if (response.isSuccess()) {
                    // 更新支付状态为已退款
                    payment.setStatus("REFUNDED");
                    paymentRepository.save(payment);
                    
                    // 更新订单状态为已退款
                    orderService.updateOrderStatus(payment.getSystemOrderId(), "REFUNDED");
                    
                    logger.info("易宝退款成功，易宝退款单号：{}", response.getUniqueRefundNo());
                    return true;
                } else if (response.isProcessing()) {
                    // 状态为处理中，也视为成功，等待回调通知
                    logger.info("易宝退款处理中，易宝退款单号：{}", response.getUniqueRefundNo());
                    return true;
                } else {
                    logger.error("易宝退款失败：{}，错误码：{}", response.getMessage(), response.getCode());
                    return false;
                }
            } else {
                logger.error("易宝退款响应为空");
                return false;
            }
        } catch (Exception e) {
            logger.error("易宝退款异常", e);
            return false;
        } finally {
            logger.info("==== 结束易宝退款流程 ====");
        }
    }

    /**
     * 构建易宝支付请求参数
     */
    private YeepayPrePayRequest buildPrePayRequest(Payment payment) {
        YeepayPrePayRequest request = new YeepayPrePayRequest();
        
        // 设置商户信息
        request.setParentMerchantNo(yeepayConfig.getStandardMerchantNo());
        request.setMerchantNo(yeepayConfig.getStandardMerchantNo());
        
        // 使用全局唯一的订单ID，不再生成新的ID
        request.setOrderId(payment.getOrderId());
        request.setOrderAmount(String.format("%.2f", payment.getAmount()));
        
        // 设置过期时间（默认24小时后）
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date expireDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        request.setExpiredTime(sdf.format(expireDate));
        
        // 设置通知URL
        request.setNotifyUrl(yeepayConfig.getNotifyUrl());
        
        // 设置商品信息
        request.setGoodsName("订单:" + payment.getSystemOrderId());
        
        // 设置支付方式和渠道
        request.setPayWay("USER_SCAN");
        request.setChannel("ALIPAY"); // 默认支付宝，可根据需要支持更多渠道
        request.setScene("OFFLINE");
        
        // 设置用户IP
        request.setUserIp("127.0.0.1");
        
        logger.info("构建易宝支付请求参数 - 商户订单号：{}，支付金额：{}，过期时间：{}", 
                request.getOrderId(), request.getOrderAmount(), request.getExpiredTime());
        
        return request;
    }

    /**
     * 发送易宝支付请求
     */
    private YeepayPrePayResponse requestPrePay(YeepayPrePayRequest prePayRequest) {
        logger.debug("准备发送易宝支付请求");
        
        try {
            // 构建YOP请求
            YopRequest request = new YopRequest(yeepayConfig.getPrePayPath(), "POST");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getStandardMerchantNo());
            
            // 构建参数Map并过滤空值
            Map<String, String> paramMap = new HashMap<>();
            addParamIfNotBlank(paramMap, "parentMerchantNo", prePayRequest.getParentMerchantNo());
            addParamIfNotBlank(paramMap, "merchantNo", prePayRequest.getMerchantNo());
            addParamIfNotBlank(paramMap, "orderId", prePayRequest.getOrderId());
            addParamIfNotBlank(paramMap, "orderAmount", prePayRequest.getOrderAmount());
            addParamIfNotBlank(paramMap, "expiredTime", prePayRequest.getExpiredTime());
            addParamIfNotBlank(paramMap, "notifyUrl", prePayRequest.getNotifyUrl());
            addParamIfNotBlank(paramMap, "goodsName", prePayRequest.getGoodsName());
            addParamIfNotBlank(paramMap, "payWay", prePayRequest.getPayWay());
            addParamIfNotBlank(paramMap, "channel", prePayRequest.getChannel());
            addParamIfNotBlank(paramMap, "scene", prePayRequest.getScene());
            addParamIfNotBlank(paramMap, "userIp", prePayRequest.getUserIp());
            
            // 验证必填参数
            if (!validateRequiredParams(paramMap)) {
                logger.error("易宝支付请求缺少必填参数");
                return null;
            }
            
            // 添加有效参数到请求
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                request.addParameter(entry.getKey(), entry.getValue());
            }
            
            logger.debug("发送易宝支付请求，商户订单号：{}，有效参数个数：{}", prePayRequest.getOrderId(), paramMap.size());
            
            // 发送请求
            YopResponse response = yopClient.request(request);
            
            // 解析响应
            if (response != null) {
                if (response.getStringResult() != null) {
                    logger.debug("易宝支付响应原始数据：{}", response.getStringResult());
                    return OBJECT_MAPPER.readValue(response.getStringResult(), YeepayPrePayResponse.class);
                } else {
                    logger.warn("易宝支付响应结果为空");
                }
            } else {
                logger.warn("易宝支付响应对象为空");
            }
        } catch (Exception e) {
            logger.error("易宝支付请求异常", e);
        }
        return null;
    }
    
    /**
     * 构建易宝退款请求参数
     */
    private YeepayRefundRequest buildRefundRequest(Payment payment, double refundAmount, String refundReason) {
        YeepayRefundRequest request = new YeepayRefundRequest();
        
        // 设置商户信息
        request.setParentMerchantNo(yeepayConfig.getStandardMerchantNo());
        request.setMerchantNo(yeepayConfig.getStandardMerchantNo());
        
        // 使用全局唯一的订单ID
        request.setOrderId(payment.getOrderId());
        
        // 生成退款请求号
        String refundRequestId = payment.getRefundRequestId();
        request.setRefundRequestId(refundRequestId);
        
        // 设置退款金额
        request.setRefundAmount(String.format("%.2f", refundAmount));
        
        // 设置退款原因
        request.setDescription(refundReason);
        
        // 设置备注
        request.setMemo("退款-" + payment.getSystemOrderId());
        
        // 设置退款结果通知URL
        request.setNotifyUrl(yeepayConfig.getRefundNotifyUrl());
        
        logger.info("构建易宝退款请求参数 - 商户订单号：{}，退款请求号：{}，退款金额：{}", 
                request.getOrderId(), refundRequestId, request.getRefundAmount());
        
        return request;
    }
    
    /**
     * 发送易宝退款请求
     */
    private YeepayRefundResponse requestRefund(YeepayRefundRequest refundRequest) {
        logger.debug("准备发送易宝退款请求");
        
        try {
            // 构建YOP请求
            YopRequest request = new YopRequest(yeepayConfig.getRefundPath(), "POST");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getStandardMerchantNo());
            
            // 构建参数Map并过滤空值
            Map<String, String> paramMap = new HashMap<>();
            addParamIfNotBlank(paramMap, "parentMerchantNo", refundRequest.getParentMerchantNo());
            addParamIfNotBlank(paramMap, "merchantNo", refundRequest.getMerchantNo());
            addParamIfNotBlank(paramMap, "orderId", refundRequest.getOrderId());
            addParamIfNotBlank(paramMap, "refundRequestId", refundRequest.getRefundRequestId());
            addParamIfNotBlank(paramMap, "refundAmount", refundRequest.getRefundAmount());
            addParamIfNotBlank(paramMap, "description", refundRequest.getDescription());
            addParamIfNotBlank(paramMap, "memo", refundRequest.getMemo());
            addParamIfNotBlank(paramMap, "refundAccountType", refundRequest.getRefundAccountType());
            addParamIfNotBlank(paramMap, "notifyUrl", refundRequest.getNotifyUrl());
            
            // 验证必填参数
            if (!validateRefundRequiredParams(paramMap)) {
                logger.error("易宝退款请求缺少必填参数");
                return null;
            }
            
            // 添加有效参数到请求
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                request.addParameter(entry.getKey(), entry.getValue());
            }
            
            logger.debug("发送易宝退款请求，退款请求号：{}，有效参数个数：{}", 
                    refundRequest.getRefundRequestId(), paramMap.size());
            
            // 发送请求
            YopResponse response = yopClient.request(request);
            
            // 解析响应
            if (response != null) {
                if (response.getStringResult() != null) {
                    logger.debug("易宝退款响应原始数据：{}", response.getStringResult());
                    return OBJECT_MAPPER.readValue(response.getStringResult(), YeepayRefundResponse.class);
                } else {
                    logger.warn("易宝退款响应结果为空");
                }
            } else {
                logger.warn("易宝退款响应对象为空");
            }
        } catch (Exception e) {
            logger.error("易宝退款请求异常", e);
        }
        return null;
    }
    
    /**
     * 添加非空参数到参数Map
     * 
     * @param paramMap 参数Map
     * @param key 参数名
     * @param value 参数值
     */
    private void addParamIfNotBlank(Map<String, String> paramMap, String key, String value) {
        if (value != null && !value.trim().isEmpty()) {
            paramMap.put(key, value);
        } else {
            logger.debug("参数 {} 为空，已过滤", key);
        }
    }
    
    /**
     * 验证支付必填参数
     * 
     * @param paramMap 参数Map
     * @return 验证结果
     */
    private boolean validateRequiredParams(Map<String, String> paramMap) {
        // 必填参数列表
        String[] requiredParams = {"merchantNo", "orderId", "orderAmount", "payWay", "channel", "userIp"};
        
        for (String param : requiredParams) {
            if (!paramMap.containsKey(param)) {
                logger.error("缺少必填参数: {}", param);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * 验证退款必填参数
     * 
     * @param paramMap 参数Map
     * @return 验证结果
     */
    private boolean validateRefundRequiredParams(Map<String, String> paramMap) {
        // 如果有uniqueOrderNo，则不需要orderId
        boolean hasUniqueOrderNo = paramMap.containsKey("uniqueOrderNo") && 
                                  paramMap.get("uniqueOrderNo") != null && 
                                  !paramMap.get("uniqueOrderNo").trim().isEmpty();
        
        // 基础必填参数列表
        String[] baseRequiredParams = {"merchantNo", "refundRequestId", "refundAmount"};
        
        // 验证基础参数
        for (String param : baseRequiredParams) {
            if (!paramMap.containsKey(param)) {
                logger.error("缺少必填参数: {}", param);
                return false;
            }
        }
        
        // 检查orderId与uniqueOrderNo至少有一个
        if (!hasUniqueOrderNo && (!paramMap.containsKey("orderId") || 
            paramMap.get("orderId") == null || 
            paramMap.get("orderId").trim().isEmpty())) {
            logger.error("缺少必填参数: orderId 或 uniqueOrderNo");
            return false;
        }
        
        return true;
    }

    // Replace the processPrePayResponse method (add it if it doesn't exist)
    private void processPrePayResponse(Payment payment, YeepayPrePayResponse response) {
        if (response != null && response.isSuccess()) {
            // 保存易宝返回的唯一订单号，用于后续退款
            if (response.getUniqueOrderNo() != null && !response.getUniqueOrderNo().isEmpty()) {
                // 如果易宝返回了唯一订单号，优先使用它
                payment.setPlatformOrderId(response.getUniqueOrderNo());
                paymentRepository.save(payment);
                logger.info("更新支付记录的平台订单号为易宝唯一订单号：{}", response.getUniqueOrderNo());
            }
        }
    }

    /**
     * 查询订单
     * @param parentMerchantNo 发起方商编（可选）
     * @param merchantNo 商户编号（必填）
     * @param orderId 商户收款请求号（必填）
     * @return 订单查询响应
     */
    public YeepayOrderQueryResponse queryOrder(String parentMerchantNo, String merchantNo, String orderId) {
        logger.info("==== 开始易宝订单查询 ====");
        try {
            Map<String, String> paramMap = new HashMap<>();
            addParamIfNotBlank(paramMap, "parentMerchantNo", parentMerchantNo);
            addParamIfNotBlank(paramMap, "merchantNo", merchantNo);
            addParamIfNotBlank(paramMap, "orderId", orderId);

            // 校验必填参数
            if (!paramMap.containsKey("merchantNo") || !paramMap.containsKey("orderId")) {
                logger.error("订单查询缺少必填参数: merchantNo/orderId");
                return null;
            }

            YopRequest request = new YopRequest(yeepayConfig.getOrderQueryPath(), "GET");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getStandardMerchantNo());
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                request.addParameter(entry.getKey(), entry.getValue());
            }

            logger.info("发送易宝订单查询请求，API路径：{}，参数：{}", yeepayConfig.getOrderQueryPath(), paramMap);
            YopResponse response = yopClient.request(request);
            if (response != null && response.getStringResult() != null) {
                logger.debug("易宝订单查询响应原始数据：{}", response.getStringResult());
                YeepayOrderQueryResponse queryResponse = OBJECT_MAPPER.readValue(response.getStringResult(), YeepayOrderQueryResponse.class);
                logger.info("订单查询响应：code={}，message={}", queryResponse.getCode(), queryResponse.getMessage());
                return queryResponse;
            } else {
                logger.warn("易宝订单查询响应为空");
                return null;
            }
        } catch (Exception e) {
            logger.error("易宝订单查询异常", e);
            return null;
        } finally {
            logger.info("==== 结束易宝订单查询 ====");
        }
    }

    /**
     * 查询订单（重载，支持直接传递请求对象）
     * @param req 订单查询请求参数
     * @return 订单查询响应
     */
    public YeepayOrderQueryResponse queryOrder(YeepayOrderQueryRequest req) {
        if (req == null) {
            logger.error("订单查询请求参数对象为null");
            return null;
        }
        return queryOrder(req.getParentMerchantNo(), req.getMerchantNo(), req.getOrderId());
    }

    /**
     * 查询退款状态
     * @param req 退款查询请求参数
     * @return 退款查询响应
     */
    public YeepayRefundQueryResponse queryRefund(YeepayRefundQueryRequest req) {
        logger.info("==== 开始易宝退款查询 ====");
        try {
            Map<String, String> paramMap = new HashMap<>();
            addParamIfNotBlank(paramMap, "parentMerchantNo", req.getParentMerchantNo());
            addParamIfNotBlank(paramMap, "merchantNo", req.getMerchantNo());
            addParamIfNotBlank(paramMap, "orderId", req.getOrderId());
            addParamIfNotBlank(paramMap, "refundRequestId", req.getRefundRequestId());
            addParamIfNotBlank(paramMap, "uniqueRefundNo", req.getUniqueRefundNo());
            // 校验必填参数
            if (!paramMap.containsKey("merchantNo") || !paramMap.containsKey("orderId") || !paramMap.containsKey("refundRequestId")) {
                logger.error("退款查询缺少必填参数: merchantNo/orderId/refundRequestId");
                return null;
            }
            YopRequest request = new YopRequest("/rest/v1.0/trade/refund/query", "GET");
            request.getRequestConfig().setAppKey(yeepayConfig.getAppKeyPrefix() + yeepayConfig.getStandardMerchantNo());
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                request.addParameter(entry.getKey(), entry.getValue());
            }
            logger.info("发送易宝退款查询请求，API路径：{}，参数：{}", "/rest/v1.0/trade/refund/query", paramMap);
            YopResponse response = yopClient.request(request);
            if (response != null && response.getStringResult() != null) {
                logger.debug("易宝退款查询响应原始数据：{}", response.getStringResult());
                YeepayRefundQueryResponse queryResponse = OBJECT_MAPPER.readValue(response.getStringResult(), YeepayRefundQueryResponse.class);
                logger.info("退款查询响应：code={}，message={}", queryResponse.getCode(), queryResponse.getMessage());
                return queryResponse;
            } else {
                logger.warn("易宝退款查询响应为空");
                return null;
            }
        } catch (Exception e) {
            logger.error("易宝退款查询异常", e);
            return null;
        } finally {
            logger.info("==== 结束易宝退款查询 ====");
        }
    }

    /**
     * 微信实名认证申请单申请
     * @param req 请求参数
     * @return 响应参数
     */
    public WechatAuthApplyResponse wechatAuthApply(WechatAuthApplyRequest req) {
        logger.info("==== 发起微信实名认证申请单 ====");
        try {
            Map<String, Object> paramMap = new HashMap<>();
            // 只添加非空参数
            if (req.getRequestNo() != null) paramMap.put("requestNo", req.getRequestNo());
            if (req.getSubMerchantNo() != null) paramMap.put("subMerchantNo", req.getSubMerchantNo());
            if (req.getApplicantType() != null) paramMap.put("applicantType", req.getApplicantType());
            if (req.getApplicantName() != null) paramMap.put("applicantName", req.getApplicantName());
            if (req.getApplicantPhone() != null) paramMap.put("applicantPhone", req.getApplicantPhone());
            if (req.getApplicantIdCard() != null) paramMap.put("applicantIdCard", req.getApplicantIdCard());
            if (req.getTransactorInfo() != null) paramMap.put("transactorInfo", req.getTransactorInfo());
            if (req.getIdentificationType() != null) paramMap.put("identificationType", req.getIdentificationType());
            if (req.getIdentificationFrontCopy() != null) paramMap.put("identificationFrontCopy", req.getIdentificationFrontCopy());
            if (req.getIdentificationBackCopy() != null) paramMap.put("identificationBackCopy", req.getIdentificationBackCopy());
            if (req.getIdentificationValidDate() != null) paramMap.put("identificationValidDate", req.getIdentificationValidDate());
            if (req.getIdentificationAddress() != null) paramMap.put("identificationAddress", req.getIdentificationAddress());
            if (req.getCertCopy() != null) paramMap.put("certCopy", req.getCertCopy());
            if (req.getCompanyAddress() != null) paramMap.put("companyAddress", req.getCompanyAddress());
            if (req.getLicenceValidDate() != null) paramMap.put("licenceValidDate", req.getLicenceValidDate());
            if (req.getIsFinanceInstitution() != null) paramMap.put("isFinanceInstitution", req.getIsFinanceInstitution());
            if (req.getFinanceInstitutionInfo() != null) paramMap.put("financeInstitutionInfo", req.getFinanceInstitutionInfo());
            if (req.getCertType() != null) paramMap.put("certType", req.getCertType());
            if (req.getCertNumber() != null) paramMap.put("certNumber", req.getCertNumber());
            if (req.getCompanyProveCopy() != null) paramMap.put("companyProveCopy", req.getCompanyProveCopy());
            if (req.getOwner() != null) paramMap.put("owner", req.getOwner());
            if (req.getUboInfoList() != null) paramMap.put("uboInfoList", req.getUboInfoList());
            if (req.getReportFee() != null) paramMap.put("reportFee", req.getReportFee());
            if (req.getChannelId() != null) paramMap.put("channelId", req.getChannelId());
            if (req.getMicroBizType() != null) paramMap.put("microBizType", req.getMicroBizType());
            if (req.getStoreName() != null) paramMap.put("storeName", req.getStoreName());
            if (req.getStoreAddressCode() != null) paramMap.put("storeAddressCode", req.getStoreAddressCode());
            if (req.getStoreHeaderCopy() != null) paramMap.put("storeHeaderCopy", req.getStoreHeaderCopy());
            if (req.getStoreIndoorCopy() != null) paramMap.put("storeIndoorCopy", req.getStoreIndoorCopy());
            // 必填参数校验
            if (!paramMap.containsKey("requestNo") || !paramMap.containsKey("subMerchantNo") || !paramMap.containsKey("applicantType") || !paramMap.containsKey("applicantPhone") || !paramMap.containsKey("identificationValidDate") || !paramMap.containsKey("companyAddress")) {
                logger.error("微信实名认证申请单缺少必填参数");
                throw new IllegalArgumentException("缺少必填参数");
            }
            logger.info("微信实名认证申请单参数: {}", paramMap);
            YopRequest yopRequest = new YopRequest("/rest/v1.0/mer/merchant/wechatauth/apply", "POST");
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                yopRequest.addParameter(entry.getKey(), entry.getValue());
            }
            YopResponse yopResponse = yopClient.request(yopRequest);
            logger.info("微信实名认证申请单响应: {}", yopResponse.getStringResult());
            return OBJECT_MAPPER.readValue(yopResponse.getStringResult(), WechatAuthApplyResponse.class);
        } catch (Exception e) {
            logger.error("微信实名认证申请单异常", e);
            return null;
        }
    }
} 