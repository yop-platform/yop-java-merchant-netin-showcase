<%--
  Copyright: Copyright (c)2014
  Company: 易宝支付(YeePay)
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>文件上传 - 商户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>文件上传（merchantmgmt独立模块）</h3>
    <form id="uploadForm" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="fileType" class="form-label">文件类型</label>
            <select class="form-select" id="fileType" name="fileType" required>
                <option value="">请选择</option>
                <option value="licence">营业执照</option>
                <option value="legalFront">法人证件正面</option>
                <option value="legalBack">法人证件背面</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="file" class="form-label">选择文件</label>
            <input class="form-control" type="file" id="file" name="file" required>
        </div>
        <button type="submit" class="btn btn-primary">上传</button>
    </form>
    <div id="result" class="mt-3"></div>
</div>
<script src="/static/js/jquery.min.js"></script>
<script>
$(function() {
    $('#uploadForm').on('submit', function(e) {
        e.preventDefault();
        var formData = new FormData(this);
        $('#result').html('上传中...');
        $.ajax({
            url: '/merchantmgmt/file/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(res) {
                if(res.success) {
                    $('#result').html('<span class="text-success">上传成功，文件URL：' + res.fileUrl + '</span>');
                } else {
                    $('#result').html('<span class="text-danger">上传失败：' + res.message + '</span>');
                }
            },
            error: function() {
                $('#result').html('<span class="text-danger">请求失败</span>');
            }
        });
    });
});
</script>
</body>
</html> 