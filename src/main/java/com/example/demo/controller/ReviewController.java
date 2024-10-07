package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.ReviewDTO;
import com.example.demo.model.SearchDTO;
import com.example.demo.service.ReviewService;

@Controller
@RequestMapping("rb/")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private SearchDTO searchDTO;
	
	// 후기게시판 기본1페이지
	@GetMapping("reviewList")
	public String reviewList() {
		return "redirect:reviewList/1";
	}
	
	// page 변수 넘어올 경우 후기게시판 진입
	@GetMapping("reviewList/{page}")
	public String reviewList(@PathVariable("page") int page,Model model) {
		model.addAttribute("page",page);
		return "reviewBoard/reviewList";
	}
	
	// 검색 리스트 페이지 진입
	@GetMapping("reviewSearch/{page}/{sel}/{find}")
	public String reviewSearch(@PathVariable("page") int page,@PathVariable("sel") String sel,@PathVariable("find") String find,Model model) {
		model.addAttribute("page",page);
		model.addAttribute("sel",sel);
		model.addAttribute("find",find);
		
		return "reviewBoard/reviewSearch";
	}
	
	// 글 리스트 출력
	@GetMapping("/reviewBoard/{page}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> reviewBoard(@PathVariable("page") int page) {
		int limit = 10;
		int offset = (page-1)*limit;
		int listcount = reviewService.getCount();
		List<ReviewDTO> rblist = reviewService.getList(offset);
		
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
		map.put("rblist", rblist);
		map.put("page", page);
		map.put("listcount", listcount);
		map.put("pageCount", pageCount);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
				
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	// 검색 리스트 출력
	@GetMapping("/reviewBoard/{page}/{sel}/{find}")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> searchBoard(@PathVariable("page") int page,@PathVariable("sel") String sel,@PathVariable("find") String find) {
		int limit = 10;
		int offset = (page-1)*limit;
		searchDTO.setSel(sel);
		searchDTO.setFind(find);
		searchDTO.setOffset(offset);
		// 검색조건 글 카운팅
		int listcount = reviewService.getSearchCount(searchDTO);
		List<ReviewDTO> rblist = reviewService.searchList(searchDTO);
		
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
		map.put("rblist", rblist);
		map.put("page", page);
		map.put("listcount", listcount);
		map.put("pageCount", pageCount);
		map.put("startPage", startPage);
		map.put("endPage", endPage);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	// 글작성
	@PostMapping("/boardwrite")
	@ResponseBody
	public ResponseEntity<Integer> boardwrite(@RequestBody ReviewDTO reviewDTO) {
		int result = reviewService.insert(reviewDTO);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	// 상세페이지로 이동
	@GetMapping("/rbdetail/{rb_no}/{page}")
	public String rbdetail(@PathVariable("rb_no") int rb_no,@PathVariable("page") int page,Model model) {
		model.addAttribute("rb_no", rb_no);
		model.addAttribute("page", page);
		
		return "reviewBoard/rbdetail";
	}
	
	// 상세페이지
	@GetMapping("/reviewDetail/{rb_no}/{page}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> cdetail(@PathVariable("rb_no") int rb_no,@PathVariable("page") int page){
		// 조회수 1증가
		reviewService.updatehit(rb_no);
		// 상세정보 구하기
		ReviewDTO reviewDTO = reviewService.getBoard(rb_no);
		String content = reviewDTO.getRb_content().replace("\n", "<br>");

		Map map = new HashMap<>();
		map.put("reviewDTO", reviewDTO);
		map.put("content", content);
		map.put("page", page);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	// 수정폼
	@GetMapping("/updateform/{rb_no}/{page}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> rbupdateform(@PathVariable("rb_no") int rb_no,@PathVariable("page") int page){
		
		ReviewDTO reviewDTO = reviewService.getBoard(rb_no);
		
		Map map = new HashMap<>();
		map.put("reviewDTO", reviewDTO);
		map.put("page", page);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	// 글수정
	@PutMapping("/boardupdate")
	@ResponseBody
	public ResponseEntity<Integer> boardupdate(@RequestBody ReviewDTO reviewDTO){
		int result = reviewService.update(reviewDTO);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 글삭제
	@DeleteMapping("/boarddelete/{rb_no}")
	@ResponseBody
	public ResponseEntity<Integer> boarddelete(@PathVariable("rb_no") int rb_no) {
		int result = reviewService.delete(rb_no);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
