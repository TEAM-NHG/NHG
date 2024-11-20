package com.ssafy.tripinfo.web.dto.response;

import java.util.List;

import com.ssafy.member.web.dto.request.LoginRequest;
import com.ssafy.tripinfo.persistent.entity.ContentTypes;
import com.ssafy.tripinfo.persistent.entity.Sidos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetFilterContentResponse {
	List<ContentTypes> contentTypeList;
	List<Sidos> sidoList;
}
