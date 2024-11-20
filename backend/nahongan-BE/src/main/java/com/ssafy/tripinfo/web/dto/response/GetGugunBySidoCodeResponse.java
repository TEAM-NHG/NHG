package com.ssafy.tripinfo.web.dto.response;

import java.util.List;

import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.tripinfo.persistent.entity.Guguns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetGugunBySidoCodeResponse {
	List<Guguns> gugunList;
}
