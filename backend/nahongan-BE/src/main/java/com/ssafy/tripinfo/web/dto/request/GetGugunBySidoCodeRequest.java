package com.ssafy.tripinfo.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetGugunBySidoCodeRequest {
	String sidoCode;
}
