<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/reviewboard/reviewlist.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>후기 게시판</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
//인증 아이디정보
<sec:authentication property="principal" var="mem_id" />
var mem_id = "${mem_id}";

// 넘어오는 page값 할당
var page = ${page};

$(document).ready(function() {
	rbList(page);
});

// 리스트 데이터 요청
function rbList(page){
	$("#rbList").show();
	$("#rbPagination").show();
	$("#searchList").show();
	$("#writeform").hide();
	
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/rb/reviewBoard/"+page,
		success : function(result){
			var no = result.listcount - (result.page - 1) * 10;    // 화면출력 번호 limit 10
			content = "<table class='board-table'><tr><th class='no'>번호</th><th class='subject'>제목</th><th class='writer'>작성자</th><th class='Date'>날짜</th><th class='hit'>조회수</th><th class='point'>추천수</th></tr>"
			// 데이터가 없을 때 처리
			if(result.rblist.length === 0){
				content += "<tr><td colspan='6'>등록된 게시물이 없습니다</td></tr>";
			}else{
				$.each(result.rblist,function(index,item){
				content+= "<tr><td>"+no--+"</td>"
				content+= "<td><a href='${pageContext.request.contextPath}/rb/rbdetail/"+item.rb_no+"/"+page+"'>"+item.rb_subject+"</a></td>"
				content+= "<td>"+item.rb_writer+"</td>";
				
				// 날짜변환
				var date = new Date(item.rb_date);
	            var formattedDate = date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) +
	                " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
				
				content+= "<td>"+formattedDate+"</td>"
				content+= "<td>"+item.rb_hit+"</td>"
				content+= "<td class='last-td'>"+item.rb_point+"</td></tr>";
				});
			}
			content+= "</table>";
			
			$("#rbList").html(content);
			
			pagination(result);
		}
	});
}

// 10보다 작은 숫자에 0을 추가하는 함수
function addZero(number) {
    return number < 10 ? "0" + number : number;
}

// 페이지 처리
function pagination(result){
	var pagination = "";
	
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:reviewList(1)'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:reviewList("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:reviewList("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
		if(result.page == i){
			pagination += "["+i+"]";
		}else{
			pagination += "<a href='javascript:reviewList("+i+")'>["+i+"]</a>";
		}
	}
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:reviewList("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:reviewList("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:reviewList("+result.pageCount+")'> >> </a>"
	}
	$("#rbPagination").html(pagination);
}

function reviewList(page){
	location.href="${pageContext.request.contextPath}/rb/reviewList/"+page
}


// 글 작성 폼
function writeForm(){
	$("#rbList").hide();
	$("#rbPagination").hide();
	$("#searchList").hide();
	$("#writeform").show();
	
	var content = "<form><table>"
		content += "<tr><td>작성자</td></tr>"
		content += "<td><input type=text id=writer value='"+mem_id+"' readonly></td></tr>"
		content += "<tr><td>제목</td></tr>"
	    content += "<tr><td><input type=text id=subject style='width: 900px; height: 25px;'></td></tr>"
	    content += "<tr><td>내용</td></tr>"
	    content += "<tr><td><textarea id=content rows='5' cols='30' name='nb_content' required='required' style='width: 900px; height: 450px;'></textarea></td></tr>"
	    content += "<tr><td colspan='2' align='center'>"
	    content += "<input type='button' value='글작성' onClick='boardwrite()'>"
	    content += "<input type='button' value='취소' onclick='reviewList(1)'></td></tr></table></form>"
	
	$("#writeform").html(content);	
}

// 글작성
function boardwrite(){

	var formData = {
	        rb_writer: $("#writer").val(),
	        rb_subject: $("#subject").val(),
	        rb_content: $("#content").val()
	    };	// JSON 형식이긴 하지만 완전한 JOSN 타입의 데이터는 아님
	
	$.ajax({
		type : "POST",
		url : "${pageContext.request.contextPath}/rb/boardwrite",
		contentType: 'application/json',  	// 데이터 타입을 JSON으로 설정
		data : JSON.stringify(formData),  	// 데이터를 JSON 문자열로 변환하여 전송
		success : function(result){
			if(result == 1){
				alert("글작성 성공");
			}else{
				alert("글작성 실패");
			}
			// 글작성후 목록 페이지로 이동
			rbList(1);
		}	
	});	
}

// 검색 기능 - 검색페이지로 이동 후, 데이터 작업
function search(){
	var page = 1;
	var sel = $("#sel").val();
	var find = $("#find").val();
	
	if(sel == ""){
		alert("검색할 항목을 선택 하세요");
		return false;
	}
	if(find == ""){
		alert("검색어를 입력 하세요");
		$("#find").focus();
		return false;
	}
	location.href="${pageContext.request.contextPath}/rb/reviewSearch/"+page+"/"+sel+"/"+find;
}

</script>
<style>
#rbPagination a {
    text-decoration: none;
}
</style>
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
	<h1><a href="${path}/main"><img src="${path}/image/airplane.png" alt="로고" style="weight: 100px; height:100px;"></a></h1>
</div>
<div class="topMenu" >
	<ul class="menu">
		<li class="li"><a class="menuLink" href="${path}/noticelist">공지사항</a></li>
		<li class="li"><a class="menuLink" href="${path}/ctlist">캠핑장/관광지 검색</a></li>
		<li class="li"><a class="menuLink" href="${path}/rb/reviewList">후기게시판</a></li>
	</ul>
</div>
<h1 class="text-primary" style='text-align:center'>후기게시판</h1>
<div id="rbList" class="container"></div>
<div id="rbPagination" style='text-align:center'></div>
<div id="searchList" align="center">
	<select id="sel" name="sel">
		<option value="">검색</option>
		<option value="rb_writer">작성자</option>
		<option value="rb_subject">제목</option>
		<option value="rb_content">내용</option>
	</select>
	<input type="text" name="find" id="find">
	<input type="button" value="검색" onClick="search()">
	<sec:authorize access="isAuthenticated()">
		<input type='button' value='글작성' onClick='writeForm()'>
	</sec:authorize>
</div>
<div id="writeform" align="center"></div>

</body>
</html>