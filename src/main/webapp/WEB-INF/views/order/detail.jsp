<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单详情</title>
    <meta charset="UTF-8">
    <style>
        .message {
            color: green;
            margin: 10px 0;
        }
        .error {
            color: red;
            margin: 10px 0;
        }
        .button {
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
        }
        .button-warning {
            background-color: #f44336;
        }
        form {
            margin-top: 15px;
        }
    </style>
</head>
<body>
    <h1>订单详情</h1>
    
    <p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/orders'/>">我的订单</a> | <a href="<c:url value='/products'/>">商品列表</a> | <a href="<c:url value='/logout'/>">退出</a></p>
    
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    
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
            <form action="<c:url value='/payments/create'/>" method="post" accept-charset="UTF-8">
                <input type="hidden" name="orderId" value="${order.id}">
                <label>
                    <input type="radio" name="paymentMethod" value="ALIPAY" checked> 支付宝
                </label>
                <label>
                    <input type="radio" name="paymentMethod" value="WECHAT"> 微信支付
                </label>
                <label>
                    <input type="radio" name="paymentMethod" value="YEEPAY"> 易宝支付
                </label>
                <button type="submit" class="button">去支付</button>
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
            <p>平台订单号: ${payment.orderId}</p>
            <p>退款状态: 
                <c:choose>
                    <c:when test="${payment.status eq 'REFUNDED'}">已退款</c:when>
                    <c:when test="${payment.status eq 'REFUND_FAILED'}">退款失败</c:when>
                    <c:otherwise>-</c:otherwise>
                </c:choose>
            </p>
            <button type="button" onclick="queryRefund('${order.id}')">查退款</button>
            
            <c:if test="${payment.status eq 'PENDING'}">
                <p><a href="<c:url value='/payments/${payment.id}/pay'/>" class="button">继续支付</a></p>
            </c:if>
            
            <c:if test="${payment.status eq 'SUCCESS' && (order.status eq 'PAID')}">
                <h3>申请退款</h3>
                <form action="<c:url value='/payments/refund'/>" method="post" accept-charset="UTF-8">
                    <input type="hidden" name="paymentId" value="${payment.id}">
                    <div>
                        <label for="refundAmount">退款金额:</label>
                        <input type="number" id="refundAmount" name="refundAmount" step="0.01" min="0.01" max="${payment.amount}" value="${payment.amount}">
                        <small>(最大可退款金额: ¥${payment.amount})</small>
                    </div>
                    <div>
                        <label for="refundReason">退款原因:</label>
                        <select id="refundReason" name="refundReason">
                            <option value="用户申请退款">用户申请退款</option>
                            <option value="商品质量问题">商品质量问题</option>
                            <option value="商品与描述不符">商品与描述不符</option>
                            <option value="不想要了">不想要了</option>
                            <option value="其他原因">其他原因</option>
                        </select>
                    </div>
                    <button type="submit" class="button button-warning">申请退款</button>
                </form>
            </c:if>
        </div>
    </c:if>
    
    <!-- 查退款结果弹窗 -->
    <div id="refundQueryModal" style="display:none; position:fixed; left:50%; top:35%; transform:translate(-50%,0); background:#fff; border:1px solid #888; padding:20px; z-index:9999; min-width:400px;">
        <div id="refundQueryContent"></div>
        <button onclick="closeRefundQueryModal()">关闭</button>
    </div>
    <script>
        function queryRefund(orderId) {
            fetch('/orders/queryRefund?orderId=' + orderId)
                .then(resp => resp.json())
                .then(data => {
                    let html = '';
                    if (data && data.code) {
                        html += '<h3>退款查询结果</h3>';
                        html += '<p><b>返回码:</b> ' + data.code + '</p>';
                        html += '<p><b>返回信息:</b> ' + (data.message || '') + '</p>';
                        html += '<p><b>退款状态:</b> ' + (data.status || '') + '</p>';
                        html += '<p><b>退款金额:</b> ' + (data.refundAmount || '') + '</p>';
                        html += '<p><b>退款成功时间:</b> ' + (data.refundSuccessDate || '') + '</p>';
                        html += '<p><b>失败原因:</b> ' + (data.failReason || '') + '</p>';
                    } else {
                        html = '<p>退款查询失败或无结果</p>';
                    }
                    document.getElementById('refundQueryContent').innerHTML = html;
                    document.getElementById('refundQueryModal').style.display = 'block';
                })
                .catch(err => {
                    document.getElementById('refundQueryContent').innerHTML = '<p>退款查询请求失败</p>';
                    document.getElementById('refundQueryModal').style.display = 'block';
                });
        }
        function closeRefundQueryModal() {
            document.getElementById('refundQueryModal').style.display = 'none';
            window.location.reload();
        }
    </script>
</body>
</html>