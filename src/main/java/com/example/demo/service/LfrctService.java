package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.LfrctDAO;
import com.example.demo.model.Lfrct;

@Service
public class LfrctService {
	
	@Autowired
	private LfrctDAO lfrctDAO;
	@Autowired
	private Lfrct lfrct;
	@Autowired
	private DataSource dataSource;
	
	public void longn() {
		// 이전 데이터 삭제
		lfrctDAO.autoDelete();
		// pk 자동증가 시작값 초기화
		String sql = "ALTER TABLE long_frct AUTO_INCREMENT = 1";
		
		try(Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			List<String> list1 = Arrays.asList(new String[] {"11B00000","11B00000","11C20000","11F20000","11H20000","11H10000","11H20000","11G00000","11B00000","11B00000","11D10000","11D20000","11C10000","11C20000","11F10000","11F20000","11H10000","11H20000"});
			List<String> list2 = Arrays.asList(new String[] {"11B10101","11B20201","11C20401","11F20501","11H20201","11H10701","11H20101","11G00201","11B20601","11B20305","11D10301","11D20501","11C10101","11C20101","11F10201","21F20801","11H10501","11H20701"});
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date today = new Date();
			
			for (int i = 0; i < list1.size(); i++) {
				String Id1 = list1.get(i);
				String Id2 = list2.get(i);
				
				String baseurl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=JxKpkocHrptrio6e%2B1mWWIrYRu7pq38QPCxU6PqCG8MpdmxhxXliiixYKaeGt2RHiuGHQd539dMvchFmw8e%2BQA%3D%3D&numOfRows=10&dataType=JSON&pageNo=1";
				String regId1 = "&regId="+ Id1;
				String tmFc = "&tmFc="+dateFormat.format(today)+"0600";
				
				String baseurl2 = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=JxKpkocHrptrio6e%2B1mWWIrYRu7pq38QPCxU6PqCG8MpdmxhxXliiixYKaeGt2RHiuGHQd539dMvchFmw8e%2BQA%3D%3D&numOfRows=10&dataType=JSON&pageNo=1";
				String regId2 = "&regId="+Id2;
				String tmFc2 = "&tmFc="+dateFormat.format(today)+"0600";
				
				URL url = new URL(baseurl+regId1+tmFc);
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				urlConnection.setRequestMethod("GET");
				
				URL url2 = new URL(baseurl2+regId2+tmFc2);
				HttpURLConnection urlConnection2 = (HttpURLConnection)url2.openConnection();
				urlConnection2.setRequestMethod("GET");
				
				BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
				BufferedReader bf2 = new BufferedReader(new InputStreamReader(url2.openStream(),"UTF-8"));
				
				StringBuilder sb = new StringBuilder();
				String line;
				while((line=bf.readLine()) != null) {
					sb.append(line);
				}
				bf.close();
				
				StringBuilder sb2 = new StringBuilder();
				String line2;
				while((line2=bf2.readLine()) != null) {
					sb2.append(line2);
				}
				bf2.close();
				
				JSONParser jsonParser = new JSONParser();
				// JSONParser.parse를 통해 JASON 문자열을 파싱해서 JSONObject 객체로 변환
				JSONObject jsonObject = (JSONObject)jsonParser.parse(sb.toString());
				// JSONObject 객체 안의 response 부분 JSONObject 객체형식으로 파싱
				JSONObject parseResponse = (JSONObject)jsonObject.get("response");
				// 위와 같은 방식으로 body 부분 파싱
				JSONObject parseBody = (JSONObject)parseResponse.get("body");
				// 위와 같은 방식으로 items 부분 파싱
				JSONObject parseItems = (JSONObject)parseBody.get("items");
				// items 안의 item이 한개 JSONArray 형식으로 나옴
				JSONArray jsonArray = (JSONArray)parseItems.get("item");
				// 한개여서 첫번째 인덱스에서 바로 꺼내오기
				JSONObject o1 = (JSONObject)jsonArray.get(0);

				JSONParser jsonParser2 = new JSONParser();
				// JSONParser.parse를 통해 JASON 문자열을 파싱해서 JSONObject 객체로 변환
				JSONObject jsonObject2 = (JSONObject)jsonParser2.parse(sb2.toString());
				// JSONObject 객체 안의 response 부분 JSONObject 객체형식으로 파싱
				JSONObject parseResponse2 = (JSONObject)jsonObject2.get("response");
				// 위와 같은 방식으로 body 부분 파싱
				JSONObject parseBody2 = (JSONObject)parseResponse2.get("body");
				// 위와 같은 방식으로 items 부분 파싱
				JSONObject parseItems2 = (JSONObject)parseBody2.get("items");
				// items 안의 item이 한개 JSONArray 형식으로 나옴
				JSONArray jsonArray2 = (JSONArray)parseItems2.get("item");
				// 한개여서 첫번째 인덱스에서 바로 꺼내오기
				JSONObject o2 = (JSONObject)jsonArray2.get(0);
				
				String reg = String.valueOf(o2.get("regId"));
				
				String regidk[] = {"서울","인천","대전","광주","부산","대구","울산","제주","경기남부","경기북부","영서","영동","충북","충남","전북","전남","경북","경남"};
				for(int l = 0; l < regidk.length; l++) {
					if(reg.equals(list2.get(l))) {
						reg = regidk[l];
					}
				}
				lfrct.setRegid(reg);

				String[] popa = new String[8];
				String[] skya = new String[8];
				String[] tamia = new String[8];
				String[] tamaa = new String[8];
				for(int k = 3; k < 11; k++) {
					popa[k-3] = String.valueOf(o1.get("rnSt"+k+(k>7 ? "":"Pm")));
					skya[k-3] = String.valueOf(o1.get("wf"+k+(k>7 ? "":"Pm")));
					tamia[k-3] = String.valueOf(o2.get("taMin"+k));
					tamaa[k-3] = String.valueOf(o2.get("taMax"+k));
				}
		
				List<String> list4 = Arrays.asList(new String[] {popa[0],popa[1],popa[2],popa[3],popa[4],popa[5],popa[6],popa[7]});
				List<String> list5 = Arrays.asList(new String[] {skya[0],skya[1],skya[2],skya[3],skya[4],skya[5],skya[6],skya[7]});
				List<String> list6 = Arrays.asList(new String[] {tamia[0],tamia[1],tamia[2],tamia[3],tamia[4],tamia[5],tamia[6],tamia[7]});
				List<String> list7 = Arrays.asList(new String[] {tamaa[0],tamaa[1],tamaa[2],tamaa[3],tamaa[4],tamaa[5],tamaa[6],tamaa[7]});
				
				for (int j = 0; j<list4.size(); j++) {
					String pop = list4.get(j);
					String sky = list5.get(j);
					String tami = list6.get(j);
					String tama = list7.get(j);
					String ltime = String.valueOf(j+3);
					
					lfrct.setPop(pop);
					lfrct.setSky(sky);
					lfrct.setTem_min(tami);
					lfrct.setTem_max(tama);
					lfrct.setLfrct_time(ltime);
					lfrctDAO.insert(lfrct);
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<Lfrct> getWeek(String regid) {
		return lfrctDAO.getWeek(regid);
	}

}
