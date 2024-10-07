package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Alias("bestloc")
public class Bestloc {
	private String regid;
	
}
