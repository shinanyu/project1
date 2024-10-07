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
<link href="${path}/css/noticeboard/noticelist.css" rel="stylesheet">
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
		var content = "<input type='button' value='글작성' onClick=\"location.href='/insertform'\">";
		$("#manager").html(content);
	}
});

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
	<div class="container" align="center">
		<h1 class="text-primary">공지사항</h1>
		<table class="board-table">
			<tr>
				<th class="th-title">제목</th>
				<th class="th-date">등록일</th>
			</tr>
			<c:if test="${empty selectlist}">
				<tr>
					<td colspan="5">데이터가 없습니다</td>
				</tr>
			</c:if>
			<c:if test="${not empty selectlist}">
				<c:forEach var="n" items="${selectlist }">
					<tr>
						<th><a href="${path }/noticedetail/${pp.currentPage}/${n.nb_no}">${n.nb_title}</a></th>
						<td><fmt:formatDate value="${n.nb_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</tr>
				</c:forEach>
			</c:if>
		</table>
		<ul class="paging">
			<c:if test="${pp.startPage < pp.currentPage }">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.startPage}">&lt;&lt;</a></li>
			</c:if>
			<c:if test="${pp.startPage < pp.currentPage }">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.currentPage - 1}">&lt;</a></li>
			</c:if>
			<c:if test="${pp.startPage > pp.pagePerBlk }">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.startPage - 1}">이전</a></li>
			</c:if>
			<c:forEach var="i" begin="${pp.startPage}" end="${pp.endPage}">
				<li class="active"<c:if test="${pp.currentPage==i}"></c:if>><a class="a" 
					href="${path }/noticelist/${i}">[${i}]</a></li>
			</c:forEach>
			<c:if test="${pp.endPage < pp.totalPage}">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.endPage + 1}">다음</a></li>
			</c:if>
			<c:if test="${pp.totalPage > pp.currentPage }">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.currentPage + 1}">&gt;</a></li>
			</c:if>
			<c:if test="${pp.totalPage > pp.currentPage }">
				<li class="active"><a class="a" href="${path }/noticelist/${pp.totalPage}">&gt;&gt;</a></li>
			</c:if>
		</ul>
	</div>
<div id="manager"></div>
</body>
</html>