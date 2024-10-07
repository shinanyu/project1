package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("sfrct")
public class Sfrct {
	private int sfrct_no;
	private String regid;
	private String sfrct_time;
	private String tmp;
	private String pop;
	private String wsd;
	private String reh;
	private String sky;
	private String pty;
}
