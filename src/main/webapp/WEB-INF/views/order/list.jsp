<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>我的订单</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>我的订单</h1>
    
    <p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/products'/>">商品列表</a> | <a href="<c:url value='/logout'/>">退出</a></p>
    
    <c:if test="${empty orders}">
        <p>您还没有订单，<a href="<c:url value='/products'/>">去购物</a></p>
    </c:if>
    
    <c:if test="${not empty orders}">
        <table>
            <thead>
                <tr>
                    <th>订单号</th>
                    <th>商品ID</th>
                    <th>数量</th>
                    <th>总金额</th>
                    <th>状态</th>
                    <th>退款状态</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.id}</td>
                        <td>${order.productId}</td>
                        <td>${order.quantity}</td>
                        <td>¥${order.totalAmount}</td>
                        <td>${order.status}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty payment && payment.status eq 'REFUNDED'}">已退款</c:when>
                                <c:when test="${not empty payment && payment.status eq 'REFUND_FAILED'}">退款失败</c:when>
                                <c:otherwise>-</c:otherwise>
                            </c:choose>
                        </td>
                        <td><fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a href="<c:url value='/orders/${order.id}'/>">查看详情</a>
                            <button type="button" onclick="queryOrder('${order.id}')">查单</button>
                            <button type="button" onclick="queryRefund('${order.id}')">查退款</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- 查单结果弹窗 -->
    <div id="orderQueryModal" style="display:none; position:fixed; left:50%; top:30%; transform:translate(-50%,0); background:#fff; border:1px solid #888; padding:20px; z-index:9999; min-width:400px;">
        <div id="orderQueryContent"></div>
        <button onclick="closeOrderQueryModal()">关闭</button>
    </div>
    <!-- 查退款结果弹窗 -->
    <div id="refundQueryModal" style="display:none; position:fixed; left:50%; top:35%; transform:translate(-50%,0); background:#fff; border:1px solid #888; padding:20px; z-index:9999; min-width:400px;">
        <div id="refundQueryContent"></div>
        <button onclick="closeRefundQueryModal()">关闭</button>
    </div>
    <script>
        function queryOrder(orderId) {
            fetch('/orders/queryOrder?orderId=' + orderId)
                .then(resp => resp.json())
                .then(data => {
                    let html = '';
                    if (data && data.code) {
                        html += '<h3>查单结果</h3>';
                        html += '<p><b>返回码:</b> ' + data.code + '</p>';
                        html += '<p><b>返回信息:</b> ' + (data.message || '') + '</p>';
                        html += '<p><b>订单状态:</b> ' + (data.status || '') + '</p>';
                        html += '<p><b>订单金额:</b> ' + (data.orderAmount || '') + '</p>';
                        html += '<p><b>支付金额:</b> ' + (data.payAmount || '') + '</p>';
                        html += '<p><b>支付方式:</b> ' + (data.payWay || '') + '</p>';
                        html += '<p><b>支付成功时间:</b> ' + (data.paySuccessDate || '') + '</p>';
                        html += '<p><b>失败原因:</b> ' + (data.failReason || '') + '</p>';
                    } else {
                        html = '<p>查单失败或无结果</p>';
                    }
                    document.getElementById('orderQueryContent').innerHTML = html;
                    document.getElementById('orderQueryModal').style.display = 'block';
                })
                .catch(err => {
                    document.getElementById('orderQueryContent').innerHTML = '<p>查单请求失败</p>';
                    document.getElementById('orderQueryModal').style.display = 'block';
                });
        }
        function closeOrderQueryModal() {
            document.getElementById('orderQueryModal').style.display = 'none';
        }
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
            // 关闭后刷新页面以同步本地退款状态
            window.location.reload();
        }
    </script>
</body>
</html>