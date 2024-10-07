<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/member/login.css">
</head>
<body>
<div align="right">
   <input type="button" onclick = "location.href = '${path}/public/login'" value="로그인">
   <input type="button" onclick = "location.href = '${path}/public/Register'" value="회원가입">
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
<div class="login" align="center">
<form method='post' action="loginAttemptSecurity">
	<table>
		<tr>
			<td>아이디</td>
			<td><input type="text" name="id" required="required" style="width: 400px; height: 25px;" autofocus></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="pwd" required="required" style="width: 400px; height: 25px;"></td>
		</tr>
		<tr>
			<td colspan='2' style="text-align: center;"><input type="submit" value="로그인"> <input type="button" onClick="${path}/public/Register" value="회원가입"></td>
		</tr>
	</table>
</form>
</div>
</body>
</html>
