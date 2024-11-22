package com.ssafy.companion_board.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.companion_board.domain.CompanionBoardService;
import com.ssafy.companion_board.web.dto.request.DeleteArticleRequest;
import com.ssafy.companion_board.web.dto.request.GetArticleListRequest;
import com.ssafy.companion_board.web.dto.request.UpdateArticleRequest;
import com.ssafy.companion_board.web.dto.request.WriteArticleRequest;
import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.companion_board.web.dto.response.UpdateArticleResponse;
import com.ssafy.tripinfo.web.dto.request.SearchBlogRequest;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "동행게시판 API", description = "Companion board 관련 API")
@RequestMapping("/api/companion-board")
public class CompanionBoardController {

	private final CompanionBoardService companionBoardService;

	@GetMapping
	public ResponseEntity<GetArticleListResponse> getArticles(GetArticleListRequest request, @RequestParam(required=false, defaultValue = "1") int pgno) throws Exception {
		long start = System.currentTimeMillis();
		GetArticleListResponse responseBody = companionBoardService.getArticles(request, pgno);
		long end = System.currentTimeMillis();
		System.out.println("Execution time: " + (end - start) + " ms");
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@GetMapping("/{articleNo}")
	public ResponseEntity<GetArticleResponse> getArticle(@PathVariable int articleNo, HttpSession session) throws Exception {
		
		return new ResponseEntity<GetArticleResponse>(companionBoardService.getArticle(articleNo), HttpStatus.OK);
	}
	@PostMapping
	public ResponseEntity<Void> writeArticle(@RequestBody WriteArticleRequest request) throws Exception {
		System.out.println(request);
		companionBoardService.writeArticle(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<UpdateArticleResponse> updateArticle(@RequestBody UpdateArticleRequest request, HttpSession session) throws Exception {
		System.out.println(request.getArticleNo());
		return new ResponseEntity<>(companionBoardService.updateArticle(request), HttpStatus.OK);
	}
	
	@DeleteMapping("/{articleno}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("articleno") int articleno, HttpSession session) throws Exception {
		DeleteArticleRequest article = new DeleteArticleRequest(articleno);
		System.out.println(article);
		companionBoardService.deleteArticle(article);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
