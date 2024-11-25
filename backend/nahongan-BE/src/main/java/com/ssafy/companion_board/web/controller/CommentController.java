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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.companion_board.domain.CommentService;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.request.comment.CreateChildCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.CreateCommentRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentReadRequest;
import com.ssafy.companion_board.web.dto.request.comment.UpdateCommentRequest;
import com.ssafy.companion_board.web.dto.response.comment.GetCommentsResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "동행게시판 댓글 API", description = "동행게시판 댓글 관련 API")
@RequestMapping("/api/companion-board")
public class CommentController {

	private final CommentService commentService;

	@PostMapping("/comment")
	public ResponseEntity<Void> createComment(@RequestBody CreateCommentRequest request, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		commentService.createComment(request, userDetails.getUsername());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/comment/child")
	public ResponseEntity<Void> createChildComment(@RequestBody CreateChildCommentRequest request, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		commentService.createComment(request, userDetails.getUsername());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable int commentId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		return new ResponseEntity<>(commentService.getComment(commentId, userDetails.getUsername()), HttpStatus.CREATED);
	}
	
	@GetMapping("/{articleNo}/comment")
	public ResponseEntity<GetCommentsResponse> getComments(@PathVariable int articleNo) throws Exception {
		return new ResponseEntity<>(commentService.getComments(articleNo), HttpStatus.CREATED);
	}

	@PutMapping("/comment/{commentId}")
	public ResponseEntity<Void> updateComment(@RequestBody UpdateCommentRequest request, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		commentService.updateComment(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/comment/{commentId}/read")
	public ResponseEntity<Void> updateCommentRead(@RequestBody UpdateCommentReadRequest request, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		commentService.updateCommentRead(request, userDetails.getUsername());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<Void> deleteComment(@PathVariable int commentId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		System.out.println(commentId);
		commentService.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/comment/notice")
	public ResponseEntity<GetCommentNoticeResponse> getCommentNotice(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
		return new ResponseEntity<>(commentService.getCommentNotice(userDetails.getUsername()), HttpStatus.OK);
	}
}
