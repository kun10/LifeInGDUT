$(document).ready(function(){
	var main=document.getElementById('check-main-part');
	main.onclick=function(e){
		e=e||window.event;     
		var el=e.srcElement;
		var el=e.target;		//兼容火狐
		switch(el.className){
			//点击分页页数按钮
			case 'pagehref':judgePage(el);break;
		}
	}
	//点击复选框全选事件
	$("#checkallbox").click(function(){
		changeBoxCheck();
	})
	$(".checkbox").click(function(){
		changeboxCheck();
	})
	//关闭更多详细信息窗口事件
	$(".more-infor-button").click(function(){
		$("#more-infor").hide();
		$("#mask").hide();
	})
})
//body加载相关函数
	var totalpage,pagesize,cpage,count,curcount,outstr;
	var checkAllBox=document.getElementById('checkallbox');
	var checkboxes=document.getElementsByName('checkbox');
	var allOrder=8;
	pagesize = 10; 		//总共可以显示多少页
	outstr = ""; 		//字符串，用来存放生成的页码
	//设置页数函数
	function pagesNumberSet(nowpage,allpage){
		cpage=nowpage;
		totalpage=allpage;
		setpage();
	}
    //创建页数函数
	function judgePage(el){
		var $el=$(el);
		var elText=$el.text();
		if(!isNaN(elText))	gotopage(elText);
		else if(elText=="下一页"){
			if(cpage!=totalpage)	gotopage(parseInt(cpage)+1);
		}
		else if(elText=="上一页"){
			if(cpage!=1)	gotopage(parseInt(cpage)-1);
		}
	}
	function gotopage(pages){   
	    cpage = pages;        //把页面计数定位到第几页
	    setpage(); 
	} 
	function setpage(){ 
		outstr = outstr + "<a href='#' class='pagehref'>上一页</a>"; 
	    if(totalpage<=10){        //总页数小于十页 
	        for (count=1;count<=totalpage;count++) setOutstr();
	    } 
	    if(totalpage>10){        //总页数大于十页 
	        if(parseInt((cpage-1)/10) == 0){            
	            for (count=1;count<=10;count++) setOutstr(); 
	        } 
	        else if(parseInt((cpage-1)/10) == parseInt(totalpage/10)) {     
	            for (count=parseInt(totalpage/10)*10+1;count<=totalpage;count++) setOutstr();      
	        } 
	        else{     
	            for (count=parseInt((cpage-1)/10)*10+1;count<=parseInt((cpage-1)/10)*10+10;count++)		setOutstr();
	        } 
	    }     
	    outstr = outstr + "<a href='#' class='pagehref'>下一页</a>"; 
	    document.getElementById("setpage").innerHTML = "<div id='setpage'><span id='info'>共"+totalpage+"页|第"+cpage+"页<\/span>" + outstr + "<\/div>"; 
	    outstr = ""; 
	}
	//根据页码对字符串outstr进行拼接处理
	function setOutstr(){
		if(count!=cpage) 
			outstr = outstr + "<a href='#' class='pagehref'>"+count+"</a>"; 
	    else
	        outstr = outstr + "<span class='current' >"+count+"</span>";  
	}
	//将复选框改为全选或全不选函数
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
	function changeboxCheck(){
		var sum=0;
		for(var index=0;index<checkboxes.length;index++){
			if(checkboxes[index].checked==true){
				sum++;
			}
		}
		if(sum==allOrder)	checkAllBox.checked=true;
		else	checkAllBox.checked=false;
	}
	//显示更多详细信息函数
	function inforWrite(id,name,time,urls){
		$("#mask").css('height',$(document).height()).show();
		$('.more-infor-main').empty();
		var html='<p>申请社团名称:<span id="more-infor-name">'+name+'</span></p>'+
					'<p>申请人学号:<span id="more-infor-id">'+id+'</span></p>'+
					'<p>申请时间:<span id="more-infor-id">'+time+'</span></p>'+
					'<p>相关照片:</p>'+
					'<p><img src="'+urls+'"/></p>';
		$('.more-infor-main')[0].innerHTML=html;
		$('#more-infor').show();
	}
	//审核通过按钮事件
	$(".check-ok").click(function(){
		var str = "/LifeInGDUT/preTeam/changeToOk?"
		for(var index=0;index<checkboxes.length;index++){
			if(checkboxes[index].checked){
				var idInt=$(checkboxes[index]).parent().next().text();
				str = str +"names="+idInt+"&";
			}
		}
		str =str.substring(0,str.length-1)
		$.get(str,
		  function(data,status){
			if(data=="success"){
				location.reload();
			}
		});
	})
	//审核不通过按钮事件
	$(".check-not-ok").click(function(){
		var str = "/LifeInGDUT/preTeam/changeToNotOk?"
		for(var index=0;index<checkboxes.length;index++){
			if(checkboxes[index].checked){
				var idInt=$(checkboxes[index]).parent().next().text();
				str = str +"names="+idInt+"&";	
			}
		}
		str =str.substring(0,str.length-1)
		$.get(str,
		  function(data,status){
			if(data=="success"){
				location.reload();
			}
		});
	})