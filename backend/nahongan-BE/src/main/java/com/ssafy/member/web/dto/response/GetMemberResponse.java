package com.ssafy.member.web.dto.response;

import com.ssafy.member.persistent.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetMemberResponse {
    private final String id;
    private final String nickname;
    private final String email;
    private final String phone;
    private final String img;

    public static GetMemberResponse from(Member member) {
        return GetMemberResponse
                .builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phone(member.getPhone())
                .img(member.getImg())
                .build();
    }
}
