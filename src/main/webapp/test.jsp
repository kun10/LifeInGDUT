<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<form action="/LifeInGDUT/repair/add" method="post">
		type:<input type="text" name="type" ><br>
		schoolArea:<input type="text" name="schoolArea"><br>
		lifeArea:<input type="text" name="lifeArea"><br>
		building:<input type="text" name="building"><br>
		address:<input type="text" name="address"><br>
		description:<input type="text" name="description"><br>
		contact:<input type="text" name="contact"><br>
		phone:<input type="text" name="phone"><br>
		studentId:<input type="text" name="studentId"><br>
		time:<input type="text" name="time"><br>
		<input type="submit" value="提交">
	</form>
	<form action="/LifeInGDUT/repair/showRepair" method="post">
	state:<input type="text" name="state" >
	pageNumber:<input type="text" name="pageNumber">
	page_size:<input type="text" name="page_size">
	studentId:<input type="text" name="studentId">
	<input type="submit" value="提交">
	</form>
	
	
	<form action="">
		<input type="checkbox" name="authoritys" value="ROLE_NEWADMIN">新增管理员
		<input type="checkbox" name="authoritys" value="ROLE_ANSWERBOOK">接待预订
		<input type="checkbox" name="authoritys" value="ROLE_SEARSHBOOK	">订房查询
		<input type="checkbox" name="authoritys" value="ROLE_DAYSTARTEND">日始日结
		<input type="checkbox" name="authoritys" value="ROLE_STANDARD">客房标准管理
		<input type="checkbox" name="authoritys" value="ROLE_PAY">付款方式
		<input type="checkbox" name="authoritys" value="ROLE_SERVICEMANAGE">收费项目管理
		<input type="checkbox" name="authoritys" value="ROLE_BUSINESS">收费款项
		<input type="checkbox" name="authoritys" value="ROLE_FINANCE">财务查询
		<input type="checkbox" name="authoritys" value="ROLE_USERMANAGE">用户管理
		<input type="checkbox" name="authoritys" value="ROLE_ADMINMANAGE">管理员权限管理
		<input type="checkbox" name="authoritys" value="ROLE_DATAMANAGE">数据管理
	</form>
	
	
</body>
</html>