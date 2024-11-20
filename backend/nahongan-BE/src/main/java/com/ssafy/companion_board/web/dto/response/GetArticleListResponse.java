package com.ssafy.companion_board.web.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetArticleListResponse {
	List<GetArticleResponse> articles;
	int currentPage;
	int lastPage;
	public static GetArticleListResponse from(List<CompanionBoard> articles, int currentPage, int lastPage) {
		return GetArticleListResponse.builder()
				.articles(articles.stream()
						.map(GetArticleResponse::from)
						.collect(Collectors.toList()))
				.currentPage(currentPage)
				.lastPage(lastPage)
				.build();
	}
}
