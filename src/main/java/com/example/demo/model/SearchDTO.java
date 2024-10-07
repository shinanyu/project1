package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("searchDTO")
public class SearchDTO {
	private String sel;
	private String find;
	private int offset;
}
