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
    <link href="/LifeInGDUT/css/check.css" rel="stylesheet">
</head>
<body onload="pagesNumberSet(${pageNumber},${allPages },1)">
	<!--页面遮罩层-->
	<div id="mask"></div>
	<!--开头部分-->
	<div class="check-head">
		<div class="check-head-contain">
			<p>生活圈申请账号审核页面</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="check-main" id="check-main-part">
		<!--菜单-->
		<div class="check-main-left">
			<ul class="first-menu">
				<li><a href="#" class="first-menu-li">申请账号列表</a>
					<ul class="second-menu">
						<li><a href="#" id="chooseColor">未审核账号</a></li>
						<li><a href="/LifeInGDUT/team/show">审核通过账号</a></li>
						<li><a href="/LifeInGDUT/preTeam/show?isCheck=1">审核不通过账号</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="check-main-right">
			<div class="check-right-header">
				<p>您当前的位置:未审核账号</p>
			</div>
			<div class="check-information">
				<table border="1px" width="100%" cellspacing="0" class="table">
				<thead>
					<tr>
						<td><input type="checkbox" name="checkallbox" value="checkbox" id="checkallbox"/></td>
						<td>社团名称</td>
						<td>申请人学号</td>
						<td>申请时间</td>
						<td>基本操作</td>
					</tr>
				</thead>
				<tbody>
					<form>
						<c:forEach items="${preTeams }" var="preTeam">
							<tr>
								<td><input type="checkbox" name="checkbox" value="checkbox" class="checkbox"/></td>
								<td>${preTeam.name }</td>
								<td>${preTeam.user.studentId }</td>
								<td>${preTeam.time }</td>
								<td>
									<span class="infor-more-button" onclick="inforWrite('${preTeam.user.studentId}','${preTeam.name }','${preTeam.time }','${preTeam.url }')">查看详情</span>
									<!-- <a href=""><span class="delete-button">删除记录</span></a> -->
								</td>
							</tr>
						</c:forEach>
						
					</form>
				</tbody>
			</table>
			<!--分页部分及修改状态部分-->
			<div class="check-data-pages">
				<div class="check-ok">审核通过</div>
				<div class="check-not-ok">审核不通过</div>
				<div id="setpage"></div> 
			</div>
			<!--查看详情-->
			<div id="more-infor">
				<div class="more-infor-head">
					<p>申请材料详情</p>
					<div class="more-infor-button">关闭</div>
				</div>	
				<div class="more-infor-main">	</div>
			</div>
			<!--图片放大-->
			<div id="pic-bigger">
				<div class="more-infor-head">
					<div class="more-img-button">返回</div>
				</div>	
				<img alt="">
			</div>
		</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/check.js"></script>
</html>