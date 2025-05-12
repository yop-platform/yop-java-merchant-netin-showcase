<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单详情</title>
</head>
<body>
    <h1>订单详情</h1>
    
    <p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/orders'/>">我的订单</a> | <a href="<c:url value='/products'/>">商品列表</a> | <a href="<c:url value='/logout'/>">退出</a></p>
    
    <div>
        <h2>订单信息</h2>
        <p>订单号: ${order.id}</p>
        <p>商品: ${product.name}</p>
        <p>数量: ${order.quantity}</p>
        <p>总金额: ¥${order.totalAmount}</p>
        <p>状态: ${order.status}</p>
        <p>创建时间: <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
    </div>
    
    <c:if test="${order.status eq 'PENDING' && empty payment}">
        <div>
            <h2>选择支付方式</h2>
            <form action="<c:url value='/payments/create'/>" method="post">
                <input type="hidden" name="orderId" value="${order.id}">
                <label>
                    <input type="radio" name="paymentMethod" value="ALIPAY" checked> 支付宝
                </label>
                <label>
                    <input type="radio" name="paymentMethod" value="WECHAT"> 微信支付
                </label>
                <button type="submit">去支付</button>
            </form>
        </div>
    </c:if>
    
    <c:if test="${not empty payment}">
        <div>
            <h2>支付信息</h2>
            <p>支付ID: ${payment.id}</p>
            <p>支付方式: ${payment.paymentMethod}</p>
            <p>支付金额: ¥${payment.amount}</p>
            <p>支付状态: ${payment.status}</p>
            <p>交易号: ${payment.transactionId}</p>
            
            <c:if test="${payment.status eq 'PENDING'}">
                <p><a href="<c:url value='/payments/${payment.id}/pay'/>">继续支付</a></p>
            </c:if>
        </div>
    </c:if>
</body>
</html>