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
                        <td><fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>
                            <a href="<c:url value='/orders/${order.id}'/>">查看详情</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>