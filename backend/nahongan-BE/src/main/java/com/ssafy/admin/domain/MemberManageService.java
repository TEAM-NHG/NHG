package com.ssafy.admin.domain;

import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.persistent.repository.MemberRepository;
import com.ssafy.member.web.dto.response.GetAllMemberInfoResponse;
import com.ssafy.member.web.dto.response.GetMemberCountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberManageService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public GetAllMemberInfoResponse getAllMemberInfo() {
        return GetAllMemberInfoResponse.builder()
                .members(memberRepository.findAllMember())
                .counts(memberRepository.countMember())
                .build();
    }

    public GetMemberCountResponse getMemberCount() {
        return GetMemberCountResponse.builder()
                .counts(memberRepository.countMember())
                .build();
    }

    public void modify(Member member) throws SQLException {
        memberRepository.modify(member);

        Member original = memberRepository.findById(member.getId());
        if (!member.checkPassword(original.getPassword(), passwordEncoder)){
            memberRepository.modifyPassword(member.getId(), member.getPassword());
        }
    }

    public Member read(String id) {
        return memberRepository.findById(id);
    }
}
