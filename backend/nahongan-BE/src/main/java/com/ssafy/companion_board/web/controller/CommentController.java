package com.ssafy.companion_board.web.controller;

import com.ssafy.companion_board.web.dto.response.comment.GetCommentNoticeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.companion_board.domain.CommentService;
import com.ssafy.companion_board.domain.CompanionBoardService;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.request.DeleteArticleRequest;
import com.ssafy.companion_board.web.dto.request.GetArticleListRequest;
import com.ssafy.companion_board.web.dto.request.UpdateArticleRequest;
import com.ssafy.companion_board.web.dto.request.WriteArticleRequest;
import com.ssafy.companion_board.web.dto.request.comment.CreateChildCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.CreateCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.GetCommentsRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentReadRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentRequest;
import com.ssafy.companion_board.web.dto.response.GetArticleListResponse;
import com.ssafy.companion_board.web.dto.response.GetArticleResponse;
import com.ssafy.companion_board.web.dto.response.UpdateArticleResponse;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentResponse;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentsResponse;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "동행게시판 댓글 API", description = "동행게시판 댓글 관련 API")
@RequestMapping("/api/companion-board")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/comment")
	public ResponseEntity<Void> createComment(@RequestBody CreateCommentRequest request, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		System.out.println(userDetails.getUsername());
		commentService.createComment(request, userDetails.getUsername());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/comment/child")
	public ResponseEntity<Void> createChildComment(@RequestBody CreateChildCommentRequest request, HttpSession session) throws Exception {
		String userId = (String) session.getAttribute("user");
		commentService.createComment(request, userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable int commentId, HttpSession session) throws Exception {
		String userId = (String) session.getAttribute("user");
		return new ResponseEntity<>(commentService.getComment(commentId, userId), HttpStatus.CREATED);
	}
	
	@GetMapping("/{articleNo}/comment")
	public ResponseEntity<GetCommentsResponse> getComments(@PathVariable int articleNo, HttpSession session) throws Exception {
		String userId = "string";
		System.out.println("userid : " + userId);
		return new ResponseEntity<>(commentService.getComments(articleNo, userId), HttpStatus.CREATED);
	}

	@PutMapping("/comment/{commentId}")
	public ResponseEntity<Void> updateComment(@RequestBody UpdateCommentRequest request, HttpSession session) throws Exception {
		commentService.updateComment(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/comment/{commentId}/read")
	public ResponseEntity<Void> updateCommentRead(@RequestBody UpdateCommentReadRequest request, HttpSession session) throws Exception {
		commentService.updateCommentRead(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable("commentId") int commentId, HttpSession session) throws Exception {
		String userId = (String) session.getAttribute("user");
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/comment/notice")
	public ResponseEntity<GetCommentNoticeResponse> getCommentNotice(@RequestParam("userId") String userId, HttpSession httpSession) throws Exception {
//		String userId = (String) httpSession.getAttribute("user");
		return new ResponseEntity<>(commentService.getCommentNotice(userId), HttpStatus.OK);
	}
}
