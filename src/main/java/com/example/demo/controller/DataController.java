package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Camp;
import com.example.demo.model.Tour;
import com.example.demo.service.CampService;
import com.example.demo.service.DustService;
import com.example.demo.service.LfrctService;
import com.example.demo.service.SfrctService;
import com.example.demo.service.TourService;


@Controller
public class DataController {
	
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
	
	// 캠핑장 데이터 외부API 통해 DB에 저장
	@RequestMapping("camp")
	public String camp(@ModelAttribute Camp camp) {
		campService.camp(camp);
		return "end";
	}
	// 관광지 데이터 JSON 파일 읽어와서 DB에 저장
	@RequestMapping("tour")
	public String tour(@ModelAttribute Tour tour) {
		tourService.tour(tour);
		return "end";
	}
	
	// 날씨 정보 데이터들 외부API 통해 DB에 저장, 주기적 갱신 스케쥴러
	@Scheduled(cron="0 0 6 * * *")
	public void dust() {
		dustService.dust();
	}
	
	@Scheduled(cron="30 0 6 * * *")
	public void longn() {
		lfrctService.longn();
	}
	
	@Scheduled(cron="0 1 6 * * *")
	public void shortn() {
		sfrctService.shortn();
	}
	
}
