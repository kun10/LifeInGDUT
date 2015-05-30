<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>后台维修管理界面</title>
    <link href="/LifeInGDUT/css/repair.css" rel="stylesheet">
</head>
<body onload="setPageNumber(${pageNumber},${allPages })">
	<!--页面遮罩层-->
	<div id="mask"></div>
	<!--开头部分-->
	<div class="repair-head">
		<div class="repair-head-contain">
			<p>后台维修管理界面</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="repair-main" id="show-main">
		<!--菜单-->
		<div class="repair-menu">
			<ul class="first-menu">
				<li><a href="#">全部订单</a>
					<ul class="second-menu">
						<li><a href="/LifeInGDUT/repair/showWebRepair?state=1">未修订单</a></li>
						<li><a href="/LifeInGDUT/repair/showWebRepair?state=2">已修订单</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--数据显示部分-->
		<div class="repair-data">
			<div class="repair-data-header">
				<p>您当前的位置:
						<c:if test="${state==1 }">未处理</c:if>
						<c:if test="${state==2 }">已处理</c:if>
				</p>
				<p class="returnLogin"><a href="/LifeInGDUT/repairAdmin/logout">退出登录</a></p>
			</div>
			<table border="1px" width="100%" cellspacing="0">
				<thead>
					<tr>
						<c:if test="${state==1 }">
							<td><input type="checkbox" name="checkallbox" value="checkbox" id="checkallbox"/></td>
						</c:if>
						<td>订单号</td>
						<td>类别</td>
						<td>校区</td>
						<td>生活区</td>
						<td>建筑名</td>
						<td>房号或位置</td>
						<td>联系电话</td>
						<td>基本操作</td>
					</tr>
				</thead>
				<tbody>
					<form >
						<c:forEach items="${repairs }" var="repair">
							<tr>
								<c:if test="${state==1 }">
								<td><input type="checkbox" name="checkbox" value="checkbox"/></td>
								</c:if>
								<td>${repair.id }</td>
								<td>${repair.type }</td>
								<td>${repair.schoolArea }</td>
								<td>${repair.lifeArea }</td>
								<td>${repair.building }</td>
								<td>${repair.address }</td>
								<td>${repair.phone }</td>	
								<td>
								<%-- inforWrite(${repair.id },'${repair.type }','${repair.schoolArea }','${repair.lifeArea }','${repair.building }','${repair.address }','${repair.contact }','${repair.studentId }','${repair.phone }','${repair.time }','${repair.submitTime }','${repair.description }'   ) --%>
									<span class="infor-more-button" onclick="postInfor(${repair.id})">查看更多</span>
									<a href="/LifeInGDUT/repair/delete?id=${repair.id }&pageNumber=${pageNumber}&state=${state}"><span class="delete-button">删除记录</span></a>
								</td>
							</tr>
						</c:forEach>	
					</form>
				</tbody>
			</table>
			<!--分页部分及修改状态部分-->
			<div class="repair-data-pages">
				<div class="checkBoxChoose">改为已修状态</div>
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
	<script>
	
	</script>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/repair.js"></script>
</html>