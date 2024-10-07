<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix ="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/noticeboard/noticedetail.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
//인증 아이디정보
<sec:authentication property="principal" var="mem_id" />
var mem_id = "${mem_id}";

$(document).ready(function() {
	if(mem_id == "manager"){
		var content = "<input type='button' value='삭제' onclick='deleteform()' /><input type='button' value='수정' onclick='updateform()' />";
		$("#manager").html(content);
	}
});

function deleteform(){
	var confirmation = confirm("게시글을 삭제하시겠습니까?");
	if(confirmation){
		location.href="${path}/delete/${noticedetail.nb_no}"
	}else{
	}
}
function updateform(){
	location.href="${path}/updateform/${noticedetail.nb_no}"
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
<div class="contaner">
	<div class="noticelist">
		<input type='button' value='목록' onClick="location.href='${path}/noticelist/${pageNum}'">
	</div>
	<div class="title-row">
		<div class="title">
			${noticedetail.nb_title}
		</div>
	</div>
	<div class="date">
		<fmt:formatDate value="${noticedetail.nb_date}" pattern="yyyy-MM-dd HH:mm:ss" />
	</div>
	<div class="content">
		${noticedetail.nb_content}
	</div>
	<div id="manager"></div>
</div>

</body>
</html>