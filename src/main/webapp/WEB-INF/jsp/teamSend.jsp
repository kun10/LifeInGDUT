<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>生活圈审核页面</title>
    <link href="/LifeInGDUT/css/life.css" rel="stylesheet">
</head>
<body>
	<!--页面遮罩层-->
	<div id="mask"></div>
	<!--开头部分-->
	<div class="life-head">
		<div class="life-head-contain">
			<p>欢迎来到广工生活圈</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="life-main" id="life-main-part">
		<!--菜单-->
		<div class="life-main-left">
			<ul class="first-menu">
				<li><a href="#" class="first-menu-li">信息列表</a>
					<ul class="second-menu">
						<li><a href="life.html" id="chooseColor">发布信息</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="life-main-right">
			<div class="life-right-header">
				<p>您当前的位置:发布信息</p>
				<p id="returnLogin"><a href="/LifeInGDUT/team/logout">退出登录</a></p>
			</div>
			<div class="life-information">
				<div id="date-time-box"></div>
				<form action="/LifeInGDUT/message/add" method="post"  enctype="multipart/form-data" onsubmit="return checkForm()">
					<div class="message-box">
						<div class="message-box-header"></div>
						<textarea type="text" name="content" class="message"></textarea>
						<div class="message-box-footer"></div>
						<div id="numberLitmit"></div>
					</div>
					<div class="messageAction">
						<div class="addPic">添加图片</div>
						<div class="deletePic">删除图片</div>
					    <input type="hidden" name="section" value="2" > 
						<input type="submit" value="发布信息" id="sendMessage"></div>
				</form>
			</div>
			</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/life.js"></script>
</html>