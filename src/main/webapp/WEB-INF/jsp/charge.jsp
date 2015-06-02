<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>水费充值</title>
    <link href="/LifeInGDUT/css/water.css" rel="stylesheet">
</head>
<body>
	<!--页面遮罩层-->
	<div id="mask"></div>
	<!--开头部分-->
	<div class="water-head">
		<div class="water-head-contain">
			<p>送水后台管理界面</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="water-main" id="show-main">
		<!--菜单-->
		<div class="water-menu">
			<ul class="first-menu">
				<li><a href="#" class="first-menu-li">全部订单</a>
					<ul class="second-menu">
						<li><a href="/LifeInGDUT/water/getOrder?state=-1&page=1">未接订单</a></li>
						<li><a href="/LifeInGDUT/water/getOrder?state=0&page=1">已接订单</a></li>
						<li><a href="/LifeInGDUT/water/getOrder?state=1&page=1">送达订单</a></li>
					</ul>
				</li>
				<li><a href="#" class="first-menu-li">充值</a>
					<ul class="second-menu">
						<li class="chooseBgColor"><a href="/LifeInGDUT/water/charge">水费充值</a></li>
						<li><a href="/LifeInGDUT/water/getChargeRecord?page=1">充值订单</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="water-data">
			<div class="water-data-header">
				<p>您当前的位置:水费充值</p>
				<p class="returnLogin"><a href="login.html">退出登录</a></p>
			</div>
			<div class="water-charge">
				<div class="water-charge-head">请在下面方框输入相关的信息：</div>
				<div class="water-charge-main">
					<form>
						<p>充值学号:<input type="text" name="studentId" id="studentId"/><span id="stuIdInform"></span></p>
						<p>充值桶数:<input type="text" name="number" id="waterNumbers"/><span id="numberInform"></span></p>
						<div class="water-charge-button">确认充值</div>
					</form>
				</div>
			</div>
			<!--确认充值信息-->
			<div class="water-charge-surebox">
				<div class="water-charge-surebox-head">请确认以下信息是否无误：<span class="water-charge-sureclose">关闭</span></div>
				<div class="water-charge-surebox-main">
					<div class="water-charge-infor-sure">充值学号：<span class="sure-stuid"></span></div>
					<div class="water-charge-infor-sure">充值桶数：<span class="sure-number"></span></div>
					<div class="water-charge-infor-sure">应付金额：<span class="sure-money"></span></div>
					<div class="water-charge-infor-button">点击确认</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/water.js"></script>
</html>