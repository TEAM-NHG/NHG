package com.ssafy.member.web.dto.response;

import com.ssafy.member.persistent.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class GetAllMemberInfoResponse {
    List<Member> members;
    int counts;
}
