package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Camp;
import com.example.demo.model.Dust;
import com.example.demo.model.Lfrct;
import com.example.demo.model.Sfrct;
import com.example.demo.model.Tour;
import com.example.demo.model.Weather;
import com.example.demo.service.CampService;
import com.example.demo.service.DustService;
import com.example.demo.service.LfrctService;
import com.example.demo.service.SfrctService;
import com.example.demo.service.TourService;


@Controller
public class DetailController {
	
	@Autowired
	private CampService campService;
	@Autowired
	private TourService tourService;
	@Autowired
	private DustService dustService;
	@Autowired
	private SfrctService sfrctService;
	@Autowired
	private LfrctService lfrctService;
	@Autowired
	private Weather weather;
	
	// 캠핑장 상세페이지 이동
	@GetMapping("cdetail")
	public String cdetailPage(@RequestParam("camp_no") int camp_no,@RequestParam("page") int page,Model model) {
		model.addAttribute("camp_no", camp_no);
		model.addAttribute("page",page);
		return "cdetail";
	}
	// 관광지 상세페이지 이동
	@GetMapping("tdetail")
	public String tdetail(@RequestParam("tour_no") int tour_no,@RequestParam("page") int page,Model model) {
		model.addAttribute("tour_no", tour_no);
		model.addAttribute("page",page);
		return "tdetail";
	}
	// 캠핑장 상세정보 데이터 로딩
	@GetMapping("/campDetail/{camp_no}")
	@ResponseBody
	public ResponseEntity<Camp> cdetail(@PathVariable("camp_no") int camp_no){
		Camp camp = campService.getCamp(camp_no);
		
		return new ResponseEntity<>(camp, HttpStatus.OK);
	}
	// 관광지 상세정보 데이터 로딩
	@GetMapping("/tourDetail/{tour_no}")
	@ResponseBody
	public ResponseEntity<Tour> tdetail(@PathVariable("tour_no") int tour_no){
		Tour tour = tourService.getTour(tour_no);
		
		return new ResponseEntity<>(tour, HttpStatus.OK);
	}
	// 단기예보에서 날씨 데이터 로딩
	@GetMapping("/currt/{regid}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> currWeather(@PathVariable("regid") String regid){
		List<Sfrct> slist = sfrctService.getWeather(regid);
		
		// 현재시간 기준 DB의 index 찾아서 보여주기
		LocalDateTime currentTime = LocalDateTime.now();
		int hour = currentTime.getHour();
		int hourIndex = (hour < 5) ? hour+19 : hour-5;
		
		Map map = new HashMap<>();
		map.put("slist", slist);
		map.put("hour", hourIndex);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	// 중기예보에서 날씨 데이터 로딩
	@GetMapping("/weekDetail/{regid}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> weekDetail(@PathVariable("regid") String regid){
		List<Lfrct> wlist = lfrctService.getWeek(regid);
		
		Map map = new HashMap<>();
		map.put("wlist", wlist);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	// 먼지예보에서 날씨 데이터 로딩 (오늘 정보) 백엔드쪽에선 오늘/내일 정보 프로세스 합칠수 있을듯
	@GetMapping("/dustDetail/{regid}")
	@ResponseBody
	public ResponseEntity<Dust> getDustDetail(@PathVariable("regid") String regid){
		// 예측 시간과 지역코드 DTO에 담아서 넘겨서 미세먼지 정보 가져오기
		LocalDate today = LocalDate.now();
		String wtime = today.toString();
		weather.setRegid(regid);
		weather.setWtime(wtime);
		Dust dust = dustService.getOne(weather);
		
		return new ResponseEntity<>(dust, HttpStatus.OK);
	}
	// 먼지예보에서 날씨 데이터 로딩 (내일 정보)
	@GetMapping("/dustDetail2/{regid}")
	@ResponseBody
	public ResponseEntity<Dust> getDustDetail2(@PathVariable("regid") String regid){
		// 예측 시간과 지역코드 DTO에 담아서 넘겨서 미세먼지 정보 가져오기
		LocalDate today = LocalDate.now();
		String wtime = today.plusDays(1).toString();
		weather.setRegid(regid);
		weather.setWtime2(wtime);
		Dust dust = dustService.getTwo(weather);
		
		return new ResponseEntity<>(dust, HttpStatus.OK);
	}
	// 캠핑장 추천기능
	@GetMapping("/campSpotLike/{camp_no}")
	@ResponseBody
	public int campSpotLike(@PathVariable("camp_no") int camp_no) {
		return campService.spotLike(camp_no);
	}
	// 관광지 추천기능
	@GetMapping("/tourSpotLike/{tour_no}")
	@ResponseBody
	public int tourSpotLike(@PathVariable("tour_no") int tour_no) {
		return tourService.spotLike(tour_no);
	}
	
}
