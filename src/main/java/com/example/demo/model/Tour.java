package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("tour")
public class Tour {
	private int tour_no;
	private String tour_name;
	private String tour_intro;
	private String tour_addr;
	private String tour_addr2;
	private String tour_regid;
	private String tour_lat;
	private String tour_long;
	private String tour_tel;
	private String tour_depart;
	private int tour_point;
	
}
