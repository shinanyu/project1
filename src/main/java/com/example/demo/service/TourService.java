package com.example.demo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.dao.TourDAO;
import com.example.demo.model.Tour;

@Service
public class TourService {
	
	@Autowired
	private TourDAO tourDAO;
	
	// 관광지 정보 입력
	public void tour(@ModelAttribute Tour tour) {
		JSONParser parser = new JSONParser();
		
		try {
			// 관광지 파일 데이터 읽어오기
			FileReader reader = new FileReader("c:/webStudy/tourdata.json");
			Object object = parser.parse(reader);
			JSONObject jsonObj = (JSONObject)object;
			reader.close();
			
			// JSONObject 객체 안의 records(배열형태) 부분 JSONArray 형식으로 파싱
			JSONArray parseRecords = (JSONArray)jsonObj.get("records");
			for(int i = 0; i < parseRecords.size(); i++) {
				JSONObject obj = (JSONObject)parseRecords.get(i);
				String name = (String)obj.get("관광지명");
				String intro= (String)obj.get("관광지소개");
				String addr= (String)obj.get("소재지지번주소");
				String tour_lat= String.valueOf(obj.get("위도"));
				String tour_long= String.valueOf(obj.get("경도"));
				String tel= (String)obj.get("관리기관전화번호");
				String depart= (String)obj.get("관리기관명");
				
				tour.setTour_name(name);
				tour.setTour_intro(intro);
				tour.setTour_addr(addr);
				tour.setTour_lat(tour_lat);
				tour.setTour_long(tour_long);
				tour.setTour_tel(tel);
				tour.setTour_depart(depart);
				
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
					tour.setTour_addr2(regid2);
					
					// 날씨 찾기 영서-영동, 경기남부-북부 
					// 영동 : 고성군, 속초시, 양양군, 강릉시, 동해시, 삼척시, 태백시
					// 경기남부-북부 위도(lat) 37.5 기준
					if(regid.equals("경기") && Double.parseDouble(tour_lat) >= 37.5) {
						regid = "경기북부";
					}
					if(regid.equals("경기") && Double.parseDouble(tour_lat) < 37.5) {
						regid = "경기남부";
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
					tour.setTour_regid(regid);
				}else {
					tour.setTour_regid("");
					tour.setTour_addr2("");
				}
				
				tourDAO.insert(tour);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

	// 관광지 상세정보
	public Tour getTour(int tour_no) {
		return tourDAO.getTour(tour_no);
	}

	// 관광지 추천
	public int spotLike(int tour_no) {
		return tourDAO.spotLike(tour_no);
	}
	
	//관광지 모든 정보 조회
	public List<Tour> tourlist(int start){
		return tourDAO.tourlist(start);
	}
	//관광지 지역1코드 조회
	public List<String> tour_regid_list(){
		return tourDAO.tour_regid_list();
	}
	//관광지 지역2코드 조회
	public List<String> tour_regid2_list(String selected_region1) {
		return tourDAO.tour_regid2_list(selected_region1);
	}
	//관광지 지역1 data조회
	public List<Tour> tour1_list(Map m) {
		return tourDAO.tour1_list(m);
	}

	//관광지 지역2 data 조회
	public List<Tour> tour2_list(Map m) {
		return tourDAO.tour2_list(m);
	}

	//관광지 전체글 갯수
	public int tour_getCount() {
		return tourDAO.tour_getCount();
	}
	
	//관광지 지역1 글갯수
	public int tour1_getCount(String selected_region) {
		return tourDAO.tour1_getCount(selected_region);
	}
	//관광지 지역2 글갯수
	public int tour2_getCount(String selected_region2) {
		return tourDAO.tour2_getCount(selected_region2);
	}
	//관광지 이름검색 글갯수
	public int tourname_getCount(String name) {
		return tourDAO.tourname_getCount(name);
	}
	//관광지 이름 조회
	public List<Tour> tour_namelist(Map m) {
		return tourDAO.tour_namelist(m);
	}

}
