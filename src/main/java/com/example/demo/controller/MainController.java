package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Bestloc;
import com.example.demo.model.Camp;
import com.example.demo.model.NoticeBoard;
import com.example.demo.model.Sfrct;
import com.example.demo.service.BestlocService;
import com.example.demo.service.CampService;
import com.example.demo.service.NoticeBoardService;
import com.example.demo.service.SfrctService;


@Controller
public class MainController {
	
	@Autowired
	private CampService campService;
	@Autowired
	private BestlocService bestlocService;
	@Autowired
	private NoticeBoardService noticeboardService;
	@Autowired
	private SfrctService sfrctService;
	
	// 메인 추천 기능, 공지사항 최신글
	@RequestMapping("main")
	public String select(Camp camp, Model model) {
		List<Bestloc> bestloclist = bestlocService.getlist();
		List<NoticeBoard> Noticelist = noticeboardService.selectmain();
		String regid;
		String re = String.valueOf(bestloclist.get(0));
		if (re.length() == 19) {
			regid = re.substring(14,18);
		} else {
			regid = re.substring(14,16);
		}
		
		camp.setRegid("%"+regid+"%");
		
		List<Camp> BestCamplist = campService.bestCampList(camp);
		
		model.addAttribute("camp", BestCamplist);
		model.addAttribute("Noticelist", Noticelist);
		return "main";
	}
	
	// 전국의 당일 날씨
	@GetMapping("/allRegWeather")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> allRegWeather(){
		// 현재시간 날씨찾기
		LocalDateTime currentTime = LocalDateTime.now();
		int hour = currentTime.getHour()+1;
		String hour1 = hour < 10 ? ("0" + hour) : String.valueOf(hour);
		List<Sfrct> list = sfrctService.allRegWeather(hour1);
		List<Sfrct> slist = new ArrayList<>();
		for(int i=0;i<list.size();i+=3) {
			slist.add(list.get(i));
		}
		
		Map map = new HashMap<>();
		map.put("slist", slist);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
