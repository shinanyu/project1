package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Alias("member")
public class MemberBean {

	//Constructor
	public MemberBean() {

	}
	public MemberBean(String mem_id) {
		this.mem_id = mem_id;
	}
	public MemberBean(String mem_id, String mem_pw) {
		this.mem_id = mem_id;
		this.mem_pw = mem_pw;
		this.mem_type = "2";
	}
	//field
	private String mem_id;
	private String mem_pw;
	private String mem_name;
	private String mem_phone;
	private String mem_email;
	private String mem_date;
	private String mem_active;
	private int warningstack;
	private String mem_type;

}
