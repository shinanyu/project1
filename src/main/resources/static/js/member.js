// 유효성 검사
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

// 아이디 중복 체크
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
        url:"member_idcheck.do",
        data: {"memid":memid},        
        success: function (data) { 
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

// 정규 표현식 검사
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
 
 
 
 