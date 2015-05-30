function checkForm(){
		var result=true;
		var id=$("#studentNumber").val();
		var name=$("#name").val();
		var gdutPass=$("#GDUTPassword").val();
		var pass=$("#password").val();
		var stuPic=$("#firstfile").val();
		var aboutPic=$("#secondfile").val();
		if(id==""){
			$("#idInform").text("申请人学号不能为空");
			result=false;
		}
		else	
			$("#idInform").text("");
		if(name==""){
			$("#nameInform").text("申请社团名称不能为空");
			result=false;
		}
		else
			$("#nameInform").text("");
		if(gdutPass==""){
			$("#gdutPassInform").text('工大密码不能为空');
			result=false;
		}
		else
			$("#gdutPassInform").text('');
		if(pass==""){
			$("#passInform").text("密码不能为空");
			result=false;
		}
		else
			$("#passInform").text("");
		if(stuPic==""){
			$("#firstFileInform").text("请选择相应的图片上传");
			result=false;
		}
		else
			$("#firstFileInform").text("");
		if(aboutPic==""){
			$("#secondFileInform").text("请选择相应的图片上传");
			result=false;
		}
		else
			$("#secondFileInform").text("");
		return result;
	}