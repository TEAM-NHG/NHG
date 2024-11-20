package com.ssafy.companion_board.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.companion_board.persistent.entity.CompanionBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class WriteArticleResponse {
	private int articleNo; // 게시글 번호
	private String userId; // 사용자 ID
	private String subject; // 제목
	private String content; // 내용
	private int hit; // 조회수
	private LocalDateTime registerTime; // 등록 시간

	public static WriteArticleResponse from(CompanionBoard article) {
		return WriteArticleResponse
				.builder()
				.articleNo(article.getArticleNo())
				.userId(article.getUserId())
				.subject(article.getSubject())
				.content(article.getContent())
				.hit(article.getHit())
				.registerTime(article.getRegisterTime())
				.build();
	}
}
