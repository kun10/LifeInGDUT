<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>工大生活助手校内新闻登录界面</title>
    <link href="/LifeInGDUT/css/newsAdminLogin.css" rel="stylesheet">
</head>
<body>
	<div class="login-contain">
		<div class="login-contain-header">工大生活助手校内新闻发布页面</div>
		<div class="login">
		<form action="/LifeInGDUT/newsAdmin/login" method="post">
			<p>用户名：<input name="name" type="text" class="username"/></p>
			<p>密    码： <input name="password" type="password" class="password"/></p>
			<input type="submit" value="登录" name="button" class="loginNow" />
			<p style="color: red; font-size: 16px;">${info }</p>
		</form>
	</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
</html>