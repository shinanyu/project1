package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("camp")
public class Camp {
	private int camp_no;
	private String camp_name;
	private String camp_intro;
	private String camp_addr;
	private String camp_addr2;
	private String camp_regid;
	private String camp_lat;
	private String camp_long;
	private String camp_tel;
	private String camp_hp;
	private String camp_resve;
	private String camp_image;
	private int camp_point;
	// 추천캠핑장 조건 위해서
	private String regid;
}
