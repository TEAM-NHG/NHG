package com.ssafy.tripinfo.web.dto.request;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SearchAttractionRequest {
	private String type;
	private String sidoCode;
	private String gugunCode;
	private String keyword;
}
