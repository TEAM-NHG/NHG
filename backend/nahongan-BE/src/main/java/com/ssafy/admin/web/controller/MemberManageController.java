package com.ssafy.admin.web.controller;

import com.ssafy.admin.domain.MemberManageService;
import com.ssafy.member.domain.MemberService;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.MemberDto;
import com.ssafy.member.web.dto.request.JoinRequest;
import com.ssafy.member.web.dto.response.GetAllMemberInfoResponse;
import com.ssafy.member.web.dto.response.GetMemberCountResponse;
import com.ssafy.member.web.dto.response.GetMemberResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/member")
public class MemberManageController {
    //기능 : 모든 유저 조회, 유저 정보 수정, 강제 탈퇴, 유저 추가, 유저 개수

    private final MemberManageService memberManageService;

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody JoinRequest request) throws Exception {
        memberService.join(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Member> read(@PathVariable String userId) throws Exception {
        return new ResponseEntity<>(memberManageService.read(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetAllMemberInfoResponse> list() throws Exception {
        return new ResponseEntity<>(memberManageService.getAllMemberInfo(), HttpStatus.OK);
    }

    @PostMapping("/modify")
    public ResponseEntity<Void> modify(@RequestBody Member member) throws SQLException {
        memberManageService.modify(member);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable("userId") String id) throws SQLException {
        return new ResponseEntity<>(memberService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    GetMemberCountResponse getMemberCount() {
        return memberManageService.getMemberCount();
    }
}
