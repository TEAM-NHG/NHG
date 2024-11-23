package com.ssafy.member.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Builder
@AllArgsConstructor
@Getter
public class GetNoticeCountResponse {
    int count;
}
