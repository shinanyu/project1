package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PagesController {
	
	//로그인::loginAttemptSecurity(SecurityConfig에서 링크설정) 웹사이트에서 로그인 호출시 
	//      SecurityProvider에서 로그인 확인절차 진행
	//Authentication으로 선언된 객체가 존재한다면 로그인에 성공. 존재하지 않는다면 비로그인임.
	
	// 일반회원 마이페이지 이동
	@RequestMapping("MyPage")
	public String MyPage(HttpServletRequest hsr, HttpServletResponse response
			, Authentication auth, Model model) throws Exception {
		//인증된 사용자인경우
		if ( auth != null) {
			// 마이페이지로 이동(수정)
			return "member/myPage";
		}
		else { //인증이 안된사용자인경우
			return "redirect:public/AccessDenied";
		}
	}
	// 로그인 실패시
	@RequestMapping("public/loginFail")
	public String loginfail() {
		return "public/loginFail";
	}
	// 권한없는 상태에서 url을 통한 이동 방지
	@RequestMapping("public/AccessDenied")
	public String denied() {
		return "public/AccessDenied";
	}
	// 로그인 페이지
	@RequestMapping("public/login")
	public String login() {
		return "member/login";
	}
	// 탈퇴 페이지
	@RequestMapping("deleteMember")
	public String leaveMem(Authentication auth) {
		if ( auth == null) {
			return "redirect:public/AccessDenied";
		}
		return "member/deleteMember";
	}
	
}
