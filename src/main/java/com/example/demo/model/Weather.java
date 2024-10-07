package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("weather")
public class Weather {
	private String regid;
	private String wtime;
	private String wtime2;
}
