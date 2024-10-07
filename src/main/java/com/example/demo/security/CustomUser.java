package com.example.demo.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.example.demo.model.MemberBean;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
//User 객체를 만들기 위한 구현클래스
public class CustomUser extends User {
	private MemberBean member;

	public CustomUser(MemberBean member) {
		super(member.getMem_id(), member.getMem_pw(), AuthorityUtils.createAuthorityList(member.getMem_type()));
		this.member = member;
	}

}