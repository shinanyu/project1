<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>검색 장소 목록</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<style>
    	
	    .table-set-container {	
/* 	    margin-left: 350px; */
/* 	    margin-right: 200px; */
	    display: flex;
	    justify-content: flex-start;
	    width: 80%;
		}
		
		.table-set {
	    margin-bottom: 20px; /* 테이블 세트 사이의 간격을 설정할 수 있습니다. */
		}
	
		.table {
	    border: none; /* 테이블의 테두리 스타일을 지정합니다. */
		text-align: center;
	    border-collapse: collapse; /* 테이블의 셀 경계를 합칩니다. */
	    width: 300px; /* 각 테이블의 너비를 설정할 수 있습니다. */
	    table-layout: fixed;
	    margin-left: 50px; /* 테이블 사이의 간격을 설정할 수 있습니다. */
	    margin-right: 50px; /* 테이블 사이의 간격을 설정할 수 있습니다. */
	    margin-bottom: 30px;
		}
		
		a:link {text-decoration:none}
		
		a#subject {color: black; padding: 8px 16px; text-decoration: none;}  
		
</style>
<script>  
    	
//지역1 option
function region1(sort){
    $.ajax({
           type: "get",
           url: "${pageContext.request.contextPath}/regid1_list/" + sort,
           success: function(result) { 	// result변수에는 캠핑장 또는 관광지 지역1이름 배열이 들어감
        var content = "<option value=''>지역선택</option>";
        for (var i = 0; i < result.length; i++) {
                   content += "<option value='" + result[i] + "'>" + result[i] + "</option>";
        }
               $("#region1").html(content);
        }
        });
}

//지역2 option
function region2(selected_region1, sort) {
    $.ajax({
        type: "get",
        url: "${pageContext.request.contextPath}/regid2_list/" + selected_region1 + "/" + sort,    
        success: function(result) { 	//result변수에는 sort값에 따라 캠핑장 / 관광지 지역2코드가 옴
            var content = "<option value=''>시/군/구 선택</option>";
            for (var i = 0; i < result.length; i++) {
                content += "<option value='" + result[i] + "'>" + result[i] + "</option>";
            }
            $("#region2").html(content); 
        }
    });
}

//camp데이터 리스트 > camplist() 내에 들어감
function generateTableCamp(item, page) {
    var content = "";
    content += "<table class='table' align='center'>";
    content += "<tr><td><img src='" + item.camp_image +"' style='width: 300px; height: 200px;'></td></tr>";//이미지 한줄 더들어감
    content += "<tr><td style='background-color: skyblue; font-weight: bold;'>이름</td></tr>";
    content += "<tr><td><a href='cdetail?camp_no="+item.camp_no+"&page="+page+"'>" + item.camp_name + "</a></td></tr>";
    content += "<tr><td style='background-color: skyblue; font-weight: bold;'>주소</td></tr>";
    content += "<tr><td>" + item.camp_addr + "</td></tr>";
    content += "</table>";
    return content;
}

//tour데이터 리스트 > tourlist() 내에 들어감
function generateTableTour(item, page) {
    var content = "";
    content += "<table class='table' width='700' align='center' style= 'border: 1px solid black;'>";
    content += "<tr><td style='background-color: skyblue; font-weight: bold;'>이름</td></tr>";
    content += "<tr><td><a href='tdetail?tour_no="+item.tour_no+"&page="+page+"'>" + item.tour_name + "</td></tr>";
    content += "<tr><td style='background-color: skyblue; font-weight: bold;'>주소</td></tr>";
    content += "<tr><td>" + item.tour_addr + "</td></tr>";
    content += "</table>";
    return content;
}


//camp 데이터 뿌리기 > 이것만 써주면 됨
function camplist(result){
	
	var content = "";
	for (var i = 0; i < Math.min(3, result.camplist.length); i++) {
	    content += generateTableCamp(result.camplist[i], result.page);
	}
	$("#table1").html(content);
	
	var content1 = "";
	for(var i = 3; i < Math.min(6, result.camplist.length); i++){
		content1 += generateTableCamp(result.camplist[i], result.page);
	}
	$("#table2").html(content1);    
	
	var content2 = "";
	for(var i = 6; i < result.camplist.length; i++){
		content2 += generateTableCamp(result.camplist[i], result.page);
	}
	$("#table3").html(content2);
	
}

