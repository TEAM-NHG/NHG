package com.ssafy.member.web.controller;

import com.ssafy.member.web.dto.MemberDto;
import com.ssafy.member.web.dto.request.FindIdRequest;
import com.ssafy.member.web.dto.request.FindPasswordRequest;
import com.ssafy.member.web.dto.request.JoinRequest;
import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.member.web.dto.request.ModifyPasswordRequest;
import com.ssafy.member.web.dto.response.FindIdResponse;
import com.ssafy.member.web.dto.response.FindPasswordResponse;
import com.ssafy.member.web.dto.response.GetCommentHistoryResponse;
import com.ssafy.member.web.dto.response.GetMemberResponse;
import com.ssafy.member.web.dto.response.LoginResponse;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.member.domain.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;
	
//	@Value("file.path.upload-images")
//	private String uploadPath;

	@PostMapping("/join/id-check")
	public ResponseEntity<Boolean> checkDuplicatedId(@RequestBody Map<String, String> requestBody) throws Exception {
		String id = requestBody.get("id");
		return new ResponseEntity<>(memberService.checkId(id), HttpStatus.OK);
	}

	@PostMapping("/join")
	public ResponseEntity<Void> join(@RequestBody JoinRequest request) throws Exception {
		memberService.join(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, HttpSession httpSession) throws Exception {
		return new ResponseEntity<LoginResponse>(memberService.login(request, httpSession), HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession httpSession) throws Exception {
		memberService.logout(httpSession);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<GetMemberResponse> getMemberProfile(HttpSession httpSession) throws Exception {
		return new ResponseEntity<GetMemberResponse>(memberService.getMember(httpSession), HttpStatus.OK);
	}
	
	@PostMapping("/find-id")
	public ResponseEntity<FindIdResponse> findId(@RequestBody FindIdRequest request) throws Exception {
		return new ResponseEntity<>(memberService.findId(request), HttpStatus.OK);
	}

	@PostMapping("/find-password")
	public ResponseEntity<Void> findPassword(@RequestBody FindPasswordRequest request) throws Exception {
		memberService.findPassword(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/password")
	public ResponseEntity<Void> modifyPassword(@RequestBody ModifyPasswordRequest request, HttpSession session) throws Exception {
		memberService.modifyPassword(request, session);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody MemberDto member) throws SQLException {
		return new ResponseEntity<>(memberService.modify(member), HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> delete(@PathVariable("userId") String id) throws SQLException {
		return new ResponseEntity<>(memberService.delete(id), HttpStatus.OK);
	}
	
	@PostMapping("/profile/img")
	public ResponseEntity<Void> setMemberImage(@RequestBody MultipartFile[] image, HttpSession httpSession) throws Exception {
		memberService.setMemberImage(image);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/profile/notice")
	public ResponseEntity<GetCommentHistoryResponse> getCommentNotice(@RequestParam("userId") String userId, HttpSession httpSession) throws Exception {
//		String userId = (String) httpSession.getAttribute("user");
		return new ResponseEntity<>(memberService.getCommentNotice(userId), HttpStatus.OK);
	}
}
