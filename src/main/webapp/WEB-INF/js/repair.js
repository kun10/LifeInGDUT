$(document).ready(function(){
	var main=document.getElementById('show-main');
	var moreInforBox=document.getElementById('more-infor');
	var moreInforTableBox=document.getElementById('moreInfor-Table');
	var oMask=document.getElementById('mask');
//	点击事件，根据类名判断点击了哪个按钮
	main.onclick=function(e){
		e=e||window.event;     
		var el=e.srcElement;
		var el=e.target;		//兼容火狐
		switch(el.className){
            //点击删除按钮
			case 'delete-button':deleteNode(el.parentNode.parentNode);break;
			//点击关闭按钮
			case 'more-infor-button':hideNode(oMask);hideNode(el.parentNode.parentNode);break;
			//点击分页页数按钮
			case 'pagehref':judgePage(el);break;
		}
	}
//	删除节点函数
	function deleteNode(node){
		node.parentNode.removeChild(node);
	}
//	隐藏节点函数
	function hideNode(node){
		node.style.display="none";
	}
})
	var totalpage,pagesize,cpage,count,curcount,outstr; 
	var sHeight=document.body.scrollHeight;
	var checkAllBox=document.getElementById('checkallbox');
	var checkboxes=document.getElementsByName('checkbox');
//	页数初始化 
	pagesize = 10; 		//总共可以显示多少页
	outstr = ""; 		//字符串，用来存放生成的页码
//	复选框全选或不选点击事件
	checkAllBox.onclick=function(){
		changeBoxCheck();
	}
//点击改为已修未修状态的按钮
	$(".checkBoxChoose").click(function(){
		checkBox();
	})
	function setPageNumber(nowpage,allpage,state){
		cpage=nowpage;
		totalpage=allpage;
		setpage(state);
	}
//	创建页数函数
	function judgePage(el){
		var $el=$(el);
		var elText=$el.text();
		if(!isNaN(elText))	gotopage(elText);
		else if(elText=="下一页"){
			gotopage(parseInt(cpage)+1);
		}
		else if(elText=="上一页"){
			gotopage(parseInt(cpage)-1);
		}
	}
	function gotopage(pages){   
	    cpage = pages;        //把页面计数定位到第几页
	    setpage(); 
	    //reloadpage(target);    //调用显示页面函数显示第几页,这个功能是用在页面内容用ajax载入的情况 
	} 
	function setpage(state){ 
	    if(totalpage<=10){        //总页数小于十页 
	        for (count=1;count<=totalpage;count++) 
	        {    if(count!=cpage) 
	            { 
	                outstr = outstr +"<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+count+"' class='pagehref'>"+count+"</a>"; 
	            }else{ 
	                outstr = outstr + "<span class='current' >"+count+"</span>"; 
	            } 
	        } 
	    } 
	    if(totalpage>10){        //总页数大于十页 
	        if(parseInt((cpage-1)/10) == 0) 
	        {             
	            for (count=1;count<=10;count++) 
	            {    if(count!=cpage) 
	                { 
	                    outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+count+"' class='pagehref'>"+count+"</a>"; 
	                }else{ 
	                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
	                } 
	            } 
	            outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+(cpage+1)+"' class='pagehref'>下一页</a>"; 
	        } 
	        else if(parseInt((cpage-1)/10) == parseInt(totalpage/10)) 
	        {     
	            outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+(cpage-1)+"'  class='pagehref'>上一页</a>"; 
	            for (count=parseInt(totalpage/10)*10+1;count<=totalpage;count++) 
	            {    if(count!=cpage) 
	                { 
	                    outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+count+"' class='pagehref'>"+count+"</a>"; 
	                }else{ 
	                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
	                } 
	            } 
	        } 
	        else 
	        {     
	            outstr = outstr +  "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+(cpage-1)+"'  class='pagehref'>上一页</a>"; 
	            for (count=parseInt((cpage-1)/10)*10+1;count<=parseInt((cpage-1)/10)*10+10;count++) 
	            {         
	                if(count!=cpage) 
	                { 
	                    outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+count+"' class='pagehref'>"+count+"</a>"; 
	                }else{ 
	                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
	                } 
	            } 
	            outstr = outstr + "<a href='/LifeInGDUT/repair/showWebRepair?state="+state+"&pageNumber="+(cpage+1)+"' class='pagehref'>下一页</a>"; 
	        } 
	    }     
	    document.getElementById("setpage").innerHTML = "<div id='setpage'><span id='info'>共"+totalpage+"页|第"+cpage+"页<\/span>" + outstr + "<\/div>"; 
	    outstr = ""; 
	}
//	点击查看订单详情时发送请求的函数
	function postInfor(repair_id){
		 $.post("/LifeInGDUT/repair/getMore",
				  {
				    id:repair_id
				  },
				  function(data,status){
					   inforWrite(data[0].id,data[0].type,data[0].state,data[0].schoolArea,data[0].lifeArea,data[0].building,data[0].address,data[0].contact,data[0].studentId,data[0].phone,data[0].time,data[0].submitTime,data[0].description);   
				  });
	}
//	动态生成订单函数
	function inforWrite(id,type,state,schoolArea,lifeArea,building,address,contact,studentId,phone,time,submitTime,description){
		$('#mask').css('height',sHeight);
		$('#mask').show();
		$('#moreInfor-Table').empty();
		var html='<table border="1" cellspacing="0" width="400px">'+
				 '<tbody><tr><td>订单号</td><td>'+id+'</td></tr>'+
				 '<tr><td>类别</td><td>'+type+'</td></tr>'+
				 '<tr><td>状态</td><td>'+state+'</td></tr>'+
				 '<tr><td>校区</td><td>'+schoolArea+'</td></tr>'+
				 '<tr><td>生活区</td><td>'+lifeArea+'</td></tr>'+
				 '<tr><td>建筑名</td><td>'+building+'</td></tr>'+
				 '<tr><td>房号或位置</td><td>'+address+'</td></tr>'+
				 '<tr><td>联系人</td><td>'+contact+'</td></tr>'+
				 '<tr><td>学号</td><td>'+studentId+'</td></tr>'+
				 '<tr><td>联系电话</td><td>'+phone+'</td></tr>'+
				 '<tr><td>预约时间段</td><td>'+time+'</td></tr>'+
				 '<tr><td>提交时间</td><td>'+submitTime+'</td></tr>'+
				 '<tr><td>故障描述</td><td>'+description+'</td></tr></tbody></table>';
		$('#moreInfor-Table')[0].innerHTML=html;
		$('#more-infor').show();
	}
//	点击修改状态时判断是否选择了复选框函数
	function checkBox(){
		var str = "/LifeInGDUT/repair/changeState?"
		for(var index=0;index<checkboxes.length;index++){
			if(checkboxes[index].checked){
				var idInt=$(checkboxes[index]).parent().next().text();
				str = str +"ids="+idInt+"&";
			}
		}
		str =str.substring(0,str.length-1)
				$.get(str,
				  function(data,status){
					if(data=="success"){
						location.reload();
					}
				});
	}
//	将复选框改为全选或全不选函数
	function changeBoxCheck(){
		if(checkAllBox.checked){
			for(var index=0;index<checkboxes.length;index++){
				checkboxes[index].checked=true;
			}
		}
		else if(!checkAllBox.checked){
			for(var index=0;index<checkboxes.length;index++){
				checkboxes[index].checked=false;
			}
		}
	}