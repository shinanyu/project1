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

import com.example.demo.model.Camp;
import com.example.demo.service.CampService;

@Controller
public class CampController {

	@Autowired
    public CampService campService;
 
	// 캠핑장 모든 data 조회 + 페이징
    @GetMapping("camplist/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> camplist(@PathVariable("page") int page) {
        //페이징 관련 변수
        int limit = 9;
		int listcount = campService.camp_getCount();	
		
		int start = (page - 1) * 9;		
		
		List<Camp> camplist = campService.camplist(start);
		
		// 캠프 이미지 없는 경우 > 대체 이미지 넣어주고 map에 저장
        for(Camp camp : camplist) {
        	String camp_image = camp.getCamp_image();	//캠프 객체 내의 image 값들 뽑아서 변수에 저장
        	if(camp_image.isEmpty()) {camp_image = "image/airplane.png";}
        	// 새로운 이미지 값을 설정한 후 다시 Camp 객체에 저장
        	camp.setCamp_image(camp_image);
        }		
		
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
        map.put("camplist", camplist);
        map.put("pageCount", pageCount);
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
	    
    // 지역1선택 > 캠핑장 data 조회 + 페이징
    @GetMapping("c1_list/{selected_region1}/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> c1_list(@PathVariable("selected_region1") String selected_region1,
    												   @PathVariable("page") int page){
		//m 객체에 sql문 수행에 필요한 변수 가져감
		int start = (page - 1) * 9;
		
		Map<String, Object> m = new HashMap<>();
		m.put("selected_region1", selected_region1);
		m.put("start", start);
		
		//페이징 관련 변수
		int limit = 9;
		int listcount = campService.camp1_getCount(selected_region1);	//글갯수
		
		List<Camp> camp1_list = campService.camp1_list(m);	//data
		
		// 캠프 이미지 없는 경우 > 대체 이미지 넣어주고 map에 저장
		for(Camp camp : camp1_list) {
			String camp_image = camp.getCamp_image();	//캠프 객체 내의 image 값들 뽑아서 변수에 저장
			
			if(camp_image.isEmpty()) {camp_image = "image/airplane.png";}
			
			// 새로운 이미지 값을 설정한 후 다시 Camp 객체에 저장
			camp.setCamp_image(camp_image);
		} 
		
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
		map.put("camplist", camp1_list);
		map.put("pageCount", pageCount);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
    }
	    
	// 지역2선택 > 캠핑장 data 조회 + 페이징
	@GetMapping("c2_list/{selected_region2}/{page}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> c2_list(@PathVariable("selected_region2") String selected_region2,
														@PathVariable("page") int page){
		//m 객체에 sql문 수행에 필요한 변수 가져감
		int start = (page - 1) * 9;
		
		Map<String, Object> m = new HashMap<>();
		m.put("selected_region2", selected_region2);
		m.put("start", start);
		
	    //페이징 관련 변수
	    int limit = 9;
		int listcount = campService.camp2_getCount(selected_region2);	//글갯수
		
		List<Camp> camp2_list = campService.camp2_list(m);	//data
		
		// 캠프 이미지 없는 경우 > 대체 이미지 넣어주고 map에 저장
	    for(Camp camp : camp2_list) {
	    	String camp_image = camp.getCamp_image();	//캠프 객체 내의 image 값들 뽑아서 변수에 저장
	    	
	    	if(camp_image.isEmpty()) {camp_image = "image/airplane.png";}
	    	
	    	// 새로운 이미지 값을 설정한 후 다시 Camp 객체에 저장
	    	camp.setCamp_image(camp_image);
	    } 
		
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
	    map.put("camplist", camp2_list);
	    map.put("pageCount", pageCount);
	    map.put("startPage", startPage);
	    map.put("endPage", endPage);
	    
	    return new ResponseEntity<>(map, HttpStatus.OK);
	}
    
    // 캠프 > 이름 조회
    @GetMapping("c_namelist/{name}/{page}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> namelist(@PathVariable("name") String name,
    													@PathVariable("page") int page){
    	int start = (page - 1) * 9;
    	
    	Map<String, Object> m = new HashMap<>();
    	m.put("name", name);
    	m.put("start", start);
    	
        //페이징 관련 변수
        int limit = 9;
		int listcount = campService.campname_getCount(name);	//글갯수
		
		List<Camp> camp_namelist = campService.camp_namelist(m);		//data
		
		// 캠프 이미지 없는 경우 > 대체 이미지 넣어주고 map에 저장
		for(Camp camp : camp_namelist) {
			String camp_image = camp.getCamp_image();	//캠프 객체 내의 image 값들 뽑아서 변수에 저장
			
			if(camp_image.isEmpty()) {camp_image = "image/airplane.png";}
			
			// 새로운 이미지 값을 설정한 후 다시 Camp 객체에 저장
			camp.setCamp_image(camp_image);
		} 
		
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
        map.put("camplist", camp_namelist);
        map.put("pageCount", pageCount);
        map.put("startPage", startPage);
        map.put("endPage", endPage);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	 
}
