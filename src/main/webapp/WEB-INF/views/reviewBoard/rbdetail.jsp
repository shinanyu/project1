<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/reviewboard/reviewdetail.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>후기 상세페이지</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
//인증 아이디정보
<sec:authentication property="principal" var="mem_id" />
var mem_id = "${mem_id}";

// 넘어오는 변수들 할당 
var rb_no = ${rb_no};
var page = ${page};

$(document).ready(function() {
	rbDetail(rb_no,page);
});

// 상세정보 출력
function rbDetail(rb_no,page){
	$("#rbPage").show();
	$("#updateform").hide();
	$("#deleteform").hide();
	
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/rb/reviewDetail/"+rb_no+"/"+page,
		success : function(result){
			// 날짜변환
			var date = new Date(result.reviewDTO.rb_date);
            var formattedDate = date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) +
                " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
			
			var	content = "<div class='title-row'><div class='title'>"+result.reviewDTO.rb_subject+"</div></div>"
				content += "<div class='writer-row'><div class='writer'>"+result.reviewDTO.rb_writer+"</div><div class='date'>추천수 "+result.reviewDTO.rb_point+" | 조회수"+result.reviewDTO.rb_hit+" | 작성일 "+formattedDate+"</div></div>"
			    content += "<div class='content'>"+result.content+"</div>"							
			    // 세션으로 넘어온 pk값과 rb_writer 으로 확인과정
			    if(mem_id === result.reviewDTO.rb_writer || mem_id == "manager"){
				    content += "<div>"							
				    content += "<input type=button value=목록  onclick='reviewListPage("+page+")'>"							
				    content += "<input type=button value=수정  onclick='updateform("+rb_no+","+page+")'>"							
				    content += "<input type=button value=삭제  onclick='rbdelete("+rb_no+","+page+")'>"							
				    content += "</div>"	
			    }else{
				    content += "<input type=button value=목록  onclick='reviewListPage("+page+")'>"							
				    content += "<div></div>"							
			    }
			$("#rbPage").html(content);
		}		
	});	
}

// 목록 돌아가기
function reviewListPage(page){
	location.href="${pageContext.request.contextPath}/rb/reviewList/"+page;
}

// 수정폼
function updateform(rb_no, page){
	$("#rbPage").hide();
	$("#updateform").show();
	
	$.ajax({
		type : "GET",
		url : "${pageContext.request.contextPath}/rb/updateform/"+rb_no+"/"+page,
		success : function(result){
			var date = new Date(result.reviewDTO.rb_date);
            var formattedDate = date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) +
                " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
			
            var content = "<form><table>"
            	content += "<tr><td>제목</td></tr>"
            	content += "<tr><td colspan='2'><input type=text id='subject' required='required' style='width: 900px; height: 25px;' value="+result.reviewDTO.rb_subject+"></td></tr>"
            	content += "<tr><td>내용</td></tr>"
            	content += "<tr><td><textarea rows='5' cols='30' id=content	required='required' style='width: 900px; height: 450px;'>"+result.reviewDTO.rb_content+"</textarea></td></tr>"
            	content += "<tr><td colspan='2' align='center'><input type=button value=수정 onClick='rbupdate("+rb_no+","+page+")'>"
            	content += "<input type=button value=목록  onclick='reviewListPage("+page+")'></td></tr></table></form>"
			    //별점
			
			$("#updateform").html(content);
		}			
	});		
	
}

// 글수정
function rbupdate(rb_no,page){
	
	var formData = {
	        rb_no: rb_no, 			
	        rb_subject: $("#subject").val(),
	        rb_content: $("#content").val()
	    };
	
	// 수정 전에 확인 알림 창 표시
	if(confirm("수정하시겠습니까?")){
		$.ajax({
			type : "PUT",
			url : "${pageContext.request.contextPath}/rb/boardupdate",
			contentType: 'application/json',
			// 데이터를 JSON 문자열로 변환하여 전송
			data : JSON.stringify(formData),
			success : function(result){
				if(result == 1){
					alert("수정하셨습니다");
				}else{
					alert("수정실패");
				}
				// 글수정후 다시 돌아오기
				rbDetail(rb_no,page);
			}	
		});	
	}else{
		// 취소 클릭 시 아무런 작업 수행x
	}
	
}

//글삭제
function rbdelete(rb_no,page){

	if(confirm("삭제하시겠습니까?")){
		$.ajax({
			type : "DELETE",
			url : "${pageContext.request.contextPath}/rb/boarddelete/"+rb_no,
			success : function(result){
				if(result == 1){
					alert("삭제성공");
				}else{
					alert("삭제실패");
				}
				// 글삭제후 목록 페이지로 이동
				reviewListPage(page);
			}	
		});
	}else{
		// 취소 클릭 시 아무런 작업 수행x
	}
}

//10보다 작은 숫자에 0을 추가하는 함수
function addZero(number) {
    return number < 10 ? "0" + number : number;
}

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
	<h1><a href="${path}/main"><img src="${path}/image/airplane.png" alt="로고" style="weight: 100px; height:100px;"></a></h1>
</div>
<div class="topMenu" >
	<ul class="menu">
		<li class="li"><a class="menuLink" href="${path}/noticelist">공지사항</a></li>
		<li class="li"><a class="menuLink" href="${path}/ctlist">캠핑장/관광지 검색</a></li>
		<li class="li"><a class="menuLink" href="${path}/rb/reviewList">후기게시판</a></li>
	</ul>
</div>

<div id="rbPage"></div>
<div id="updateform"></div>
<div id="deleteform"></div>

</body>
</html>