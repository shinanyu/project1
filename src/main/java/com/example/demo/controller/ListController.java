package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.CampService;
import com.example.demo.service.TourService;

@Controller
public class ListController {

    @Autowired
    public CampService campservice;
    
    @Autowired
    public TourService tourservice;

    @RequestMapping("ctlist")
    public String list() {
        return "ctlist";
    }
    
    //지역1 option 리스트 
    @GetMapping("regid1_list/{sort}")
    @ResponseBody
    public List<String> regid1_list(@PathVariable("sort") String sort) {
        if (sort.equals("camping")) {
            // 캠핑장 지역1 코드 (중복x)
            List<String> camp_regid_list = campservice.camp_regid_list();

            return camp_regid_list;
            
        }else if(sort.equals("tour")){ // 관광지 지역코드
            // 뽑아놓고 보니 지역코드가 공백인 행이 존재 
            List<String> tour_regid_list = tourservice.tour_regid_list();
            
            // 공백이 제거된 배열 받을 변수 준비
            List<String> tour_regid1_list = new ArrayList<>();
            
            for (String code : tour_regid_list) {
                String trimmedCode = code.replaceAll("\\s+", ""); // 정규 표현식을 사용하여 문자열 내부의 모든 공백을 제거
                if (!trimmedCode.isEmpty()) {
                	tour_regid1_list.add(trimmedCode);
                }
            }
            return tour_regid1_list;
        }
        else {
            // 정의되지 않은 sort 값에 대한 처리
            throw new IllegalArgumentException("유효하지 않은 sort 값입니다: " + sort);
        }
    }
    
    
    //지역2 option 리스트
    @GetMapping("regid2_list/{selected_region1}/{sort}")
    @ResponseBody
    public List<String> regid2_list(@PathVariable("selected_region1") String selected_region1,
                                    @PathVariable("sort") String sort) {
    	try {
	        if (selected_region1 != null && sort.equals("camping")) {
	        	// 캠핑장 지역2 조회 (null, 공백 없음)
	        	
	        	List<String> c_regid2_list = campservice.camp_regid2_list(selected_region1);
	        	
	        	// null이 제거된 데이터만 필터링하여 반환
	            List<String> camp_regid2_list = new ArrayList<>();
	        	
	        	for (String item : c_regid2_list) {
	                if (item != null && !item.trim().isEmpty()) {	//null값이 아니고, 공백도 없어야함 > 둘 중 하나라도 있으면 예외처리를 또 따로 해주어야 함
	                    camp_regid2_list.add(item);
	                }
	            }
	        	
	            return camp_regid2_list;
	            
	        }else if (selected_region1 != null && sort.equals("tour")) {
	            // 관광지 지역2 조회 (null값 존재)
	            List<String> t_regid2_list = tourservice.tour_regid2_list(selected_region1);
	            
	            // null이 제거된 데이터만 필터링하여 반환
	            List<String> tour_regid2_list = new ArrayList<>();
	            
	            for (String item : t_regid2_list) {
	                if (item != null && !item.trim().isEmpty()) {	//null값이 아니고, 공백도 없어야함 > 둘 중 하나라도 있으면 예외처리를 또 따로 해주어야 함
	                    tour_regid2_list.add(item);
	                }
	            }
	            return tour_regid2_list;
	        }
	        //try문 전체에서 예외가 발생할 경우도 따로 처리를 해주어야 함
	        return new ArrayList<String>();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return new ArrayList<String>();
        }
    }
    
}
