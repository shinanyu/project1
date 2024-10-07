<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<c:set var="path" value="${pageContext.request.contextPath }"/>
<link rel="stylesheet" href="${path}/css/header.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="UTF-8">
<title>testList</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=qrk7u84vd0"></script>
<script>
// Model 에 담긴 tour_no 변수 할당 + contextPath 길어서 변수 할당 
var tour_no = ${tour_no};
var page = ${page};
var contextPath = "${pageContext.request.contextPath}";

// 세션으로 받을 유저 PK 값(FK로 넣을것)
var userno = 100;

// ++리스트 목록에서 가져올 값들 여기에서 전역변수로 할당하고 목록페이지 함수에서 같이 보내기

$(document).ready(function() {
	// 옵션 요소를 기본적으로 숨김
	$("#regs").hide();
	
	// 지역선택 클릭시 옵션 나오기
	$("#reg_select").click(function(){
		if ($("#regs").is(":hidden")){
			$("#regs").show();
		}else{
			$("#regs").hide();
		}
	});
	
	getTourDetail(tour_no);
	
});

// 관광지 상세정보
function getTourDetail(tour_no){
	$.ajax({
		type : "GET",
		url : contextPath+"/tourDetail/"+tour_no,
		success : function(result){
			// 상세내용
			var content = "<button onClick='ctlist()'>목록</button></a>"
				content += "<button onclick='tourSpotLike("+result.tour_no+")'>추천</button>"
			    content += "<table><tr><th>이름</th><td>"+result.tour_name+"</td></tr>"
			    content += "<tr><th>소개</th><td>"+result.tour_intro+"</td></tr>"
			    content += "<tr><th>주소</th><td>"+result.tour_addr+"</td></tr>"
			    content += "<tr><th>관계부서</th><td>"+result.tour_depart+"</td></tr>"
			    content += "<tr><th>연락처</th><td>"+result.tour_tel+"</td></tr></table>"
			    
			$("#detail").html(content);
			
			// 지도
	    	var mapOptions = {
				// 위치 지정
	    	    center: new naver.maps.LatLng(result.tour_lat, result.tour_long),
	    	    zoom: 15
	    	};
	    	var map = new naver.maps.Map('map', mapOptions);
	    	// 지도에 마커찍기
			var marker = new naver.maps.Marker({
			    position: new naver.maps.LatLng(result.tour_lat, result.tour_long),
			    map: map
			});
	    	var regid = result.tour_regid;
	    	
	    	getWeather2(regid);
	    	
			// 오늘 날씨1
			$("#today").click(function(){
				var hour = new Date().getHours();
				// 인덱스 0 이 예측시간 6시이고 갱신되기 전 새벽 1~5시 처리를 위해 다음과 같이 수식 정리
				var startIndex = (hour < 5) ? hour+19 : hour-5;
				getWeather(regid, startIndex);
				getDust1(regid);
			});
			
			// 내일 날씨1, 다음날 오전 12시 기준 (index 30)
			$("#second").click(function(){
				getWeather(regid, 30);
				getDust2(regid);
			});
			
			
			// 모레 날씨1, 이틀후 오전 12시 기준 (index 54)
			$("#third").click(function(){
				$("#dust").hide();
				getWeather(regid, 54);
			});
			
			
			// 중기예보
			$("#week").click(function(){
				getWeek(regid);
			});
			
		}
	});
}

// 하늘상태 코드 변환
function getSkyStatus(skyCode){
	switch (skyCode){
	case '1':
		skyStatus="맑음";
		break;
	case '3':
		skyStatus="구름많은";
		break;
	case '4':
		skyStatus="흐림";
		break;	
	default:
		skyStatus="알 수 없음";
	}
}

// 강수형태 코드 변환
function getSkyType(ptyCode){
	switch (ptyCode){
	case '0':
		skyType="강수 없음";
		break;
	case '1':
		skyType="비";
		break;
	case '2':
		skyType="비/눈";
		break;
	case '3':
		skyType="눈";
		break;
	case '4':
		skyType="소나기";
		break;
	default:
		skyType="알 수 없음";
	}
}

// 단기예보1
function getWeather(regid, startIndex){
	// 중기예보 영역 숨기고, 단기예보 영역 보이기
	$("#wdetail1").show();
	$("#wdetail2").show();
	$("#weekDetail").hide();
	$.ajax({
		url : contextPath+"/currt/"+regid,
		success : function(wresult){
			getSkyStatus(wresult.slist[startIndex].sky);
			getSkyType(wresult.slist[startIndex].pty);
			var imgsrc = weatherImg(wresult.slist[startIndex].pty,wresult.slist[startIndex].sky);
			
			var weather ="<div class='wdetail1a'><table>"
			weather +="<tr><td style='height:150px;'><img src='"+contextPath+imgsrc+"' style='height: 100px; width: auto;'></td><td style='font-size:60px; font-weight:bold;'>"+wresult.slist[startIndex].tmp+"°C</td></tr>"
			weather +="<tr><td colspan='2'>시간 : "+wresult.slist[startIndex].sfrct_time+"시 , "+skyStatus+" , "+skyType+"</td></tr>"
			weather +="</table></div>"
			weather +="<div class='wdetail1b'><table>"
			weather +="<tr><td>현재풍속 : "+wresult.slist[startIndex].wsd+"m/s</td></tr>"
			weather +="<tr><td>현재습도 : "+wresult.slist[startIndex].reh+" %</td></tr>"
			weather +="<tr><td>강수확률 : "+wresult.slist[startIndex].pop+" %</td></tr>"
			weather +="</table></div>";
			
			$("#wdetail1").html(weather);
		}
		
	});
}

