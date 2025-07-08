package com.wdc.test.shop.model;

import java.util.Date;

public class Payment {
    private Long id;
    private Long systemOrderId; // 系统内部订单ID
    private String orderId;     // 支付平台使用的订单号，全局唯一标识
    private String paymentMethod; // ALIPAY, WECHAT, etc.
    private double amount;
    private String status; // PENDING, SUCCESS, FAILED
    private String transactionId;
    private String platformOrderId; // 支付平台的订单号
    private Date createdAt;
    private Date updatedAt;
    private String yeepayOrderNo; // 易宝支付唯一订单号
    private String rawResponse; // 原始响应数据，JSON格式
    private String refundRequestId; // 商户退款请求号

    // Constructors
    public Payment() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Payment(Long id, Long systemOrderId, String orderId, String paymentMethod, double amount, String status) {
        this.id = id;
        this.systemOrderId = systemOrderId;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.status = status;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getSystemOrderId() { return systemOrderId; }
    public void setSystemOrderId(Long systemOrderId) { this.systemOrderId = systemOrderId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status;
        this.updatedAt = new Date();
    }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getPlatformOrderId() { return platformOrderId; }
    public void setPlatformOrderId(String platformOrderId) { this.platformOrderId = platformOrderId; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    
    public String getYeepayOrderNo() { return yeepayOrderNo; }
    public void setYeepayOrderNo(String yeepayOrderNo) { this.yeepayOrderNo = yeepayOrderNo; }
    
    public String getRawResponse() { return rawResponse; }
    public void setRawResponse(String rawResponse) { this.rawResponse = rawResponse; }
    
    public String getRefundRequestId() { return refundRequestId; }
    public void setRefundRequestId(String refundRequestId) { this.refundRequestId = refundRequestId; }
}