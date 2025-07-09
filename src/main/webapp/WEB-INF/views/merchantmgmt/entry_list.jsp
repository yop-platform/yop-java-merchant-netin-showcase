<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>进件申请列表 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>进件申请列表（merchantmgmt独立模块）</h3>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>ID</th>
            <th>签约名</th>
            <th>简称</th>
            <th>申请单号</th>
            <th>商户编号</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="entryTableBody"></tbody>
    </table>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
$(function(){
    $.get('/merchantmgmt/entry/list', function(list){
        var html = '';
        list.forEach(function(e){
            html += '<tr>' +
                '<td>' + e.id + '</td>' +
                '<td>' + (e.signName||'') + '</td>' +
                '<td>' + (e.shortName||'') + '</td>' +
                '<td>' + (e.applicationNo||'') + '</td>' +
                '<td>' + (e.merchantNo||'') + '</td>' +
                '<td>' + (e.applicationStatus||'') + '</td>' +
                '<td>' + (e.createTime ? new Date(e.createTime).toLocaleString() : '') + '</td>' +
                '<td><a href="/merchantmgmt/entry/detail?id=' + e.id + '" class="btn btn-sm btn-info">详情</a></td>' +
                '</tr>';
        });
        $('#entryTableBody').html(html);
    });
});
</script>
</body>
</html> 