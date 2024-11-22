package com.ssafy.companion_board.web.dto;

import java.time.LocalDateTime;

import com.ssafy.companion_board.persistent.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CommentNotice {
	private int id;            // 댓글 ID
    private int articleNo;  
    private String userId; 
    private String content;
    private LocalDateTime createdAt;  // 댓글 생성일
    private String type;
    
    public static CommentNotice from(Comment comment, String type) {
    	return CommentNotice.builder()
    			.id(comment.getId())
    			.articleNo(comment.getArticleNo())
    			.userId(comment.getUserId())
    			.content(comment.getContent())
    			.createdAt(comment.getCreatedAt())
    			.type(type)
    			.build();
    }
}
