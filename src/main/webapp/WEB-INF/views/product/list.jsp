<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品列表</title>
    <style>
        .product-list {
            display: flex;
            flex-wrap: wrap;
        }
        .product-item {
            border: 1px solid #ddd;
            margin: 10px;
            padding: 15px;
            width: 200px;
        }
    </style>
</head>
<body>
    <h1>商品列表</h1>
    
    <c:if test="${not empty sessionScope.user}">
        <p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/orders'/>">我的订单</a> | <a href="<c:url value='/logout'/>">退出</a></p>
    </c:if>
    <c:if test="${empty sessionScope.user}">
        <p><a href="<c:url value='/login'/>">登录</a></p>
    </c:if>
    
    <div class="product-list">
        <c:forEach items="${products}" var="product">
            <div class="product-item">
                <h3>${product.name}</h3>
                <p>${product.description}</p>
                <p>价格: ¥${product.price}</p>
                <p>库存: ${product.stock}</p>
                <a href="<c:url value='/products/${product.id}'/>">查看详情</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>