<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>工大生活助手校内新闻发布页面</title>
    <link href="/LifeInGDUT/css/news.css" rel="stylesheet">
</head>
<body>
	<!--页面遮罩层-->
	<div id="mask"></div>
	<!--开头部分-->
	<div class="news-head">
		<div class="news-head-contain">
			<p>欢迎来到广工校内新闻发布页面</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="news-main" id="news-main-part">
		<!--菜单-->
		<div class="news-main-left">
			<ul class="first-menu">
				<li><a href="#" class="first-menu-li">信息列表</a>
					<ul class="second-menu">
						<li><a href="news.html" id="chooseColor">发布新闻</a></li>
						<!--<li><a href="newsOk.html">查看已发信息</a></li>-->
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="news-main-right">
			<div class="news-right-header">
				<p>您当前的位置:发布校内新闻</p>
				<p id="returnLogin"><a href="/LifeInGDUT/newsAdmin/logout">退出登录</a></p>
			</div>
			<div class="news-information">
				<div id="date-time-box"></div>
				<form  action="/LifeInGDUT/message/add" method="post"  enctype="multipart/form-data"  onsubmit="return checkForm()">
					<div class="message-box">
						<div class="message-box-header"></div>
						<textarea type="text" name="content" class="message"></textarea>
						<div class="message-box-footer"></div>
						<div id="numberLitmit"></div>
					</div>
					<div class="messageAction">
						<div class="addPic">添加图片</div>
						<div class="deletePic">删除图片</div>
						<input type="hidden" name="section" value="3" > 
						<input type="submit" value="发布信息" id="sendMessage"></div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/news.js"></script>
</html>