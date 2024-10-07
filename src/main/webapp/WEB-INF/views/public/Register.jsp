<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
<link rel="stylesheet" href="${path}/css/header.css">
<link rel="stylesheet" href="${path}/css/member/insertmember.css">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
function check(){
	 if($.trim($("#join_id").val())==""){
		 alert("회원아이디를 입력하세요!");
		 $("#join_id").val("").focus();
		 return false;
	 }
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

//아이디 중복 체크
function id_check(){
	$("#idcheck").hide();//idcheck span 아이디 영역을 숨긴다.
	var memid=$("#join_id").val();
	//1.입력글자 길이 체크
	if($.trim($("#join_id").val()).length < 4){
		var newtext='<font color="red">아이디는 4자 이상이어야 합니다.</font>';
		$("#idcheck").text('');
		$("#idcheck").show();
		$("#idcheck").append(newtext);//span 아이디 영역에 경고문자 추가
		$("#join_id").val("").focus();
		return false;
	};
	if($.trim($("#join_id").val()).length >12){
		var newtext='<font color="red">아이디는 12자 이하이어야 합니다.</font>';
		$("#idcheck").text('');
		$("#idcheck").show();
		$("#idcheck").append(newtext);
		$("#join_id").val("").focus();
		return false;
	};
	//입력아이디 유효성 검사
	if(!(validate_userid(memid))){
		var newtext='<font color="red">아이디는 영문소문자,숫자,_ 조합만 가능합니다.</font>';
		$("#idcheck").text('');		
		$("#idcheck").show();		
		$("#idcheck").append(newtext);
		$("#join_id").val("").focus();
		return false;
	};
	

	//아이디 중복확인
   $.ajax({
       type:"POST",
       url:"${pageContext.request.contextPath}/member_idcheck.do",
       data: {"memid":memid},        
       success: function (data) { 
    	   console.log(data);
     	  if(data==1){			//중복 ID
     		
     		var newtext='<font color="red">중복 아이디입니다.</font>';
     			$("#idcheck").text('');
       		$("#idcheck").show();
       		$("#idcheck").append(newtext);
         		$("#join_id").val('').focus();
         		return false;
	     
     	  }else{				//사용 가능한 ID
     		var newtext='<font color="blue">사용가능한 아이디입니다.</font>';
     		$("#idcheck").text('');
     		$("#idcheck").show();
     		$("#idcheck").append(newtext);
     		$("#join_pwd1").focus();
     	  }  	    	  
       }
       ,
   	  error:function(e){
   		  alert("data error"+e);
   	  }
     });//$.ajax	
};

//정규 표현식 검사
function validate_userid(memid){
 var pattern= new RegExp(/^[a-z0-9_]+$/);  //영문 소문자,숫자 ,_가능,정규표현식
 // 정규표현식과 맞지 않으면 false값 리턴
 
 return pattern.test(memid);
};

// 회원정보 수정 경고창 
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
function domain_list() {
	var num=f.mail_list.selectedIndex;  //selectedIndex속성은 select객체하위의 속성으로서 해당리스트 목록번호를 반환
	
	if ( num == -1 ) {					//num==-1은 해당 리스트목록이 없다
		return true;
	}
	if(f.mail_list.value=="0"){  		// 직접입력
		 f.join_maildomain.value="";
		 f.join_maildomain.readOnly=false;
		 f.join_maildomain.focus();
	}else {								//리스트목록을 선택했을때	 
		f.join_maildomain.value=f.mail_list.options[num].value;
		f.join_maildomain.readOnly=true;
	 }
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
<div id="join_wrap"  align="center" class="insert">
  <h2 class="join_title">회원가입</h2>
  <form name="f" method="post" action="${pageContext.request.contextPath }/RegAttempt.do"
  		onsubmit="return check()" enctype="multipart/form-data">
   
   <table id="join_t">
    <tr>
     <th>회원아이디</th>
     <td>
      <input name="mem_id" id="join_id" size="14" class="input_box" />
      <input type="button" value="아이디 중복체크" class="input_button"  onclick="id_check()" />
      <div id="idcheck"></div>
     </td>
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
      <input name="mem_name" id="join_name" size="14" class="input_box" />
     </td>
    </tr>
    
    <tr>
     <th>휴대전화번호</th>
     <td>
     <%@ include file="../include/phone_number.jsp" %>
     <select name="mem_phone1">
      <c:forEach var="p" items="${phone}" begin="0" end="5">
       <option value="${p}">${p}</option>
      </c:forEach>
     </select>-<input name="mem_phone2" id="join_phone2" size="4"
     maxlength="4" class="input_box" />-<input name="mem_phone3"
     id="join_phone3" size="4" maxlength="4" class="input_box" />
     </td>
    </tr>
    
    <tr>
     <th>이메일</th>
     <td>
      <input name="join_mailid" id="join_mailid" size="10" 
      class="input_box" />@<input name="join_maildomain" 
      id="join_maildomain" size="20" class="input_box" readonly />
      <!--readonly는 단지 쓰기,수정이 불가능하고 읽기만 가능하다 -->
      <select name="mail_list" onchange="domain_list()">
      <option value="">=이메일선택=</option>
      <option value="daum.net">daum.net</option>
      <option value="nate.com">nate.com</option>
      <option value="naver.com">naver.com</option>
      <option value="hotmail.com">hotmail.com</option>
      <option value="gmail.com">gmail.com</option>
      <option value="0">직접입력</option>
     </select> 
     </td>
    </tr>
   </table>
   
   <div id="join_menu">
    <input type="submit" value="회원가입" class="input_button" />
    <input type="reset" value="가입취소" class="input_button" 
    	onclick="$('#join_id').focus();" />
   </div>
  </form>
 </div>
</body>
</html>