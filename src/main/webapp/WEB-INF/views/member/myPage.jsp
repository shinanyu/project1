<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/member/login.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>일반회원 myPage</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
// contextPath 길어서 변수 할당 
var contextPath = "${pageContext.request.contextPath}";

<sec:authentication property="principal" var="mem_id" />
var mem_id = "${mem_id}";

$(document).ready(function() {
	if(mem_id == "manager"){
		getUserList(1);
	}else{
		getUpdate(mem_id);
	}
});

function getUpdate(mem_id){
	$.ajax({
		type : "GET",
		url : contextPath+"/updateForm/"+mem_id,
		success : function(result){
			// 서버로부터 받은 회원 정보(result)를 폼에 채우기
            $('#join_name').val(result.editm.mem_name);
            $('#join_phone2').val(result.no2);
            $('#join_phone3').val(result.no3);
            $('#join_mailid').val(result.mailid);
            $('#join_maildomain').val(result.mail_list);
            $('#join_mail_list').val(result.mail_list);
            
            var no1_value = result.no1;
		}
	});
}

function getUserList(page){
	$("#edit").hide();
	
	$.ajax({
		type : "GET",
		url : contextPath+"/getUserList/"+page,
		success : function(result){
			var content = "<table style='text-align: center;'><tr><th>아이디</th><th>경고추가</th><th>회원삭제</th></tr>"
			$.each(result.mlist,function(index,item){
				content+= "<tr><td>"+item.mem_id+"</td>"
				content+= "<td>"+item.warningstack+" <input type='button' onClick=\"addWarning('"+item.mem_id+"',"+page+")\" value='경고' /></td>"
				content+= "<td><input type='button' onClick=\"deleteUser('"+item.mem_id+"',"+page+")\" value='삭제' /></td></tr>"
			});
			content+= "</table>"
			
			$("#userList").html(content);
			
			pagination(result);
		}
	});
}

// 관리자가 탈퇴시키기
function deleteUser(memid,page){
	// 삭제 전 확인
	var confirmation = confirm("회원을 삭제하시겠습니까?");
	if(confirmation){
		$.ajax({
			type : "GET",
			url : contextPath+"/deleteUser/"+memid,
			success : function(result){
				alert("회원 "+memid+"를 삭제했습니다.");
				getUserList(page);
			}
		});
	}else{
	}
}
// 관리자가 경고주기
function addWarning(memid,page){
	// 경고 전 확인
	var confirmation = confirm("경고를 추가하시겠습니까?");
	if(confirmation){
		$.ajax({
			type : "GET",
			url : contextPath+"/addWarning/"+memid,
			success : function(result){
				alert("회원 "+memid+"의 경고를 추가했습니다.");
				getUserList(page);
			}
		});
	}else{
	}
}
// 사용자 탈퇴하기
function deleteMember(){
	location.href="deleteMember";
}
// 페이지 처리
function pagination(result){
	var pagination = "";
	
	// 1페이지로 이동
	if(result.page > 1){
		pagination += "<a href='javascript:getUserList(1)'> << </a>";
	}
	// 이전 블록 페이지 이동
	if(result.page > 10){
		pagination += "<a href='javascript:getUserList("+(result.startPage-1)+")'> < </a>";
	}
	// 이전페이지 이동(-1)
	if(result.page > 1){
		pagination += "<a href='javascript:getUserList("+(result.page-1)+")'>[이전]</a>";
	}
	for (var i = result.startPage; i <= result.endPage; i++) {
		if(result.page == i){
			pagination += "["+i+"]";
		}else{
			pagination += "<a href='javascript:getUserList("+i+")'>["+i+"]</a>";
		}
	}
	// 다음페이지 이동(+1)
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:getUserList("+(result.page+1)+")'>[다음]</a>"
	}
	// 다음 블록 페이지 이동
	if(result.endPage < result.pageCount){
		pagination += "<a href='javascript:getUserList("+(result.endPage+1)+")'> > </a>"
	}
	// 끝 페이지 이동
	if(result.page < result.pageCount){
		pagination += "<a href='javascript:getUserList("+result.pageCount+")'> >> </a>"
	}
	$("#userPagination").html(pagination);
}

