package com.ssafy.companion_board.web.dto.request;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetArticleListRequest {
	private String key;
	private String word;
};

