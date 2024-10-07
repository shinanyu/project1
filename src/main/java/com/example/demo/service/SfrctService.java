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

import com.example.demo.dao.SfrctDAO;
import com.example.demo.model.Sfrct;

@Service
public class SfrctService {
	
	@Autowired
	private SfrctDAO sfrctDAO;
	@Autowired
	private Sfrct sfrct;
	@Autowired
	private DataSource dataSource;
	
	public void shortn() {
		// 데이터 삭제
		sfrctDAO.autoDelete();
		// pk 자동증가 시작값 초기화
		String sql = "ALTER TABLE short_frct AUTO_INCREMENT = 1";
		
		try(Connection con = dataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		try {
			List<String> nxlist = Arrays.asList(new String[] {"60","51","68","58","98","89","101","53","60","56","73","93","76","50","63","50","91","80"});
			List<String> nylist = Arrays.asList(new String[] {"127","128","100","74","77","91","84","38","120","131","133","132","114","109","89","66","106","75"});
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date today = new Date();
			
			for(int j = 0; j < nxlist.size() ; j++) {	//nxlist.size()
				String baseurl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=JxKpkocHrptrio6e%2B1mWWIrYRu7pq38QPCxU6PqCG8MpdmxhxXliiixYKaeGt2RHiuGHQd539dMvchFmw8e%2BQA%3D%3D&pageNo=1&numOfRows=1000&dataType=JSON&base_time=0500";
				String base_date = "&base_date="+dateFormat.format(today);		
				String nxny = "&nx="+nxlist.get(j)+"&ny="+nylist.get(j);
				
				URL url = new URL(baseurl+base_date+nxny);
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				urlConnection.setRequestMethod("GET");
				urlConnection.setRequestProperty("Content-type", "application/json");
	
				// 위의 요청에 대한 응답 데이터를 읽어오기
				BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
						
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
				// 위와 같은 방식으로 items 부분 파싱
				JSONObject parseItems = (JSONObject)parseBody.get("items");
				// items 안의 item 들을 JSON 배열 형식으로 파싱
				JSONArray jsonArray = (JSONArray)parseItems.get("item");
				// 배열형식으로 파싱된 것을 for문을 통해 1개씩 분리해서 그 안의 데이터 저장하기
				for(int i=0;i<jsonArray.size();i++) {	//jsonArray.size()
					JSONObject object = (JSONObject)jsonArray.get(i);
					
					String fcstTime = (String)object.get("fcstTime");
					String sfrct_time = fcstTime.substring(0, 2);
					// nx와 ny값 합쳐서 지역코드에 하나로 넣기
					String regid = String.valueOf(object.get("nx"))+"-"+String.valueOf(object.get("ny"));
					String category = (String)object.get("category");
					String fcstValue = (String)object.get("fcstValue");
					
					sfrct.setSfrct_time(sfrct_time);
					
					String regids[] = {"60-127","51-128","68-100","58-74","98-77","89-91","101-84","53-38","60-120","56-131","73-133","93-132","76-114","50-109","63-89","50-66","91-106","80-75"};
					String regidk[] = {"서울","인천","대전","광주","부산","대구","울산","제주","경기남부","경기북부","영서","영동","충북","충남","전북","전남","경북","경남"};
					
					for(int k = 0; k < regids.length; k++) {
						if(regid.equals(regids[k])) {
							regid = regidk[k];
						}
					}
					sfrct.setRegid(regid);
					
					// 필요 카테고리값만 저장
					switch(category) {
					case "TMP" :
						sfrct.setTmp(fcstValue);
						break;
					case "WSD" :
						sfrct.setWsd(fcstValue);
						break;
					case "SKY" :
						sfrct.setSky(fcstValue);
						break;
					case "POP" :
						sfrct.setPop(fcstValue);
						break;
					case "REH" :
						sfrct.setReh(fcstValue);
						break;
					case "PTY" :
						sfrct.setPty(fcstValue);
						break;
					}
					// 카테고리 값이 시간마다 반복적으로 계산됨 > 시간마다 저장하기 위해
					if(i%12==11) {
						sfrctDAO.insert(sfrct);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public List<Sfrct> getWeather(String regid) {
		return sfrctDAO.getWeather(regid);
	}

	public List<Sfrct> allRegWeather(String hour1) {
		return sfrctDAO.allRegWeather(hour1);
	}

}
