<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		<form action="/LifeInGDUT/message/add" method="post"  
        enctype="multipart/form-data"> 
        section:<input type="text" name="section">  
        content:<input type="text" name="content">  
        <input type="hidden" name="studentId"  value="311">  
        <p>  
            选择文件:<input type="file" name="files">  
        <p>  
            选择文件:<input type="file" name="files">  
        <p>  
            选择文件:<input type="file" name="files">  
        <p>  
            <input type="submit" value="提交">  
    </form>  
</body>
</html>