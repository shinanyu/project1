package com.example.demo.model;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("reviewDTO")
public class ReviewDTO {
	private int rb_no;
	private String rb_subject;
	private String rb_content;
	private String rb_writer;
	private Date rb_date;
	private int rb_hit;
	private int rb_point;
	private int rb_rank;
}
