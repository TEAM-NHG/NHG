package com.ssafy.companion_board.web.dto.request.comment;

import com.ssafy.companion_board.web.dto.request.WriteArticleRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCommentRequest {
	private String userId;
	private int articleNo;
	private String content; // 내용
}