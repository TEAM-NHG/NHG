package com.ssafy.aiplanner.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AIPlannerResponse {
	private String answer;
}