// 단기예보2
function getWeather2(regid){
	$.ajax({
        url: contextPath+"/currt/" + regid,
        success: function (wresult) {
            var startIndex = wresult.hour;
            getWeather(regid, startIndex);
            getDust1(regid);
            
   			weather ="<span style='font-size: 20px';>시간별 정보</span><br><table style='margin-left: auto; margin-right: auto; width: 500px;'><tr><th>시간</th><th>온도</th><th>강수확률</th><th>습도</th><th>풍속</th><th style='width: 80px;'>구름</th><th style='width: 80px;'>상태</th></tr>"
   			$.each(wresult.slist,function(index,item){
                getSkyStatus(item.sky);
    			getSkyType(item.pty);
   				if (index > startIndex) {
   					weather += "<tr><td>"+item.sfrct_time+"</td>";
   					weather += "<td>"+item.tmp+"</td>";
   					weather += "<td>"+item.pop+"</td>";
   					weather += "<td>"+item.reh+"</td>";
   					weather += "<td>"+item.wsd+"</td>";
   					weather += "<td>"+skyStatus+"</td>";
   					weather += "<td>"+skyType+"</td></tr>";
   				}
   			});
   			weather +="</table>"
   			
   			$("#wdetail2").html(weather);
        }
    });
}

// 중기예보
function getWeek(regid){
	// 단기예보, 미세먼지 영역 숨기고, 중기예보 영역 보이기
	$("#wdetail1").hide();
	$("#wdetail2").hide();
	$("#dust").hide();
	$("#weekDetail").show();
	
	$.ajax({
		url : contextPath+"/weekDetail/"+regid,
		success : function(result){
			weekcon = "";
			$.each(result.wlist,function(index,item){
				weekcon += "<tr><td>"+item.lfrct_time+"일 후, </td>";
				weekcon += "<td>"+item.sky+"</td>";
				weekcon += "<td>최저 온도 :"+item.tem_min+"°C</td>";
				weekcon += "<td>최고 온도 :"+item.tem_max+"°C</td>";
				weekcon += "<td>강수 확률 :"+item.pop+"%</td></tr>";
			});
			
			$("#weekDetail").html(weekcon);
		}
	});
}

// 미세먼지 오늘,내일
function getDust1(regid){
	$("#dust").show();
	$.ajax({
		url : contextPath+"/dustDetail/"+regid,
		success : function(result){
			weather ="<span><table style='margin-left: auto; margin-right: auto; width: 500px;'><tr><td>미세먼지 : "+result.air_grade+"</td></tr></table></span>";
			$("#dust").html(weather);
		}
	});
}

function getDust2(regid){
	$("#dust").show();
	$.ajax({
		url : contextPath+"/dustDetail2/"+regid,
		success : function(result){
			weather ="<span><table style='margin-left: auto; margin-right: auto; width: 500px;'><tr><td>미세먼지 : "+result.air_grade+"</td></tr></table></span>";
			$("#dust").html(weather);
		}
	});
}

// 다른 지역 현재 날씨
function currtrgid(regid){
	$("#regs").hide();
	$.ajax({
		url : contextPath+"/currt/"+regid,
		success : function(wresult){
			var startIndex = wresult.hour;
			getWeather(regid,startIndex);
			getDust1(regid);
			
			weather ="<span style='font-size: 20px';>시간별 정보</span><br><table style='margin-left: auto; margin-right: auto; width: 500px;'><tr><th>시간</th><th>온도</th><th>강수확률</th><th>습도</th><th>풍속</th><th style='width: 80px;'>구름</th><th style='width: 80px;'>상태</th></tr>"
			$.each(wresult.slist,function(index,item){
				getSkyStatus(item.sky);
    			getSkyType(item.pty);
				if (index > startIndex) {
					weather += "<tr><td>"+item.sfrct_time+"</td>";
					weather += "<td>"+item.tmp+"</td>";
					weather += "<td>"+item.pop+"</td>";
					weather += "<td>"+item.reh+"</td>";
					weather += "<td>"+item.wsd+"</td>";
					weather += "<td>"+skyStatus+"</td>";
					weather += "<td>"+skyType+"</td></tr>";
				}
			});
			weather +="</table>"
			
			$("#wdetail2").html(weather);

			// 이전에 등록된 클릭 이벤트 핸들러 제거 > 안하면 클릭이벤트 꼬임
			$("#today").off("click");
			// 오늘 날씨1
			$("#today").click(function(){
				var hour = new Date().getHours();
				var startIndex = (hour < 5) ? hour+19 : hour-5;
				getWeather(regid, startIndex);
				getDust1(regid);
			});
			
			$("#second").off("click");
			// 내일 날씨1, 다음날 오전 12시 기준 (index 30)
			$("#second").click(function(){
				getWeather(regid, 30);
				getDust2(regid);
			});
			
			$("#third").off("click");
			// 모레 날씨1, 이틀후 오전 12시 기준 (index 54)
			$("#third").click(function(){
				$("#dust").hide();
				getWeather(regid, 54);
			});
			
			$("#week").off("click");
			// 중기예보
			$("#week").click(function(){
				getWeek(regid);
			});
			
		}
	});
	
}

