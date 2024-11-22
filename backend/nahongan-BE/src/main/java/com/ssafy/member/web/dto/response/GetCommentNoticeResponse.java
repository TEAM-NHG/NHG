package com.ssafy.member.web.dto.response;

import java.util.List;

import com.ssafy.companion_board.persistent.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class GetCommentNoticeResponse {
	List<CommentNotice> comments;
}
