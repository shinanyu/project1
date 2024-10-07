package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dao.CampDAO;
import com.example.demo.model.Camp;

@Service
public class CampService {
	
	@Autowired
	private CampDAO campDAO;

	// 요청URL 중 numOfRows=4000&pageNo=1 한번에 넣는 데이터량 관련 변수인데, 필요하면 조절 가능
	public void camp(@ModelAttribute Camp camp){
		try {
			// API 요청을 위한 URL 객체를 생성
			URL url = new URL("https://apis.data.go.kr/B551011/GoCamping/basedList?serviceKey=JxKpkocHrptrio6e%2B1mWWIrYRu7pq38QPCxU6PqCG8MpdmxhxXliiixYKaeGt2RHiuGHQd539dMvchFmw8e%2BQA%3D%3D&numOfRows=4000&pageNo=1&MobileOS=ETC&MobileApp=camp");
			// URL 객체를 이용하여 HTTP 연결을 열고, 이를 통해 API 서버와 통신할 수 있는 HttpURLConnection 객체를 생성
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			// HTTP 요청 메서드를 설정
			urlConnection.setRequestMethod("GET");
			// HTTP 요청 헤더에 Content-type을 application/json 로 설정
			urlConnection.setRequestProperty("Content-type", "application/json");
			
			// 위의 요청에 대한 응답 데이터를 읽어오기
			BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
			
			//String은 변경 불가능한 문자열, StringBuilder를 통해 append 메소드로 문자열끼리 합치려고 함
			StringBuilder sb = new StringBuilder();
			String line;
			while((line=bf.readLine()) != null) {
				sb.append(line);
			}
			bf.close();
			
			//한줄로 읽어온 xml형식의 데이터를 json형식으로 변환
			org.json.JSONObject xmlJSONObj = XML.toJSONObject(sb.toString());
			
			JSONParser jsonParser = new JSONParser();
			// JSONParser.parse를 통해 JASON 문자열을 파싱해서 JSONObject 객체로 변환
			JSONObject jsonObject = (JSONObject)jsonParser.parse(xmlJSONObj.toString());
			// JSONObject 객체 안의 response 부분 JSONObject 객체형식으로 파싱
			JSONObject parseResponse = (JSONObject)jsonObject.get("response");
			// 위와 같은 방식으로 body 부분 파싱
			JSONObject parseBody = (JSONObject)parseResponse.get("body");
			// 위와 같은 방식으로 items 부분 파싱
			JSONObject parseItems = (JSONObject)parseBody.get("items");
			// items 안의 item 들을 JSON 배열 형식으로 파싱
			JSONArray jsonArray = (JSONArray)parseItems.get("item");
			// 배열형식으로 파싱된 것을 for문을 통해 1개씩 분리해서 그 안의 데이터 저장하기
			for(int i = 0; i < jsonArray.size(); i++) {
				JSONObject object = (JSONObject) jsonArray.get(i);
				String name = (String) object.get("facltNm");
				String intro = (String) object.get("intro");
				String addr = (String) object.get("addr1");
				String camp_lat = String.valueOf(object.get("mapY"));
				String camp_long = String.valueOf(object.get("mapX"));
				String tel = (String) object.get("tel");
				String hp = (String) object.get("homepage");
				String resve = (String) object.get("resveUrl");
				String image = (String) object.get("firstImageUrl");
				
				camp.setCamp_name(name);
				camp.setCamp_intro(intro);
				camp.setCamp_addr(addr);
				camp.setCamp_lat(camp_lat);
				camp.setCamp_long(camp_long);
				camp.setCamp_tel(tel);
				camp.setCamp_hp(hp);
				camp.setCamp_resve(resve);
				camp.setCamp_image(image);
				
				if(addr != null && addr.length()>=2) {
					String[] reg = addr.split(" ");
					switch(reg[0]) {
					case "전라북도":
						reg[0]="전북";
						break;
					case "전라남도":
						reg[0]="전남";
						break;
					case "충청북도":
						reg[0]="충북";
						break;
					case "충청남도":
						reg[0]="충남";
						break;
					case "경상북도":
						reg[0]="경북";
						break;
					case "경상남도":
						reg[0]="경남";
						break;
					}
					String regid = reg[0].substring(0, 2);
					// 검색기능 옵션으로 지역 선택할 때, 시/군 쉽게 찾으려고
					String regid2 = reg[1];
					camp.setCamp_addr2(regid2);
				
					// 날씨 찾기 영서-영동, 경기남부-북부(위도 정보가 없는 곳도 있어서 switch문)
					if(regid.equals("경기")) {
						switch(regid2) {
							case "고양시":
							case "파주시":
							case "연천군":
							case "동두천시":
							case "양주시":
							case "의정부시":
							case "포천시":
							case "가평군":
							case "남양주시":
							case "구리시":
								regid = "경기북부";
								break;
							default:
								regid = "경기남부";
								break;
						}
					}
					
					if (regid.equals("강원")) {
						switch (regid2) {
						    case "고성군":
						    case "속초시":
						    case "양양군":
						    case "강릉시":
						    case "동해시":
						    case "삼척시":
						    case "태백시":
						    	regid = "영동";
						        break;
						    default:
						    	regid = "영서";
						        break;
						}
					}
					camp.setCamp_regid(regid);
				}else {
					camp.setCamp_regid("");
					camp.setCamp_addr2("");
				}
				campDAO.insert(camp);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// 상세정보
	public Camp getCamp(int camp_no) {
		return campDAO.getCamp(camp_no);
	}
	
	// 추천기능
	public int spotLike(int camp_no) {
		return campDAO.spotLike(camp_no);
	}
	
	// 추천검색
	public List<Camp> bestCampList(Camp camp) {
		return campDAO.bestCampList(camp);
	}
	
	//캠핑장 모든 정보 조회
	public List<Camp> camplist(int start){
		return campDAO.camplist(start);
	}
	//캠핑장 지역1코드 조회 (중복x)
	public List<String> camp_regid_list() {
		return campDAO.camp_regid_list();
	}
	//캠핑장 지역2코드 조회
	public List<String> camp_regid2_list(String selected_region1) {
		return campDAO.camp_regid2_list(selected_region1);
	}
	//캠핑장 지역1 data조회
	public List<Camp> camp1_list(Map m) {
		return campDAO.camp1_list(m);
	}	
	//캠핑장 지역2 data 조회
	public List<Camp> camp2_list(Map m) {
		return campDAO.camp2_list(m);
	}

	//캠핑장 전체글 갯수
	public int camp_getCount() {
		return campDAO.camp_getCount();
	}
	//캠프 지역1 글갯수
	public int camp1_getCount(String selected_region) {
		return campDAO.camp1_getCount(selected_region);
	}
	//캠프 지역2 글갯수
	public int camp2_getCount(String selected_region2) {
		return campDAO.camp2_getCount(selected_region2);
	}
	//캠프 > 이름검색 글갯수
	public int campname_getCount(String name) {
		return campDAO.campname_getCount(name);
	}
	//캠핑장 이름 조회
	public List<Camp> camp_namelist(Map m) {
		return campDAO.camp_namelist(m);
	}

}