</script>
<script>
function edit_check(){
	if($.trim($("#join_pwd1").val())==""){
		 alert("회원비번을 입력하세요!");
		 $("#join_pwd1").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_pwd2").val())==""){
		 alert("회원비번확인을 입력하세요!");
		 $("#join_pwd2").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_pwd1").val()) != $.trim($("#join_pwd2").val())){
		 //!=같지않다 연산. 비번이 다를 경우
		 alert("비번이 다릅니다!");
		 $("#join_pwd1").val("");
		 $("#join_pwd2").val("");
		 $("#join_pwd1").focus();
		 return false;
	 }
	 if($.trim($("#join_name").val())==""){
		 alert("회원이름을 입력하세요!");
		 $("#join_name").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_phone2").val())==""){
		 alert("휴대전화번호를 입력하세요!");
		 $("#join_phone2").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_phone3").val())==""){
		 alert("휴대전화번호를 입력하세요!");
		 $("#join_phone3").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_mailid").val())==""){
		 alert("메일 아이디를 입력하세요!");
		 $("#join_mailid").val("").focus();
		 return false;
	 }
	 if($.trim($("#join_maildomain").val())==""){
		 alert("메일 주소를 입력하세요!");
		 $("#join_maildomain").val("").focus();
		 return false;
	 }	 	 
}
</script>
</head>
<body>
<div align="right">
   <input type="button" onclick = "location.href = '${path}/logout'" value="로그아웃">
   <input type="button" onclick = "location.href = '${path}/MyPage'" value="마이페이지">
</div>
<div>
	<h1><a href="${path}/main"><img src="image/airplane.png" alt="로고" style="weight: 100px; height:100px;"></a></h1>
</div>
<div class="topMenu" >
	<ul class="menu">
		<li class="li"><a class="menuLink" href="${path}/noticelist">공지사항</a></li>
		<li class="li"><a class="menuLink" href="${path}/ctlist">캠핑장/관광지 검색</a></li>
		<li class="li"><a class="menuLink" href="${path}/rb/reviewList">후기게시판</a></li>
	</ul>
</div>

<div id="userList" class="login" align="center"></div>
<div id="userPagination" align="center"></div>

<div id="edit" class="login" align="center">
 <h2 class="join_title">회원수정</h2>
 <form name="f" method="post" action="${pageContext.request.contextPath}/member_edit_ok.do"
 		onsubmit="return edit_check()" enctype="multipart/form-data">
  <input type="hidden" name="mem_id" value="${mem_id}">
  <table id="join_t">
   <tr>
    <th>회원아이디</th>
    <td id="mem_id">${mem_id}</td>
   </tr>
   
   <tr>
    <th>회원비번</th>
    <td>
     <input type="password" name="mem_pw" id="join_pwd1" size="14"
     		class="input_box" />
    </td>
   </tr>
   
   <tr>
    <th>회원비번확인</th>
    <td>
     <input type="password" name="join_pwd2" id="join_pwd2" size="14"
     		class="input_box" />
    </td>
   </tr>
   
   <tr>
    <th>회원이름</th>
    <td>
     <input name="mem_name" id="join_name" size="14" class="input_box"/>
    </td>
   </tr>
   
   <tr>
    <th>연락처</th>
    <td>
    <%@ include file="../include/phone_number.jsp" %>
    <select name="mem_phone1">
     <c:forEach var="p" items="${phone}" begin="0" end="5">
      <option value="${p}" <c:if test="${no1_value == p}">${'selected'}
         </c:if>>${p}
       </option>
     </c:forEach>
    </select>-
    <input name="mem_phone2" id="join_phone2" size="4" maxlength="4" class="input_box" />-
    <input name="mem_phone3" id="join_phone3" size="4" maxlength="4" class="input_box" />
    </td>
   </tr>
   
   <tr>
    <th>이메일</th>
    <td>
     <input name="join_mailid" id="join_mailid" size="10" 
     class="input_box" />@<input name="mail_list" 
     id="join_maildomain" size="20" class="input_box" />
    </td>
   </tr>
  </table>
  
  <div id="eidt_menu">
   <input type="submit" value="회원수정" class="input_button" />
   <input type="reset" value="수정취소" class="input_button" 
   		onclick="$('#join_pwd1').focus();" />
   <input type="button" value="회원탈퇴" onClick="deleteMember()" />
  </div>
 </form>
</div>

</body>
</html>