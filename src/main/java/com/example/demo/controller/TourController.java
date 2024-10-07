package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Tour;
import com.example.demo.service.TourService;

@Controller
public class TourController {

	@Autowired
	public TourService tourService;
	 
	//관광지 모든 data 조회 + 페이징
	@GetMapping("tourlist/{page}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> tourlist(@PathVariable("page") int page) {
	    	
	    //페이징 관련 변수
	    int limit = 9;
		int listcount = tourService.tour_getCount();	
		
		int start = (page - 1) * 9;		
		
		List<Tour> tourlist = tourService.tourlist(start);
		
		//한 페이지 당 출력할 페이지 단락 수 
		int block = 10;
		
		// 총 페이지수
		int pageCount = listcount / block + ((listcount % block == 0) ? 0 : 1);
		int startPage = ((page - 1) / block) * block + 1; 		// 1, 11, 21..
		int endPage = startPage + block - 1; 			   		// 10, 20, 30..
		if (endPage > pageCount)
			endPage = pageCount;
	    
	    Map<String, Object> map = new HashMap<>();
	    map.put("page", page);
	    map.put("listcount", listcount);
	    map.put("tourlist", tourlist);
	    map.put("pageCount", pageCount);
	    map.put("startPage", startPage);
	    map.put("endPage", endPage);
	
	    return new ResponseEntity<>(map, HttpStatus.OK);
	}
	    
	// 지역1선택 > 관광지 data 조회 + 페이징
	@GetMapping("t1_list/{selected_region1}/{page}")
	@ResponseBody
    public ResponseEntity<Map<String, Object>> t1_list(@PathVariable("selected_region1") String selected_region1,
	    												   @PathVariable("page") int page){
    	int start = (page - 1) * 9;
    	
    	//m 객체에 sql문 수행에 필요한 변수 가져감
    	Map<String, Object> m = new HashMap<>();
    	m.put("start", start);
    	m.put("selected_region1", selected_region1);
    	
        //페이징 관련 변수
        int limit = 9;
		int listcount = tourService.tour1_getCount(selected_region1);	//글갯수
		
		List<Tour> tour1_list = tourService.tour1_list(m);	//data
				
		//한 페이지 당 출력할 페이지 단락 수 
		int block = 10;
		
		// 총 페이지수
		int pageCount = listcount / block + ((listcount % block == 0) ? 0 : 1);
		int startPage = ((page - 1) / block) * block + 1; 		// 1, 11, 21..
		int endPage = startPage + block - 1; 			   		// 10, 20, 30..
		if (endPage > pageCount)
			endPage = pageCount;
        
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("listcount", listcount);
        map.put("tourlist", tour1_list);
        map.put("pageCount", pageCount);
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	    
	// 지역2선택 > 관광지 data 조회 + 페이징
    @GetMapping("t2_list/{selected_region2}/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> t2_list(@PathVariable("selected_region2") String selected_region2,
    													@PathVariable("page") int page){
    	
    	//m 객체에 sql문 수행에 필요한 변수 가져감
    	int start = (page - 1) * 9;
    	
    	Map<String, Object> m = new HashMap<>();
    	m.put("selected_region2", selected_region2);
    	m.put("start", start);
    	
        //페이징 관련 변수
        int limit = 9;
		int listcount = tourService.tour2_getCount(selected_region2);	//글갯수
		
		List<Tour> tour2_list = tourService.tour2_list(m);	//data
		
		//한 페이지 당 출력할 페이지 단락 수 
		int block = 10;
		
		// 총 페이지수
		int pageCount = listcount / block + ((listcount % block == 0) ? 0 : 1);
		int startPage = ((page - 1) / block) * block + 1; 		// 1, 11, 21..
		int endPage = startPage + block - 1; 			   		// 10, 20, 30..
		if (endPage > pageCount)
			endPage = pageCount;
        
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("listcount", listcount);
        map.put("tourlist", tour2_list);
        map.put("pageCount", pageCount);
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	    
    // 관광지 > 이름 조회
    @GetMapping("t_namelist/{name}/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> tourlist(@PathVariable("name") String name,
    													@PathVariable("page") int page){
        
    	int start = (page - 1) * 9;
    	
    	Map<String, Object> m = new HashMap<>();
    	m.put("name", name);
    	m.put("start", start);
    	
        //페이징 관련 변수
        int limit = 9;
		int listcount = tourService.tourname_getCount(name);	//글갯수
		
		List<Tour> tour_namelist = tourService.tour_namelist(m);		//data
		
		//한 페이지 당 출력할 페이지 단락 수 
		int block = 10;
		
		// 총 페이지수
		int pageCount = listcount / block + ((listcount % block == 0) ? 0 : 1);
		int startPage = ((page - 1) / block) * block + 1; 		// 1, 11, 21..
		int endPage = startPage + block - 1; 			   		// 10, 20, 30..
		if (endPage > pageCount)
			endPage = pageCount;
        
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("listcount", listcount);
        map.put("tourlist", tour_namelist);
        map.put("pageCount", pageCount);
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
