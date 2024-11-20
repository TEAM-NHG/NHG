package com.ssafy.member.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ModifyPasswordRequest {
	private String id;
    private String password;
}
