<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>易宝付款码支付测试</title>
    <style>
        body { font-family: Arial; }
        .form-group { margin-bottom: 12px; }
        label { display: inline-block; width: 160px; }
        input, select { width: 260px; padding: 4px; }
        .result { margin-top: 24px; border: 1px solid #ccc; padding: 12px; background: #f9f9f9; }
    </style>
</head>
<body>
<h1>易宝付款码支付测试</h1>
<form method="post" action="<c:url value='/pay/test/pay'/>">
    <div class="form-group">
        <label>商户编号(merchantNo):</label>
        <input type="text" name="merchantNo" value="10040054321" required />
    </div>
    <div class="form-group">
        <label>商户收款请求号(orderId):</label>
        <input type="text" name="orderId" value="orderId<%= System.currentTimeMillis() %>" required />
    </div>
    <div class="form-group">
        <label>订单金额(orderAmount):</label>
        <input type="number" step="0.01" min="0.01" name="orderAmount" value="0.01" required />
    </div>
    <div class="form-group">
        <label>支付方式(payWay):</label>
        <select name="payWay" required>
            <option value="MERCHANT_SCAN">MERCHANT_SCAN</option>
        </select>
    </div>
    <div class="form-group">
        <label>渠道类型(channel):</label>
        <select name="channel">
            <option value="WECHAT">WECHAT</option>
            <option value="ALIPAY">ALIPAY</option>
            <option value="UNIONPAY">UNIONPAY</option>
            <option value="DCEP">DCEP</option>
        </select>
    </div>
    <div class="form-group">
        <label>用户授权码(authCode):</label>
        <input type="text" name="authCode" value="" required />
    </div>
    <div class="form-group">
        <label>用户真实IP(userIp):</label>
        <input type="text" name="userIp" value="127.0.0.1" required />
    </div>
    <div class="form-group">
        <label>终端号(terminalId):</label>
        <input type="text" name="terminalId" value="00000000" required />
    </div>
    <div class="form-group">
        <label>商品名称(goodsName):</label>
        <input type="text" name="goodsName" value="测试商品" />
    </div>
    <div class="form-group">
        <label>支付结果通知地址(notifyUrl):</label>
        <input type="text" name="notifyUrl" value="http://127.0.0.1:8080/notify" />
    </div>
    <div class="form-group">
        <button type="submit">提交支付</button>
    </div>
</form>

<c:if test="${not empty payResult}">
    <div class="result">
        <h3>支付响应</h3>
        <pre>${payResult}</pre>
    </div>
</c:if>

</body>
</html> 