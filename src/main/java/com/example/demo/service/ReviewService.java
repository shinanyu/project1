package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.ReviewDAO;
import com.example.demo.model.ReviewDTO;
import com.example.demo.model.SearchDTO;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewDAO reviewDAO;

	public int getCount() {
		return reviewDAO.getCount();
	}

	public List<ReviewDTO> getList(int offset) {
		return reviewDAO.getList(offset);
	}

	public ReviewDTO getBoard(int rb_no) {
		return reviewDAO.getBoard(rb_no);
	}

	public void updatehit(int rb_no) {
		reviewDAO.updatehit(rb_no);
	}

	public int update(ReviewDTO reviewDTO) {
		return reviewDAO.update(reviewDTO);
	}

	public int delete(int rb_no) {
		return reviewDAO.delete(rb_no);
	}

	public int insert(ReviewDTO reviewDTO) {
		return reviewDAO.insert(reviewDTO);
	}
	
	public List<ReviewDTO> searchList(SearchDTO searchDTO) {
		return reviewDAO.searchList(searchDTO);
	}

	public int getSearchCount(SearchDTO searchDTO) {
		return reviewDAO.getSearchCount(searchDTO);
	}

}
