<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>送水后台管理界面</title>
<link href="/LifeInGDUT/css/water.css" rel="stylesheet">
</head>
<body onload='pagesNumberSet(1,16)'>
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
					</ul></li>
				<li><a href="#" class="first-menu-li">充值</a>
					<ul class="second-menu">
						<li><a href="/LifeInGDUT/water/charge">水费充值</a></li>
						<li class="chooseBgColor"><a
							href="/LifeInGDUT/water/getChargeRecord?page=1">充值订单</a></li>
					</ul></li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="water-data">
			<div class="water-data-header">
				<p>您当前的位置:充值订单</p>
				<p class="returnLogin">
					<a href="login.html">退出登录</a>
				</p>
			</div>
			<table border="1px" width="100%" cellspacing="0">
				<thead>
					<tr>
						<td><input type="checkbox" name="checkallbox"
							value="checkbox" id="checkallbox" class="checkbox" /></td>
						<td>充值单号</td>
						<td>学号</td>
						<td>充值桶数</td>
						<td>充值时间</td>
						<td>基本操作</td>
					</tr>
				</thead>
				<tbody>
					<form>
						<c:forEach items="${charges}" var="charge">
							<tr>
								<td><input type="checkbox" name="checkbox" value="checkbox"
									class="checkbox" /></td>
								<td>${charge.id}</td>
								<td>${charge.studentId}</td>
								<td>${charge.number}</td>
								<td>${charge.time}</td>
								<td><a><span class="delete-button">删除记录</span></a></td>
							</tr>
						</c:forEach>
					</form>
				</tbody>
			</table>
			<!--分页部分及修改状态部分-->
			<div class="water-data-pages">
				<div id="setpage"></div>
			</div>
			<!--订单详情-->
			<div id="more-infor">
				<div class="more-infor-head">
					<p>订单详情</p>
					<div class="more-infor-button">关闭</div>
				</div>
				<div id="moreInfor-Table"></div>
			</div>
		</div>
	</div>
</body>
<script src="js/jquery.js"></script>
<script src="js/water.js"></script>
</html>