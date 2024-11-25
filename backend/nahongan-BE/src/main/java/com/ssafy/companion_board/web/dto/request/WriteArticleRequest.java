package com.ssafy.companion_board.web.dto.request;

import com.ssafy.companion_board.domain.CompanionBoardService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WriteArticleRequest {
	private String subject; // 제목
    private String content; // 내용
}
