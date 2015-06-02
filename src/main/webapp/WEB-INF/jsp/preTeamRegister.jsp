<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>生活圈账号注册页面</title>
    <link href="/LifeInGDUT/css/preTeamRegister.css" rel="stylesheet">
</head>
<body>
	<!--开头部分-->
	<div class="register-head">
		<div class="register-head-contain">
			<p>生活圈账号注册页面</p>
		</div>
	</div>
	<!--主要部分-->
	<div class="register-main">
		<!--数据显示部分-->
		<div class="register-main-box">
			<div class="register-box-header">
				<div class="register-box-header-contain">
					<p>社团申请信息填写</p>
				</div>
			</div>
			<div class="register-information">
				<form action="/LifeInGDUT/preTeam/apply" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
					<p>申请人学号:<input type="text" name="studentId" id="studentNumber"/><span id="idInform"></span></p>
					<p>申请社团名称:<input type="text" name="name" id="name"/><span id="nameInform"></span></p>
					<p>工大密码:<input type="password" name="GDUTPassword" id="GDUTPassword"/><span id="gdutPassInform"></span></p></p>
					<p>密码:<input type="password" name="password" id="password"/><span id="passInform"></span></p>
					<p>确认密码:<input type="password" name="password_again" id="password"/><span id="passInform"></span></p>
					<p class="uploadFiles">请上传学生证图片一张:<br />
						<input class="fake" type="text" disabled="disabled" name="firsttxt"/>
            			<button class="upload" name="files">浏览</button>
            			<input id="firstfile" type="file" name="files" onchange="firsttxt.value=this.value"/>
            			<span id="firstFileInform"></span></p>
            		</p>
            		<p class="uploadFiles">请上传本人社团聘书或者其它证明本人为该社团的图片一张:<br />
						<input class="fake" type="text" disabled="disabled" name="secondtxt"/>
            			<button class="upload" name="files">浏览</button>
            			<input id="secondfile" type="file" name="files" onchange="secondtxt.value=this.value"/>
            			<span id="secondFileInform"></span></p>
            		</p>
            		<input type="file" name="head" >
            		<p style="color: red; font-size: 16px;">${info }</p>
            		<input type="submit" value="提交" id="submit"/>
           	 	</form>
			</div>
		</div>
</body>
<script src="/LifeInGDUT/js/jquery.js"></script>
<script src="/LifeInGDUT/js/preTeamRegister.js"></script>
</html>