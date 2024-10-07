package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.MemberBean;
import com.example.demo.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {

	@Autowired
	private MemberService ms;
	
	// 회원가입 페이지로 이동
	@RequestMapping("public/Register")
	public String register() {
		return "public/Register";
	}
	
	// 중복ID 찾기
	@RequestMapping(value = "/member_idcheck.do", method = RequestMethod.POST)
	@ResponseBody
	public int member_idcheck(@RequestParam("memid") String id) throws Exception {
		int result = -1;	   // 사용 가능한 ID
		MemberBean member = ms.checkMemberId(id);
		if(member != null)
		   result = 1; 	       // 중복 ID
		
		return result;
	}
	
	// 회원가입하기
	@PostMapping("RegAttempt.do")
	public String RegCheck(MemberBean member,HttpServletRequest hsr, HttpServletResponse response) throws Exception {
		log.info("regattempt");
		//mem_phone 1,2,3   join_mailid   mail_list
		String phone1 = hsr.getParameter("mem_phone1");
		String phone2 = hsr.getParameter("mem_phone2");
		String phone3 = hsr.getParameter("mem_phone3");
		String join_mailid = hsr.getParameter("join_mailid");
		String mail_list = hsr.getParameter("mail_list");
		
		String mem_phone = phone1 +"-"+ phone2 +"-"+ phone3;
		String mem_email = join_mailid + "@" + mail_list;
		
		member.setMem_phone(mem_phone);
		member.setMem_email(mem_email);
		
        ms.insertNew(member);
        return "member/RegSuccess";
	}
	
	// 수정폼 로딩
	@GetMapping("/updateForm/{mem_id}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> updateform(@PathVariable("mem_id") String mem_id){
		MemberBean editm = ms.checkMemberId(mem_id);
		
		String[] phoneNo = editm.getMem_phone().split("-");
		String[] email = editm.getMem_email().split("@");
		
		Map map = new HashMap<>();
		map.put("editm", editm);
		map.put("no1", phoneNo[0]);
		map.put("no2", phoneNo[1]);
		map.put("no3", phoneNo[2]);
		map.put("mailid", email[0]);
		map.put("mail_list", email[1]);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	// 회원정보 수정
	@PostMapping("member_edit_ok.do")
	public String member_edit_ok(MemberBean member,HttpServletRequest hsr, HttpServletResponse response) throws Exception {
		log.info("update");
		String phone1 = hsr.getParameter("mem_phone1");
		String phone2 = hsr.getParameter("mem_phone2");
		String phone3 = hsr.getParameter("mem_phone3");
		String join_mailid = hsr.getParameter("join_mailid");
		String mail_list = hsr.getParameter("mail_list");
		
		String mem_phone = phone1 +"-"+ phone2 +"-"+ phone3;
		String mem_email = join_mailid + "@" + mail_list;
		
		member.setMem_phone(mem_phone);
		member.setMem_email(mem_email);
		
        ms.update(member);
        return "member/RegSuccess";
	}

	// 회원정보 삭제
	@PostMapping("deleteMember.do")
	public String memberDel(@RequestParam("mem_pw") String mem_pw, Authentication auth) throws Exception {
		log.info("delete");
		if ( auth == null) {
			return "redirect:public/AccessDenied";
		}
		String id = auth.getName();
		String pwd = ms.checkMemberId(id).getMem_pw();

		if (!pwd.equals(mem_pw)) {   // 비번 불일치시
			return "member/deleteMember";
		} else {										// 비번 일치시			
			ms.deleteMember(id);	// delete SQL문 실행
			return "redirect:logout";
		}
	}
	
	// 회원 리스트 출력
	@GetMapping("getUserList/{page}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getUserList(@PathVariable("page") int page){
		int limit = 10;
		int offset = (page-1)*limit;
		int listcount = ms.getCount();
		List<MemberBean> mlist = ms.getUserList(offset);
		
		//한 페이지 당 출력할 페이지 단락 수 
		int block = 10;
		// 총 페이지수
		int pageCount = listcount / block + ((listcount % block == 0) ? 0 : 1);
		int startPage = ((page - 1) / block) * block + 1; 		// 1, 11, 21..
		int endPage = startPage + block - 1; 			   		// 10, 20, 30..
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		Map map = new HashMap<>();
		map.put("mlist", mlist);
		map.put("page", page);
		map.put("listcount", listcount);
		map.put("pageCount", pageCount);
		map.put("startPage", startPage);
		map.put("endPage", endPage);

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	// 회원 경고주기
	@GetMapping("addWarning/{memid}")
	@ResponseBody
	public int addWarning(@PathVariable("memid") String memid) {
		int result = ms.addWarning(memid);
		
		return result;
	}
	
	// 회원 탈퇴시키기
	@GetMapping("deleteUser/{memid}")
	@ResponseBody
	public int deleteUser(@PathVariable("memid") String memid) {
		ms.deleteMember(memid);
		int result = 0;
		return result;
	}
	
}
