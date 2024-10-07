package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.MemberDAOImpl;
import com.example.demo.model.MemberBean;

@Service
public class MemberService {

	@Autowired
	private MemberDAOImpl memberDao;
	// 회원 가입
	public void insertNew(MemberBean mem) throws Exception{
		memberDao.insertNew(mem);
	}
	// 상세정보(id 중복체크, 수정 등)
	public MemberBean checkMemberId(String id) {
		return memberDao.checkMemberId(id);
	}
	// 회원정보 수정
	public void update(MemberBean member) throws Exception {
		memberDao.updateMem(member);
	}
	// 회원 탈퇴(본인 or 관리자 권한)
	public void deleteMember(String id) {
		memberDao.deleteMember(id);
	}
	// 회원수 카운팅
	public int getCount() {
		return memberDao.getCount();
	}
	// 전체회원 조회
	public List<MemberBean> getUserList(int offset) {
		return memberDao.getUserList(offset);
	}
	// 경고스택 추가
	public int addWarning(String mem_id) {
		return memberDao.addWarning(mem_id);
	}

}