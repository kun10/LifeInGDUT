<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>送水后台维修管理登录界面</title>
<link href="/LifeInGDUT/css/waterlogin.css" rel="stylesheet">
</head>
<body>
	<div class="login">
		<p class="loginHead">工大生活助手送水后台管理页面</p>
		<form action="/LifeInGDUT/water/login" method="post">
			<p>
				用户名：<input name="userName" type="text" class="username" value="123"/>
			</p>
			<p>
				密 码： <input name="password" type="password" class="password" value="123"/>
			</p>
			<p>
				<button type="submit" class="loginNow">登录</button>
			</p>
		</form>
		<p style="color: red; font-size: 16px;margin-left:290px;margin-top:-20px">${errmsg}</p>
	</div>

</body>
</html>