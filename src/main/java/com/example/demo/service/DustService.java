package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.DustDAO;
import com.example.demo.model.Dust;
import com.example.demo.model.Weather;

@Service
public class DustService {
	
	@Autowired
	private DustDAO dustDAO;
	@Autowired
	private Dust dust;
	@Autowired
	private DataSource dataSource;

	public void dust(){
		// 이전 데이터 삭제
		dustDAO.autoDelete();
		// pk 자동증가 시작값 초기화
		String sql = "ALTER TABLE air_dust AUTO_INCREMENT = 1";
		
		try(Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			// 요청변수 중에 매번 바뀌어야될 변수 = 날짜
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date today = new Date();
			String searchDate = "&searchDate="+dateFormat.format(today);
			
			URL url = new URL("https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth?serviceKey=JxKpkocHrptrio6e%2B1mWWIrYRu7pq38QPCxU6PqCG8MpdmxhxXliiixYKaeGt2RHiuGHQd539dMvchFmw8e%2BQA%3D%3D&returnType=json&numOfRows=100&pageNo=1&InformCode=PM10"+searchDate);
			HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setRequestMethod("GET");
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
			
			JSONParser jsonParser = new JSONParser();
			// JSONParser.parse를 통해 JASON 문자열을 파싱해서 JSONObject 객체로 변환
			JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
			// JSONObject 객체 안의 response 부분 JSONObject 객체형식으로 파싱
			JSONObject parseResponse = (JSONObject)jsonObject.get("response");
			// 위와 같은 방식으로 body 부분 파싱
			JSONObject parseBody = (JSONObject)parseResponse.get("body");
			// 위와 같은 방식으로 items 부분 파싱 - 실패, "items":[{"":"","":""}] 이런 형식으로 나옴
//			JSONObject parseItems = (JSONObject)parseBody.get("items");
			// JSONArray 형식으로 받아보기
			JSONArray parseItems = (JSONArray)parseBody.get("items");
			// items 안의 item 들을 JSON 배열 형식으로 파싱 > 실패
			// JSONArray 형식이라고 JASONObject 안된다함 콘솔 찍어보니 배열형태
			for(int i = 0; i < 2; i++) {
				JSONObject object = (JSONObject) parseItems.get(i);
				String air_time = (String) object.get("informData");
				String air_grade = (String) object.get("informGrade");

				String[] grade = air_grade.split(",");
				for(int j = 0; j < grade.length; j++) {
					dust.setAir_time(air_time);
					String[] grade2 = grade[j].split(" : ");
					dust.setRegid(grade2[0]);
					dust.setAir_grade(grade2[1]);
					dustDAO.insert(dust);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Dust getOne(Weather weather) {
		return dustDAO.getOne(weather);
	}

	public Dust getTwo(Weather weather) {
		return dustDAO.getTwo(weather);
	}

}