// 추천 기능
function tourSpotLike(tour_no){
	$.ajax({
		type: "GET",
		url: contextPath+"/tourSpotLike/"+tour_no,
		success: function(response) {
			alert("추천하셨습니다");
		}
	});
}

// 리스트 목록으로 다시 가기
function ctlist(){
	location.href="ctlist";
}

// 날씨아이콘
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
<style>
	.detail2{
	/*Flexbox 레이아웃 모델을 사용하기 위해 사용
	Flexbox는 효율적이고 유연한 레이아웃을 만들 수 있도록 도와주는 CSS 속성*/
		display: flex;
		align-items: center; /* 수직 가운데 정렬 */
		width: 1000px;
	}
	.half-width1{
		width: 60%; /* 부모 요소의 60% 크기 */
	}
	.half-width2{
		width: 40%; /* 부모 요소의 40% 크기 */
		width: 400px;
		height: 400px;
	}
	.detailpage{
		margin-left: auto;
		margin-right: auto;
		width: 1000px;
	}
	.reg_select{
		text-align: left;
	}
	.regs{
		display: none;
		width: 350px;
		height: 100px;
		border: 1px solid black; /* 경계선 두께 1px, 색상 */
		padding: 5px;
	}
	.regs ul {
		display: flex;
		flex-wrap: wrap;	/* 여러 줄에 걸쳐 표시될 수 있도록 길면 자동 넘김*/
		list-style-type: none;	/* ul이나 ol 앞에 마커 지우기 */
		padding: 5px;	/* 내부 여백 */
		margin: 0;
	}
	.regs li {
		width: 25%; /* 4개씩 정렬하므로 너비를 25%로 설정 */
		box-sizing: border-box; /* 내부 여백과 테두리를 포함한 크기 지정 */
		padding: 5px; /* 각 아이템의 내부 여백 */
		text-align: center; /* 내용을 가운데 정렬 */
	}
	.wdetail1{
		display: flex;
		width: 600px;
		align-items: center;
	}
	.wdetail1a{
		width: 60%;
		text-align: center;
	}
	.wdetail1b{
		width: 40%;
		text-align: center;
	}
	.wdetail2{
		width: 600px;
		max-height: 400px;
		overflow-y: auto;
		text-align: center;
	}
	.link{
		text-decoration: none; /* 밑줄 없애기 */
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
	<h1><a href="${path}/main"><img src="image/airplane.png" alt="로고" style="weight: 100px; height:100px;"></a></h1>
</div>
<div class="topMenu" >
	<ul class="menu">
		<li class="li"><a class="menuLink" href="${path}/noticelist">공지사항</a></li>
		<li class="li"><a class="menuLink" href="${path}/ctlist">캠핑장/관광지 검색</a></li>
		<li class="li"><a class="menuLink" href="${path}/rb/reviewList">후기게시판</a></li>
	</ul>
</div>
<!-- 상세 페이지 -->
<div id="detailpage" class="detailpage">
	<div id="detail" class="detail">
	</div>
	<div class="detail2">
		<div class="half-width1">
			<div id="selectOption" class="selectOption">
				<button id="reg_select" class="reg_select">지역</button>
				<button id="today">오늘</button>
				<button id="second">내일</button>
				<button id="third">모레</button>
				<button id="week">이번주</button>
			</div>
			<div id="regs" class="regs">
			<ul>
			
				<li><a href="javascript:currtrgid('경기북부')">경기북부</a></li>
				<li><a href="javascript:currtrgid('경기남부')">경기남부</a></li>
				<li><a href="javascript:currtrgid('영서')">영서</a></li>
				<li><a href="javascript:currtrgid('영동')">영동</a></li>
				<li><a href="javascript:currtrgid('충북')">충청북도</a></li>
				<li><a href="javascript:currtrgid('충남')">충청남도</a></li>
				<li><a href="javascript:currtrgid('전북')">전라북도</a></li>
				<li><a href="javascript:currtrgid('전남')">전라남도</a></li>
				<li><a href="javascript:currtrgid('경북')">경상북도</a></li>
				<li><a href="javascript:currtrgid('경남')">경상남도</a></li>
			</ul>
			</div>
			<div id="weather">
				<div class='wdetail'>
					<div id='wdetail1' class='wdetail1'></div>
					<div id='dust' class='dust'></div>
					<div id='wdetail2' class='wdetail2'></div>
				</div>
				<div><table id='weekDetail'></table></div>
			</div>
		</div>
		<div id="map" class="half-width2"></div>
	</div>
</div>

</body>
</html>