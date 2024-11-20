package com.ssafy.aiplanner.web.dto;

import com.ssafy.tripinfo.web.dto.request.GetGugunBySidoCodeRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class AIPlannerRequest {
	private String userPlan;
	private int aiType;
}
