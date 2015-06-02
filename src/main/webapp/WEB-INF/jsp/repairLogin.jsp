<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台维修管理登录界面</title>
    <link href="/LifeInGDUT/css/repairLogin.css" rel="stylesheet">
</head>
<body>
	<div class="name">广工大生活助手维修系统后台管理页面</div>
	<div class="login">
		<div class="login-main">
			<form action="/LifeInGDUT/repairAdmin/login" method="post">
				<input name="name" type="text" class="username"/>
				<input name="password" type="password" class="password"/>
				<p style="color: red; font-size: 16px;">${info }</p>
				<button type="submit">登录</button>
			</form>
		</div>
	</div>
</body>
<script src="/LifeInGDUT/js/myjs/comment.js"></script>
</html>