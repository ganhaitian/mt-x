<%--
  Created by IntelliJ IDEA.
  User: zhijieliu
  Date: 2014/7/14
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<%
    String realPath= application.getContextPath();
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="<%=realPath %>/resources/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>
<body style="padding-top: 50px;">

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <form action="<c:url value="/j_spring_security_check"></c:url>" method="post">
                <div class="form-group">
                    <label for="">用户名</label>
                    <input type="text" class="form-control" name="userId"/>
                </div>
                <div class="form-group">
                    <label for="">密码</label>
                    <input type="password" class="form-control" name="token"/>
                </div>
                <div class="form-group">
                    <label for="">平台</label>
                    <input type="text" class="form-control" name="platformId"/>
                </div>
                <button class="btn btn-default" type="submit">提交</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
