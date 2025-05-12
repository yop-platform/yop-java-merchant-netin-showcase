<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${product.name}</title>
</head>
<body>
    <h1>${product.name}</h1>
    
    <c:if test="${not empty sessionScope.user}">
        <p>欢迎, ${sessionScope.user.name} | <a href="<c:url value='/orders'/>">我的订单</a> | <a href="<c:url value='/logout'/>">退出</a></p>
    </c:if>
    <c:if test="${empty sessionScope.user}">
        <p><a href="<c:url value='/login'/>">登录</a></p>
    </c:if>

    <div>
        <h2>${product.name}</h2>
        <p>${product.description}</p>
        <p>价格: ¥${product.price}</p>
        <p>库存: ${product.stock}</p>

        <c:if test="${not empty sessionScope.user && product.stock > 0}">
            <form action="<c:url value='/orders/create'/>" method="post">
                <input type="hidden" name="productId" value="${product.id}">
                <label>
                    数量:
                    <input type="number" name="quantity" min="1" max="${product.stock}" value="1">
                </label>
                <button type="submit">立即购买</button>
            </form>
        </c:if>

        <c:if test="${product.stock <= 0}">
            <p style="color: red;">商品已售罄</p>
        </c:if>

        <c:if test="${empty sessionScope.user}">
            <p><a href="<c:url value='/login'/>">登录</a>后购买</p>
        </c:if>
    </div>

    <p><a href="<c:url value='/products'/>">返回商品列表</a></p>
</body>
</html>