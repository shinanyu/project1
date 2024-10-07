<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function back(){
     history.back();
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
	<div class="container" align="center">
		<h2 class="text-primary">공지글 작성</h2>
		<form action="${path}/insert" method="post">
			<table class="table table-striped">
				<tr>
					<td>제목</td>
				</tr>
				<tr>
					<td><input type="text" name="nb_title" required="required" style="width: 900px; height: 25px;"></td>
				</tr>
				<tr>
					<td>내용</td>
				</tr>
				<tr>
					<td><textarea rows="5" cols="30" name="nb_content"	required="required" style="width: 900px; height: 450px;"></textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<input type="submit" value="글작성">
					<input type="button" value="취소" onclick="back()">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>