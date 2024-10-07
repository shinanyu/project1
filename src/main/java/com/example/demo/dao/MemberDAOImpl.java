package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.MemberBean;

@Mapper
public interface MemberDAOImpl {
	// 회원가입
	void insertNew(MemberBean mem);
	// 상세정보(id 중복체크, 수정 등)
	MemberBean checkMemberId(String id);
	// 회원정보 수정
	void updateMem(MemberBean member);
	// 회원 탈퇴(본인 or 관리자 권한)
	void deleteMember(String id);
	// 회원수 카운팅
	int getCount();
	// 전체회원 조회
	List<MemberBean> getUserList(int offset);
	// 경고스택 추가
	int addWarning(String mem_id);
}
