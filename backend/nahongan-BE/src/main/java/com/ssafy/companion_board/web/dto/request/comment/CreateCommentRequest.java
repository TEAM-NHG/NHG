package com.ssafy.companion_board.web.dto.request.comment;

import com.ssafy.companion_board.web.dto.request.WriteArticleRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateCommentRequest {
	private int articleNo;
	private String content;
}