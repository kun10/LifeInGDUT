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
<body onload="pagesNumberSet(${pageNumber},${allPages },2)">
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
						<li><a href="/LifeInGDUT/preTeam/show">未审核账号</a></li>
						<li><a href="#" id="chooseColor">审核通过账号</a></li>
						<li><a href="/LifeInGDUT/preTeam/show?isCheck=1">审核不通过账号</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="check-main-right">
			<div class="check-right-header">
				<p>您当前的位置:审核通过账号</p>
			</div>
			<div class="check-information">
				<table border="1px" width="100%" cellspacing="0" class="table">
				<thead>
					<tr>
						<!-- <td><input type="checkbox" name="checkallbox" value="checkbox" id="checkallbox"/></td> -->
						<td>社团名称</td>
						<td>负责人学号</td>
						<td>申请通过时间</td>
						<!-- <td>基本操作</td> -->
					</tr>
				</thead>
				<tbody>
					<form>
						<c:forEach items="${teams }" var="team">
							<tr>
								<!-- <td><input type="checkbox" name="checkbox" value="checkbox" class="checkbox"/></td> -->
								<td>${team.name }</td>
								<td> ${team.user.studentId }</td>
								<td> ${team.time }</td>
								<!-- <td>
									<a><span class="delete-button">删除记录</span></a>
								</td> -->
							</tr>
						</c:forEach>
					</form>
				</tbody>
			</table>
			<!--分页部分及修改状态部分-->
			<div class="check-data-pages">
				<div id="setpage"></div> 
			</div>
		</div>
	</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/check.js"></script>
</html>