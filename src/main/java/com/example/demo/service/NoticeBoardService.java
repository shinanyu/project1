package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.NoticeBoardDAO;
import com.example.demo.model.NoticeBoard;

@Service
public class NoticeBoardService {

	@Autowired
	private NoticeBoardDAO noticeboardDAO;
	
	public int insert(NoticeBoard noticeboard) {
		return noticeboardDAO.insert(noticeboard);
	}

	public int update(NoticeBoard noticeboard) {
		return noticeboardDAO.update(noticeboard);
	}

	public int delete(int nb_no) {
		return noticeboardDAO.delete(nb_no);
	}
	public NoticeBoard getlist(int nb_no) {
		return noticeboardDAO.getlist(nb_no);
	}

	public List<NoticeBoard> select(NoticeBoard noticeboard) {
		return noticeboardDAO.select(noticeboard);
	}
	public void selectUpdate(int nb_no) {
		noticeboardDAO.selectUpdate(nb_no);
	}
	public int getTotal(NoticeBoard noticeboard) {
		return noticeboardDAO.getTotal(noticeboard);
	}
	public int getMaxNum() {
		return noticeboardDAO.getMaxNum();
	}

	public List<NoticeBoard> selectmain() {
		return noticeboardDAO.selectmain();
	}
	
}
