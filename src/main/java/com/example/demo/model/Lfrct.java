package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("lfrct")
public class Lfrct {
	private int lfrct_no;
	private String regid;
	private String lfrct_time;
	private String tem_min;
	private String tem_max;
	private String sky;
	private String pop;
	
}