//tour 데이터 뿌리기 > 이것만 써주면 됨
function tourlist(result){
	
	var content = "";
    for (var i = 0; i < Math.min(3, result.tourlist.length); i++) {
        content += generateTableTour(result.tourlist[i], result.page);
    }
    $("#table4").html(content);
    
    var content1 = "";
    for(var i = 3; i < Math.min(6, result.tourlist.length); i++){
    	content1 += generateTableTour(result.tourlist[i], result.page);
    }
    $("#table5").html(content1);    
    
    var content2 = "";
    for(var i = 6; i < result.tourlist.length; i++){
    	content2 += generateTableTour(result.tourlist[i], result.page);
    }
    $("#table6").html(content2);
    
    
}

//페이징 처리 > 캠, 관
function pagination(result){

	var pagination = "<div style='text-align:center'>";
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:check(1)'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:check("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:check("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
	     pagination += "<span class='page-item " + (i === result.page ? "active" : "") + "'>";
	     pagination += "<a id=subject href='javascript:check(" + i + ")'>" + i + "</a></span>";
	 }
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:check("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check("+result.pageCount+")'> >> </a>"
	}
	 pagination += "</div>";
	 $("#pagination").html(pagination); 
}	

//페이징 처리 > 지역1
function pagination1(result){

	var pagination = "<div style='text-align:center'>";
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:check1(1)'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:check1("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:check1("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
	     pagination += "<span class='page-item " + (i === result.page ? "active" : "") + "'>";
	     pagination += "<a id=subject href='javascript:check1(" + i + ")'>" + i + "</a></span>";
	 }
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check1("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:check1("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check1("+result.pageCount+")'> >> </a>"
	}
	 pagination += "</div>";
	 $("#pagination").html(pagination); 
}	

//페이징 처리 > 지역2
function pagination2(result){

	var pagination = "<div style='text-align:center'>";
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:check2(1)'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:check2("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:check2("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
	     pagination += "<span class='page-item " + (i === result.page ? "active" : "") + "'>";
	     pagination += "<a id=subject href='javascript:check2(" + i + ")'>" + i + "</a></span>";
	 }
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check2("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:check2("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:check2("+result.pageCount+")'> >> </a>"
	}
	 pagination += "</div>";
	 $("#pagination").html(pagination); 
}	

//페이징 처리 > 이름검색
function pagination3(result){

	var pagination = "<div style='text-align:center'>";
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:search'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:search("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:search("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
	     pagination += "<span class='page-item " + (i === result.page ? "active" : "") + "'>";
	     pagination += "<a id=subject href='javascript:search(" + i + ")'>" + i + "</a></span>";
	 }
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:search("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:search("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:search("+result.pageCount+")'> >> </a>"
	}
	 pagination += "</div>";
	 $("#pagination").html(pagination); 
}	

	//시작
	$(document).ready(function(){
	    check(1);
	});

	// 캠/관 선택 > 캠핑장, 관광지 데이터 뿌림
	function check(page){
	            var sort = $("#sort").val();
	            // 캠핑장 선택
	            if ($("#sort").val() == "camping") {
	                $("#tour").hide();
	                $("#camp").show();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/camplist/"+page,
	                    success: function(result){	//map객체로 받음 (camplist + 페이징 관련)
	                 
	                		camplist(result);	
	                		pagination(result);
	            	}//function
	             });//ajax
	             
	            //관광지 선택
	            }else if ($("#sort").val() == "tour") {  
	                $("#camp").hide();
	                $("#tour").show();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/tourlist/"+page,
	                    success: function(result){	//map객체로 받음 (tourlist + 페이징 관련)
	                    
	 						tourlist(result);
	 						pagination(result);
	            		}//function
	            	});//ajax
	        	}//else if
	        	
	            //지역1 option
	            region1(sort);
	}//check

	function check1(page) {
		
		var sort = $("#sort").val();
	    var selected_region1 = $("#region1").val();
	    
	    // 지역2 옵션 뿌림
	    region2(selected_region1,sort);
	    
	    //지역1에 맞는 data뿌림
	    
	        	// 캠핑장 선택
	            if ($("#sort").val() == "camping") {
	                $("#camp").show();
	                $("#tour").hide();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/c1_list/"+selected_region1+"/"+page,
	                    success: function(result){	//map객체로 받음 (camplist + 페이징 관련)
	                    
	                	camplist(result);
	                	pagination1(result)
	            	}//function
	             });//ajax
	            
	            //관광지 선택
	            }else if ($("#sort").val() == "tour") {  
	                $("#tour").show();
	                $("#camp").hide();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/t1_list/"+selected_region1+"/"+page,
	                    success: function(result){	//map객체로 받음 (tourlist + 페이징 관련)
	                    
	 				tourlist(result);
	 				pagination1(result)
	            		}//function
	            	});//ajax
				}
	    		
	}//check1()

	// 지역2 > data뿌림
	function check2(page){
		
		var sort = $("#sort").val();
		var selected_region2 = $("#region2").val();
		
	        	if ($("#sort").val() === "camping") {
	                $("#camp").show();
	                $("#tour").hide();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/c2_list/"+selected_region2+"/"+page,
	                    success: function(result){	//map객체로 받음 (camplist)
	                		camplist(result);
	                    	pagination2(result);
	                    }
	                });    
	            }
	            //관광지 선택
	            else if ($("#sort").val() === "tour") {  
	                $("#tour").show();
	                $("#camp").hide();
	                
	                $.ajax({
	                    type : "get",
	                    url: "${pageContext.request.contextPath}/t2_list/"+selected_region2+"/"+page,
	                    success: function(result){	//map객체로 받음 (tourlist)
	                		tourlist(result);
	            			pagination2(result);
	                    }
	                });   
	            }
	}//check2()	
 	
	function search(page){
		
		var name = $("#name").val()
		
		if( $("#name").val() ){	
			
			if($("#sort").val() == "camping"){
				$.ajax({
					type : "get",
		            url: "${pageContext.request.contextPath}/c_namelist/"+name+"/"+page,
		            success: function(result){	//map객체로 받음 (camplist)
		        		camplist(result);
		    			pagination3(result);
		            }
				});
				
			}else if($("#sort").val() == "tour"){
				$.ajax({
					type : "get",
		            url: "${pageContext.request.contextPath}/t_namelist/"+name+	"/"+page,
		            success: function(result){	//map객체로 받음 (tourlist)
		        		tourlist(result);
		    			pagination3(result);
		            }
				});
			}
		}else{
			alert("캠핑장 또는 관광지 명을 입력하세요.");
		}
			
	}//search()


