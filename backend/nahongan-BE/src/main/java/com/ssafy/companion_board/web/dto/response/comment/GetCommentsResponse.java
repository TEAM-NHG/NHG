package com.ssafy.companion_board.web.dto.response.comment;

import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.companion_board.persistent.entity.Comment;
import com.ssafy.companion_board.web.dto.CommentDto;
import com.ssafy.companion_board.web.dto.ParentCommentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetCommentsResponse {
	List<ParentCommentDto> comments;
	int count;
	public static GetCommentsResponse from(List<ParentCommentDto> comments, int count) {
		return GetCommentsResponse.builder().comments(comments)
				.count(count)
				.build();
	}
}
