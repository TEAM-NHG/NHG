package com.ssafy.companion_board.web.dto.response.comment;

import java.util.List;

import com.ssafy.companion_board.web.dto.CommentNotice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetCommentNoticeResponse {
	List<CommentNotice> comments;
}
