$(document).ready(function(){
	var len;
	var textarea=document.getElementsByTagName('textarea')[0];
	var num=document.getElementById('numberLitmit');
	var btn=document.getElementById("sendMessage");
	var dateTimeBox=document.getElementById("date-time-box");
	var form=document.getElementsByTagName('form')[0];
	var inputFiles=document.getElementsByTagName('input');
	var inputNum=0;
	
	//获取当前时间
	var nowDay=new Date();
	var month=nowDay.getMonth()+1;
	var day=nowDay.getDay();
	if(day==1)	day="一";
	else if(day==2) day="二";
	else if(day==3) day="三";
	else if(day==4) day="四";
	else if(day==5) day="五";
	else if(day==6) day="六";
	else if(day==7) day="七";
	var nowDayString="发布时间: "+nowDay.getFullYear()+"-"+month+"-"+nowDay.getDate()+"         星期"+day;
	dateTimeBox.innerHTML=nowDayString;
	
	//按钮和字数限制初始化
	btn.className="message-no-button";
	num.innerHTML='0'+'/150';
	//获取编辑器中字数长度
	textarea.onkeyup=function(e){
		len=this.value.length;
		if(len<=0||len>150)		
			btn.className="message-no-button";
		else
			btn.className="message-button";
		num.innerHTML=len+'/150';
	}
	
    //点击添加图片
    $(".addPic").click(function(){
    	inputNum++;
    	var div=document.createElement('div');
    	var html;
    	var html1='<div class="uploadPic">请上传相关图片一张:<br />'+
				 '<input class="fake" type="text" disabled="disabled" name="txt1"/>'+
	             '<button class="upload" name="file">浏览</button>'+
	             '<input id="file" type="file" name="files" onchange="txt1.value=this.value"/>'+
				 '</div>';
		var html2='<div class="uploadPic">请上传相关图片一张:<br />'+
				 '<input class="fake" type="text" disabled="disabled" name="txt2"/>'+
	             '<button class="upload" name="file">浏览</button>'+
	             '<input id="file" type="file" name="files" onchange="txt2.value=this.value"/>'+
				 '</div>';
		var html3='<div class="uploadPic">请上传相关图片一张:<br />'+
				 '<input class="fake" type="text" disabled="disabled" name="txt3"/>'+
	             '<button class="upload" name="file">浏览</button>'+
	             '<input id="file" type="file" name="files" onchange="txt3.value=this.value"/>'+
				 '</div>';
		if(inputNum<=3&&inputNum>=0){
			$(".deletePic").css({"opacity":"1","cursor":"pointer"});
			if(inputNum==1)	html=html1;
			else if(inputNum==2)	html=html2;
			else if(inputNum==3){
				html=html3;
				$(".addPic").css({'opacity':'0.4','cursor':'default'});
			}
			div.innerHTML=html;
			$(div).insertBefore(".messageAction");
		}
		else{
			inputNum--;
		}
    });
    
    //删除图片
    $(".deletePic").click(function(){
    	inputNum--;
    	if(inputNum>=0){
    		if(inputNum==0)	$(this).css({"opacity":"0.4","cursor":"default"});
    		else	$(".addPic").css({'opacity':'1','cursor':'pointer'});
		$(".uploadPic:last").remove();
    	}
    	else inputNum++;
    })
})
   //提交表单时检查是否有错
    function checkForm(){
    	var result=true;
    	var txt=$(".message").val();
    	if(txt.length<=0||txt.length>150)	result=false;
    	if($("input").val()==""){
    		alert('请上传相应的图片或删除图片');
    		result=false;
    	}
    	return result;
   }
