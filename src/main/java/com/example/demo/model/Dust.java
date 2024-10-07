package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("dust")
public class Dust {
	private int air_no;
	private String air_time;
	private String regid;
	private String air_grade;
	
}
