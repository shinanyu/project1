package com.example.demo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Tour;

@Mapper
public interface TourDAO {
	// 관광지 정보 입력
	public int insert(Tour tour);
	// 관광지 상세 정보
	public Tour getTour(int tour_no);
	// 관광지 추천
	public int spotLike(int tour_no);
	// 관광지 모든 정보 조회
	public List<Tour> tourlist(int start);
	// 관광지 지역1 코드 조회
	public List<String> tour_regid_list();
	// 관광지 지역2 코드 조회
	public List<String> tour_regid2_list(String selected_region1);
	// 관광지 지역1 data 조회
	public List<Tour> tour1_list(Map m);
	//관광지 지역2 data 조회
	public List<Tour> tour2_list(Map m);
	//관광지 전체글 갯수
	public int tour_getCount();
	//관광지 지역1 data갯수
	public int tour1_getCount(String selected_region);
	//관광지 지역2 data갯수
	public int tour2_getCount(String selected_region2);
	//관광지 이름검색 글갯수
	public int tourname_getCount(String name);
	//관광지 이름 조회
	public List<Tour> tour_namelist(Map m);
}
