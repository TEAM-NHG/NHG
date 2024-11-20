package com.ssafy.member.web.dto.response;

import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class LoginResponse {
    private final String id;
    private final String nickname;
    private final String email;
    private final String phone;
    private final String img;

    public static LoginResponse from(Member member) {
        return LoginResponse
                .builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phone(member.getPhone())
                .img(member.getImg())
                .build();
    }
}
