<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>支付</title>
    <script src="https://cdn.jsdelivr.net/npm/qrcodejs@1.0.0/qrcode.min.js"></script>
</head>
<body>
<h1>支付</h1>

<p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/orders'/>">我的订单</a> | <a href="<c:url value='/products'/>">商品列表</a> | <a href="<c:url value='/logout'/>">退出</a></p>

<div>
    <h2>订单信息</h2>
    <p>订单号: ${order.id}</p>
    <p>支付金额: ¥${payment.amount}</p>
    <p>支付方式: ${payment.paymentMethod}</p>
</div>

<div>
    <h2>支付链接</h2>
    <p>请点击以下链接完成支付：</p>
    <a href="${paymentUrl}" target="_blank">${paymentUrl}</a>

    <p>或者扫描以下二维码：</p>
    <div style="border: 1px solid #ddd; padding: 10px; width: 220px; height: 220px; text-align: center;">
        <div id="qrcode"></div>
    </div>

    <script type="text/javascript">
        window.onload = function() {
            new QRCode(document.getElementById("qrcode"), {
                text: "${paymentUrl}",
                width: 200,
                height: 200,
                colorDark: "#000000",
                colorLight: "#ffffff",
                correctLevel: QRCode.CorrectLevel.H
            });
        }
    </script>

    <p>支付完成后，请点击：</p>
    <!-- 模拟支付成功回调 -->
    <form action="<c:url value='/payments/callback/${payment.paymentMethod}'/>" method="post">
        <input type="hidden" name="transactionId" value="${payment.transactionId}">
        <input type="hidden" name="status" value="SUCCESS">
        <button type="submit">模拟支付成功</button>
    </form>

    <!-- 模拟支付失败回调 -->
    <form action="<c:url value='/payments/callback/${payment.paymentMethod}'/>" method="post" style="margin-top: 10px;">
        <input type="hidden" name="transactionId" value="${payment.transactionId}">
        <input type="hidden" name="status" value="FAILED">
        <button type="submit">模拟支付失败</button>
    </form>
</div>

<p><a href="<c:url value='/orders/${order.id}'/>">返回订单详情</a></p>
</body>
</html>