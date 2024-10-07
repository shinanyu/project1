package com.example.demo.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("noticeboard")
public class NoticeBoard {
	private int nb_no;
	private String nb_title;
	private String nb_content;
	private Date nb_date;
	
	// page
	private int startRow;
	private int page;
}
