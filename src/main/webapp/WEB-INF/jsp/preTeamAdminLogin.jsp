<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>工大生活助手生活圈信息审核页面</title>
    <link href="/LifeInGDUT/css/preTeamAdminLogin.css" rel="stylesheet">
</head>
<body>
	<div class="login">
		<p class="loginHead">工大生活助手生活圈信息审核页面</p>
		<form action="/LifeInGDUT/preTeamAdmin/login" method="post">
			<p>用户名：<input name="name" type="text" class="username"/></p>
			<p>密    码： <input name="password" type="password" class="password"/></p>
			<p style="color: red; font-size: 16px;">${info }</p>
			<input type="submit" value="登录" name="button" class="loginNow" />
		</form>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
</html>