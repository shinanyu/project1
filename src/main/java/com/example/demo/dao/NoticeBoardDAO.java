package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.NoticeBoard;

@Mapper
public interface NoticeBoardDAO {

	List<NoticeBoard> select(NoticeBoard noticeboard);
	int insert(NoticeBoard noticeboard);
	int update(NoticeBoard noticeboard);	
	int delete(int nb_no);
	public NoticeBoard getlist(int nb_no);
	void selectUpdate(int nb_no);
	int getTotal(NoticeBoard noticeboard);
	int getMaxNum();
	List<NoticeBoard> selectmain();
}
