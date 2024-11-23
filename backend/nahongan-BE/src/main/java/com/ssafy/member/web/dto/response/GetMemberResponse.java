package com.ssafy.member.web.dto.response;

import com.ssafy.member.persistent.entity.Member;
import com.ssafy.member.web.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@Getter
public class GetMemberResponse extends MemberDto {
    public static GetMemberResponse from(Member member) {
        return GetMemberResponse
                .builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .phone(member.getPhone())
                .img(member.getImg())
                .commentNotificationCount(member.getCommentNotificationCount())
                .build();
    }
}
