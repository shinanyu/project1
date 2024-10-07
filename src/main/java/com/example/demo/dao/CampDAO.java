package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Camp;

@Mapper
public interface CampDAO {
	// 캠핑장 정보 입력
	public int insert(Camp camp);
	// 캠핑장 상세정보
	public Camp getCamp(int camp_no);
	// 캠핑장 추천
	public int spotLike(int camp_no);
	// 캠핑장 추천리스트
	List<Camp> bestCampList(Camp camp);
	// 캠핑장 모든 정보 조회
	public List<Camp> camplist(int start);
	// 캠핑장 지역1 코드 조회(중복x)
	public List<String> camp_regid_list();
	// 캠핑장 지역2 코드 조회
	public List<String> camp_regid2_list(String selected_region1);
	// 캠핑장 지역1 data 조회
	public List<Camp> camp1_list(Map m);
	// 캠핑장 지역2 data 조회
	public List<Camp> camp2_list(Map m);
	//캠프 글갯수
	public int camp_getCount();
	//캠프 지역1 글갯수
	public int camp1_getCount(String selected_region);
	//캠프 지역2 글갯수
	public int camp2_getCount(String selected_region2);
	//캠핑장 이름검색 글갯수
	public int campname_getCount(String name);
	//캠핑장 이름 조회
	public List<Camp> camp_namelist(Map m);
}