</script>
</head>
<body>
<div align="right">
<sec:authorize access="isAnonymous()">
   <input type="button" onclick = "location.href = '${path}/public/login'" value="로그인">
   <input type="button" onclick = "location.href = '${path}/public/Register'" value="회원가입">
</sec:authorize>

<sec:authorize access="isAuthenticated()">
   <input type="button" onclick = "location.href = '${path}/logout'" value="로그아웃">
   <input type="button" onclick = "location.href = '${path}/MyPage'" value="마이페이지">
</sec:authorize>
</div>
<div>
	<h1><a href="${path}/main"><img src="image/airplane.png" alt="로고" style="weight: 100px; height: 100px;"></a></h1>
</div>
<div class="topMenu" >
	<ul class="menu">
		<li class="li"><a class="menuLink" href="${path}/noticelist">공지사항</a></li>
		<li class="li"><a class="menuLink" href="${path}/ctlist">캠핑장/관광지 검색</a></li>
		<li class="li"><a class="menuLink" href="${path}/rb/reviewList">후기게시판</a></li>
	</ul>
</div>

   <div id="section" align="center">

       <div>
           <table>
               <tr>
                   <td>
                       <!-- 캠핑장 / 관광지 선택 -->
                    <select id="sort" onChange="check(1)">
                        <option value="camping" selected>캠핑장</option>
                        <option value="tour">관광지</option>
                    </select>
                </td>
                <td>
                     <!-- 지역1 선택 -->
                    <select id="region1" onChange="check1(1)"></select>
                </td>
                <td>
                    <!-- 지역2 선택  -->
                    <select id="region2" onChange="check2(1)"></select>
                </td>
                    <!-- 이름 입력 -->
                <td><input type="text" placeholder="캠핑장/관광지 명 입력" value="" id="name" name="name"/></td>
                    <!-- 버튼 -->
                <td><input type="button" value="확인" onClick="search(1)"/></td>
             </tr>
        </table>
    <div>
    <div id="camp" class="table-set-container"> 
        <!-- 테이블을 3 x 4 개씩 출력되도록 만듦 -->
        <div class="table-set" id="table1"></div>
        <div class="table-set" id="table2"></div>
        <div class="table-set" id="table3"></div>
        
    </div>          
    <div id="tour" class="table-set-container">
 	    <!-- 테이블을 3 x 4 개씩 출력되도록 만듦 -->
        <div class="table-set" id="table4"></div>
        <div class="table-set" id="table5"></div>
        <div class="table-set" id="table6"></div>
    </div>
<!-- 페이징 처리 -->
</div>  <!-- id=section <div> -->
<div id="pagination"></div>
    
</body>
</html>