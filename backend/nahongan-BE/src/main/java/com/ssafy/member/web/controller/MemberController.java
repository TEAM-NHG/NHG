package com.ssafy.member.web.controller;

import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.request.FindIdRequest;
import com.ssafy.member.web.dto.request.FindPasswordRequest;
import com.ssafy.member.web.dto.request.JoinRequest;
import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.member.web.dto.request.ModifyMemberRequest;
import com.ssafy.member.web.dto.request.ModifyPasswordRequest;
import com.ssafy.member.web.dto.response.FindIdResponse;
import com.ssafy.member.web.dto.response.GetMemberImageResponse;
import com.ssafy.member.web.dto.response.GetMemberResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.ssafy.member.domain.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@Tag(name = "멤버 API", description = "유저 관련 API")
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	@Operation(
			summary = "회원가입 아이디 체크 API", // 요약
			description = "true or false 반환" // 상세 설명
	)
	@PostMapping("/join/id-check")
	public ResponseEntity<Boolean> checkDuplicatedId(@RequestBody Map<String, String> requestBody) throws Exception {
		String id = requestBody.get("id");
		return new ResponseEntity<>(memberService.checkId(id), HttpStatus.OK);
	}

	@Operation(
			summary = "회원가입 API", // 요약
			description = "회원가입 성공 or 오류 방출" // 상세 설명
	)
	@PostMapping(value="/join", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> join(@RequestPart(value="member") JoinRequest request, @RequestPart(required = false, value="images") MultipartFile images) throws Exception {
		memberService.join(request, images);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Operation(summary = "현재 사용자 정보 조회", description = "현재 인증된 사용자의 정보를 반환합니다.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "현재 사용자 정보 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증되지 않은 사용자")
	})

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest request) throws Exception {
		return new ResponseEntity<>(HttpStatus.OK);
	}

//	@Operation(
//			summary = "로그아웃 API", // 요약
//			description = "로그아웃 세션해제" // 상세 설명
//	)
//	@PostMapping("/logout")
//	public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
//
//		memberService.logout();
//		return new ResponseEntity<>(HttpStatus.OK);
//	}

	@Operation(
			summary = "프로필 정보 호출 API", // 요약
			description = "비밀번호를 제외한 모든 유저의 정보 반환" // 상세 설명
	)
	@GetMapping("/profile")
	public ResponseEntity<GetMemberResponse> getMemberProfile(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
		return new ResponseEntity<GetMemberResponse>(memberService.getMember(userDetails.getUsername()), HttpStatus.OK);
	}

	@Operation(
			summary = "프로필 이미지 조회 API", // 요약
			description = "프로필 이미지만 반환. 본인이 아니어도 요청 가능" // 상세 설명
	)
	@GetMapping("/profile/image")
	public ResponseEntity<GetMemberImageResponse> getMemberProfileImage(String userId) throws Exception {
		return new ResponseEntity<>(memberService.getMemberImage(userId), HttpStatus.OK);
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
	
	@PostMapping(value="/modify",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> modify(@RequestPart(value="member") ModifyMemberRequest member, @RequestPart(required = false, value="images") MultipartFile images) throws SQLException {
		return new ResponseEntity<>(memberService.modify(images, member), HttpStatus.OK);
	}
	
	@PostMapping(value = "/profile/img",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> setMemberImage(@RequestPart(required = false) MultipartFile images, @AuthenticationPrincipal UserDetails userDetails) throws SQLException {
		memberService.setMemberImage(images, userDetails.getUsername());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
