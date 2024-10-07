package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.NoticeBoard;
import com.example.demo.service.NoticeBoardService;
import com.example.demo.service.PagingPgm;


@Controller
public class NoticeBoardController {
	@Autowired
	private NoticeBoardService noticeboardService;
	
	// 공지 게시판 이동
	@RequestMapping("/noticelist")
	public String initList() {
		return "redirect:noticelist/1";
	}
	// 공지 게시판 페이지 이동
	@RequestMapping("/noticelist/{pageNum}")
	public String select(@PathVariable("pageNum") String pageNum, NoticeBoard noticeboard, Model model) {
		final int rowPerPage = 10;
		if (pageNum == null || pageNum.equals("")) {
			pageNum = "1";
		}
		int currentPage = Integer.parseInt(pageNum);
		int total = noticeboardService.getTotal(noticeboard); // 검색
		int startRow = (currentPage - 1) * rowPerPage;
		noticeboard.setStartRow(startRow);
		noticeboard.setPage(currentPage);
		// List<Board> list = bs.list(startRow, endRow);
		PagingPgm pp = new PagingPgm(total, rowPerPage, currentPage);
		List<NoticeBoard> selectlist = noticeboardService.select(noticeboard);
		
		model.addAttribute("selectlist", selectlist);
		model.addAttribute("pp", pp);
		return "NoticeBoard/list";
	}
	// 작성폼 이동
	@RequestMapping("/insertform")
	public String insert() {
		return "NoticeBoard/insertform";
	}
	// 글 작성
	@RequestMapping("/insert")
	public String insert(NoticeBoard noticeboard) {
      String content = noticeboard.getNb_content();
      content = content.replace("\r\n","<br>");
      noticeboard.setNb_content(content);
      noticeboardService.insert(noticeboard);
      return "NoticeBoard/insert";
	}
	// 상세페이지
	@RequestMapping("/noticedetail/{pageNum}/{nb_no}")
	public String noticedetail(@PathVariable("nb_no") int nb_no,@PathVariable("pageNum") String pageNum, Model model) {
		NoticeBoard noticedetail = noticeboardService.getlist(nb_no);

		model.addAttribute("noticedetail",noticedetail);
		model.addAttribute("pageNum", pageNum);
		return "NoticeBoard/noticedetail";
	}
	// 글삭제
	@RequestMapping("/delete/{nb_no}")
	public String delete(@PathVariable("nb_no") int nb_no) {
		noticeboardService.delete(nb_no);
		return "NoticeBoard/delete";
	}
	// 수정폼 이동
	@RequestMapping("/updateform/{nb_no}")
	public String updateform(@PathVariable("nb_no") int nb_no,Model model) {
		NoticeBoard noticelist = noticeboardService.getlist(nb_no);
		model.addAttribute("noticelist",noticelist);
		String content = noticelist.getNb_content();
	    content = content.replace("<br>","\r\n");
	    noticelist.setNb_content(content);
	    model.addAttribute("noticelist",noticelist);
		return "NoticeBoard/updateform";
	}
	// 글수정
	@RequestMapping("/update/{nb_no}")
	public String update(@PathVariable("nb_no") int nb_no,NoticeBoard noticeboard) {
		String content = noticeboard.getNb_content();
      content = content.replace("\r\n","<br>");
      noticeboard.setNb_content(content);
      noticeboardService.update(noticeboard);
      return "NoticeBoard/update";
	}
}
