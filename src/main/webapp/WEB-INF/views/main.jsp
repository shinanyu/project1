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
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<link rel="stylesheet" href="${path}/css/main.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
<title>main</title>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	allWeather();
});

$(function(){
	$('#slider-div').slick({
		slide: 'div',		//슬라이드 되어야 할 태그 ex) div, li 
		vertical : false,		// 세로 방향 슬라이드 옵션
		centerMode: true,
		centerPadding: '70px',
		infinite : true, 	//무한 반복 옵션	 
		slidesToShow : 4,		// 한 화면에 보여질 컨텐츠 개수
		slidesToScroll : 1,		//스크롤 한번에 움직일 컨텐츠 개수
		speed : 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
		arrows : true, 		// 옆으로 이동하는 화살표 표시 여부
		autoplay : true,			// 자동 스크롤 사용 여부
		autoplaySpeed : 10000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
		pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
		prevArrow : "<button type='button' class='slick-prev'>Previous</button>",		// 이전 화살표 모양 설정
		nextArrow : "<button type='button' class='slick-next'>Next</button>",		// 다음 화살표 모양 설정
		draggable : true, 	//드래그 가능 여부 
		
		responsive: [ // 반응형 웹 구현 옵션
			{  
				breakpoint: 960, //화면 사이즈 960px
				settings: {
					//위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
					slidesToShow:3 
				} 
			},
			{ 
				breakpoint: 768, //화면 사이즈 768px
				settings: {	
					//위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
					slidesToShow:2 
				} 
			}
		]
	});
})

function allWeather(){
	$.ajax({
	type : "GET",
	url : "${pageContext.request.contextPath}/allRegWeather",
	success : function(result){
		var content = "<table><tr><th colsapn='2'>전 국 날 씨</th></tr>";
		$.each(result.slist,function(index,item){
			var imgsrc = weatherImg(item.pty,item.sky);
			if (index % 2 === 0) { // 짝수 인덱스
				content += "<tr>";
			}
			content += "<td><img src='"+imgsrc+"' style='height: 40px; width: auto;'></td>";
			content += "<td>"+item.regid+" "+item.tmp+"°C</br>강수확률"+item.pop+"%</td>"
			if (index % 2 === 1) { // 홀수 인덱스
				content += "</tr>";
			}
		});
		// 행이 홀수인 경우에 대비 하여 표 마무리
		if (result.slist.length % 2 === 1) {
			 content += "<td></td></tr>";
		}

		content += "</table>";
		$("#allRegWeather").html(content);
		}
	});
}

function weatherImg(pty,sky){
	var imgsrc = "";
	switch(pty){
	case "1":
		imgsrc = "image/rain.png";
		break;
	case "2":
		imgsrc = "image/snownrain.png";
		break;
	case "3":
		imgsrc = "image/snow.png";
		break;
	default :
		switch(sky){
		case "1":
			imgsrc = "image/sunny.png";
			break;
		case "3":
			imgsrc = "image/cloudy1.png";
			break;
		default :
			imgsrc = "image/cloudy2.png";
			break;
		}
	}
	return imgsrc;
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
<!-- 인증에서 해당 username(우리는 mem_id)를 얻으려면 다음과같이 principal 속성에 접근 -->
<sec:authentication property="principal" var="mem_id" />
<!-- mem_id 라는 변수에 저장한다면 ${mem_id} 이것을 이용해서 해당 페이지에서 사용가능
<script>	로그인 하면 로그인을 한 해당 id를 알려줌(위 과정을 거치면 인증정보가 있는 모든 페이지에서 가능)
    alert("${mem_id}");
</script>  -->
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
<div>
	<h2>오늘의 추천 캠핑장</h2>
</div>
<div style="padding:15px 25px; background-color: skyblue;" >
<div id="slider-div">
	<c:forEach items="${camp}" var="c">
	  	<div>
	  		<a href="${path}/cdetail?camp_no=${c.camp_no}&page=1">
	  			<image src="${c.camp_image}" style="width:200px; height:200px;"/>
	  		</a>
	  		${c.camp_name}
	  	</div>
	</c:forEach>
</div>
</div>
<div class="container" align="center">
	<div class="notice">
		<table class="noticetable">
			<tr>
				<th>공지사항</th>
			</tr>
			<c:if test="${empty Noticelist}">
				<tr>
					<td colspan="5">데이터가 없습니다</td>
				</tr>
			</c:if>
			<c:if test="${not empty Noticelist}">
			<c:forEach items="${Noticelist }" var="n"  end="4">
				<tr>
					<td><a class="a" href="${path }/noticedetail/1/${n.nb_no}">${n.nb_title}</a></td>
					<td><fmt:formatDate value="${n.nb_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</c:forEach>
			</c:if>
			<tr>
			</tr>
		</table>
	</div>
	<div id="allRegWeather" class="weather">
	</div>
</div>
  
</body>
</html>