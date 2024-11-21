package com.ssafy.member.domain;

import com.ssafy.common.exception.BaseException;
import com.ssafy.common.util.ImageUploader;
import com.ssafy.companion_board.persistent.repository.CommentRepository;
import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.persistent.repository.MemberRepository;
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
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    
    private final CommentRepository commentRepository;

    private final PasswordEncoder passwordEncoder;
    
    private final ImageUploader imageUploader;
    
    private String USER_IMAGE_PATH;
    
    public void join(JoinRequest request) throws Exception {
        System.out.println(checkId(request.getId()));
        if (checkId(request.getId())) {
            throw new BaseException("이미 존재하는 아이디",HttpStatus.BAD_REQUEST);
        }
        Member member = request.toEntity();
        member.hashPassword(passwordEncoder);
        memberRepository.create(member);
    }

    public LoginResponse login(LoginRequest request, HttpSession httpSession) throws Exception {
        Member member = memberRepository.findById(request.getId());
        //아이디 검사
        if (member == null) {
            throw new BaseException("없는 아이디인디요", HttpStatus.BAD_REQUEST);
        }
        //비밀번호 검사
        if (!member.checkPassword(request.getPassword(), passwordEncoder)) {
            throw new BaseException("없는 비번인디요", HttpStatus.BAD_REQUEST);
        }
        //세션 삽입
        httpSession.setAttribute("user", member.getId());
        return LoginResponse.from(member);
    }

    public void logout(HttpSession httpSession) {
        System.out.println(httpSession.getAttribute("user"));
        httpSession.removeAttribute("user");
        httpSession.invalidate();
    }

    public Boolean checkId(String id) throws SQLException {
        return memberRepository.existsById(id);
    }

    public GetMemberResponse getMember(HttpSession httpSession) {
        String id = (String) httpSession.getAttribute("user");
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
    
    public int modify(MemberDto member) throws SQLException {
    	return memberRepository.modify(member.toEntity());
    }
    
    public int delete(String id) {
    	return memberRepository.delete(id);
    }

	public void setMemberImage(List<MultipartFile> images, String userId) throws SQLException {
		Member member = memberRepository.findById(userId);
		String detailPath = "member" + File.separator + userId;
		String imagePath = imageUploader.upload(images, detailPath);
		member.updateImage(imagePath);
		memberRepository.modify(member);
	}
	
	public GetCommentHistoryResponse getCommentNotice(String userId) {
		return GetCommentHistoryResponse.builder()
		.articleComments(commentRepository.findCommentByUserArticle(userId))
		.childComments(commentRepository.findChildCommentByUserParentComment(userId))
		.build();
	}

}
