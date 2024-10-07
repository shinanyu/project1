package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.ReviewDTO;
import com.example.demo.model.SearchDTO;

@Mapper
public interface ReviewDAO {
	// 게시글 카운팅
	public int getCount();
	// 리스트 출력
	public List<ReviewDTO> getList(int offset);
	// 게시글 상세정보
	public ReviewDTO getBoard(int rb_no);
	// 조회수 증가
	public void updatehit(int rb_no);
	// 게시글 수정
	public int update(ReviewDTO reviewDTO);
	// 게시글 삭제
	public int delete(int rb_no);
	// 게시글 작성
	public int insert(ReviewDTO reviewDTO);
	// 검색 후 리스트 출력
	public List<ReviewDTO> searchList(SearchDTO searchDTO);
	// 검색 한 게시글 카운팅
	public int getSearchCount(SearchDTO searchDTO);
}
