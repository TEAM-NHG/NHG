package com.ssafy.member.domain;

import com.ssafy.common.exception.BaseException;
import com.ssafy.common.util.ImageUploader;
import com.ssafy.companion_board.persistent.repository.CommentRepository;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.persistent.repository.MemberRepository;
import com.ssafy.member.web.dto.request.FindIdRequest;
import com.ssafy.member.web.dto.request.FindPasswordRequest;
import com.ssafy.member.web.dto.request.JoinRequest;
import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.member.web.dto.request.ModifyMemberRequest;
import com.ssafy.member.web.dto.request.ModifyPasswordRequest;
import com.ssafy.member.web.dto.response.FindIdResponse;
import com.ssafy.member.web.dto.response.GetMemberImageResponse;
import com.ssafy.member.web.dto.response.GetMemberResponse;
import com.ssafy.member.web.dto.response.GetNoticeCountResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final ImageUploader imageUploader;
    
    public void join(JoinRequest request) throws Exception {
        System.out.println(checkId(request.getId()));
        if (checkId(request.getId())) {
            throw new BaseException("이미 존재하는 아이디",HttpStatus.BAD_REQUEST);
        }
        Member member = request.toEntity();
        member.hashPassword(passwordEncoder);
        memberRepository.create(member);
    }

    public void logout(HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("user"));
        httpSession.removeAttribute("user");
        httpSession.invalidate();
    }

    public Boolean checkId(String id) throws SQLException {
        return memberRepository.existsById(id);
    }

    public GetMemberResponse getMember(String id) {
        System.out.println(id);
        Member member = memberRepository.findById(id);
        if (member == null) {
            throw new BaseException("없다구?", HttpStatus.BAD_REQUEST);
        }
        return GetMemberResponse.from(member);
    }

    public void findPassword(FindPasswordRequest request) {
        memberRepository.modifyPassword(request.getId(), request.getEmail());
    }
    
    public FindIdResponse findId(FindIdRequest request) {
        String id = memberRepository.findIdByEmail(request.getEmail());
        return FindIdResponse.builder().id(id).build();
    }
    public void modifyPassword(ModifyPasswordRequest request, HttpSession httpSession) {
    	memberRepository.modifyPassword(request.getId(), passwordEncoder.encode(request.getPassword()));
    }    
    
    public int modify(MultipartFile images, ModifyMemberRequest member) throws SQLException {
    	Member memberEntity = member.toEntity();
    	memberEntity.updateImage(setMemberImage(images, member.getId()));
    	return memberRepository.modify(memberEntity);
    }
    
    public int delete(String id) {
    	return memberRepository.delete(id);
    }

	public String setMemberImage(MultipartFile images, String userId) throws SQLException {
		Member member = memberRepository.findById(userId);
		String detailPath = "member" + File.separator + userId;
        return imageUploader.upload(images, detailPath);
	}

	public GetMemberImageResponse getMemberImage(String userId) {
		Member member = memberRepository.findById(userId);
		return GetMemberImageResponse.builder().image(member.getImg()).build();
	}

    public GetNoticeCountResponse getMemberNoticeCount(String userId) {
        Member member = memberRepository.findById(userId);
        return GetNoticeCountResponse.builder().count(member.getCommentNotificationCount()).build();
    }

}
